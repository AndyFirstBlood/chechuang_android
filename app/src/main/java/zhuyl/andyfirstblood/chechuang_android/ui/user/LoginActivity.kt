package zhuyl.andyfirstblood.chechuang_android.ui.user

import android.os.Bundle
import android.widget.Button
import android.widget.Toast.makeText
import kotterknife.bindView
import rx.android.schedulers.AndroidSchedulers.mainThread
import zhuyl.andyfirstblood.chechuang_android.R
import zhuyl.andyfirstblood.chechuang_android.client.SendSmsRequest
import zhuyl.andyfirstblood.chechuang_android.root.BindContentView
import zhuyl.andyfirstblood.chechuang_android.ui.BaseActivity

@BindContentView(R.layout.activity_login)
class LoginActivity : BaseActivity() {
    val aaa: Button by bindView(R.id.aaa)

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        aaa.setOnClickListener { SmsRequestRequirements() }
    }

    private fun SmsRequestRequirements() {
        invokeRequestBackground(SendSmsRequest().apply {
            prepare().interpolation()
        }).observeOn(mainThread()).subscribe({
            makeText(this, "发送成功", 1000)
        }, {
            alertDialog(it.message!!)
        })
    }
}
