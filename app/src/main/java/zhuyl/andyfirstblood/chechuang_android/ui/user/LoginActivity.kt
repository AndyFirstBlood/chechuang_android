package zhuyl.andyfirstblood.chechuang_android.ui.user

import android.os.Bundle
import rx.android.schedulers.AndroidSchedulers.mainThread
import zhuyl.andyfirstblood.chechuang_android.R
import zhuyl.andyfirstblood.chechuang_android.client.SendSmsRequest
import zhuyl.andyfirstblood.chechuang_android.root.BindContentView
import zhuyl.andyfirstblood.chechuang_android.ui.BaseActivity

@BindContentView(R.layout.activity_login)
class LoginActivity : BaseActivity() {

    override fun initContentView() {
        super.initContentView()
    }
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    private fun smsRequestRequirements() {
        invokeRequestBackground(SendSmsRequest().apply {
            prepare().interpolation()
        }).observeOn(mainThread()).subscribe({

        }, {
            alertDialog(it.message!!)
        })
    }
}
