package zhuyl.andyfirstblood.chechuang_android.ui.weight

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotterknife.bindView
import zhuyl.andyfirstblood.chechuang_android.R
import zhuyl.andyfirstblood.chechuang_android.utils.CodeUtils

class SmsRequestDialog(context: Context?) : Dialog(context) {
    val verificationCodeImage: ImageView by bindView(R.id.verification_code)
    val confirmButton: Button by bindView(R.id.ask_for_sms_button)
    val codeEditText: EditText by bindView(R.id.type_verification_code)
    lateinit var code: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_ask_for_sms)

        createCode()
        verificationCodeImage.setOnClickListener {
            createCode()
        }
    }

    fun createCode() {
        val codeUtils = CodeUtils.getInstance()
        val bitmap = codeUtils.createBitmap()
        verificationCodeImage.setImageBitmap(bitmap)
        code = codeUtils.code
    }

}
