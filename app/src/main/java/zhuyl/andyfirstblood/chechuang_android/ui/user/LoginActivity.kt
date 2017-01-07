package zhuyl.andyfirstblood.chechuang_android.ui.user

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.jakewharton.rxbinding.view.RxView
import kotterknife.bindView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers.mainThread
import zhuyl.andyfirstblood.chechuang_android.R
import zhuyl.andyfirstblood.chechuang_android.client.LoginRequest
import zhuyl.andyfirstblood.chechuang_android.client.SendSmsRequest
import zhuyl.andyfirstblood.chechuang_android.root.BindContentView
import zhuyl.andyfirstblood.chechuang_android.ui.BaseActivity
import zhuyl.andyfirstblood.chechuang_android.ui.weight.SmsRequestDialog
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

@BindContentView(R.layout.activity_login)
class LoginActivity : BaseActivity() {
    val userPhoneEditText: EditText by bindView(R.id.session_edittext_username)
    val smsCodeEditText: EditText by bindView(R.id.session_edittext_password)
    val loginButton: Button by bindView(R.id.session_button_login)
    val smsRequestButton: Button by bindView(R.id.session_request_button)

    override fun initContentView() {
        super.initContentView()
    }
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        smsRequestButton.setOnClickListener { showSmsRequestDialog() }
        loginButton.setOnClickListener { login() }
    }

    private fun showSmsRequestDialog() {
        val phoneNumber = userPhoneEditText.text.toString()
        if (phoneNumber.isEmpty()) {
            alertDialog("请输入您的手机号")
            return
        }

        if (!checkPhoneNumber(phoneNumber)) {
            alertDialog("请输入正确格式的手机号码")
            return
        }

        val smsRequestDialog = SmsRequestDialog(this)
        smsRequestDialog.show()
        smsRequestDialog.setCancelable(true)
        val confirmButton = smsRequestDialog.confirmButton
        RxView.clicks(confirmButton).subscribe {
            invokeSendAction(phoneNumber, smsRequestDialog)
        }

    }

    private fun invokeSendAction(phoneNumber: String, smsRequestDialog: SmsRequestDialog) {
        disableSendSmsTokenButton()
        confirmAndAsk(phoneNumber, smsRequestDialog)
    }

    private fun disableSendSmsTokenButton() {
        smsRequestButton.isEnabled = false
        smsRequestButton.text = "发送中..."
    }

    private fun waitForOneMinute() {
        Observable.interval(1, TimeUnit.SECONDS).takeUntil { second -> second > 60 }.observeOn(mainThread()).doOnCompleted({
            this.enableSendSmsTokenButton()
        }).subscribe({ second -> smsRequestButton.text = "重新发送(" + (60 - second!!) + ")" }, {})
    }

    private fun enableSendSmsTokenButton() {
        smsRequestButton.isEnabled = true
        smsRequestButton.text = "获取验证码"
    }

    fun checkPhoneNumber(phoneNumber: String): Boolean {
        val pattern = Pattern.compile("^1[0-9]{10}$")
        val matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }

    private fun confirmAndAsk(phoneNumber: String, smsRequestDialog: SmsRequestDialog) {
        val toUpperCase = smsRequestDialog.code.toUpperCase()
        val typeCode = smsRequestDialog.codeEditText.text.toString().toUpperCase()
        if (toUpperCase != typeCode) {
            makeText(this, "请输入正确的验证码", LENGTH_SHORT).show()
            smsRequestDialog.createCode()
            return
        }
        smsRequestDialog.dismiss()
        smsRequestRequirements(phoneNumber)
    }

    private fun login() {
        val phone = userPhoneEditText.text.toString()
        val smsCode = smsCodeEditText.text.toString()
        if (phone.isEmpty() || smsCode.isEmpty() || !checkPhoneNumber(phone)) {
            alertDialog("请填写正确的手机号和验证码")
            return
        }

        invokeRequestBackground(LoginRequest().apply {
            prepare().formMobilePhoneNumber(phone).formSmsCode(smsCode)
        }).observeOn(mainThread()).subscribe({
            alertDialog("登录成功！")
        }, {
            alertDialog(it.message!!)
        })
    }

    fun smsRequestRequirements(phone: String) {
        invokeRequestBackground(SendSmsRequest().apply {
            prepare().interpolation(phone)
        }).observeOn(mainThread()).subscribe({
            hideCurrentDialog()
            makeText(this, "已发送", LENGTH_SHORT).show()
            waitForOneMinute()
        }, {
            alertDialog(it.message!!)
            enableSendSmsTokenButton()
        })
    }

}
