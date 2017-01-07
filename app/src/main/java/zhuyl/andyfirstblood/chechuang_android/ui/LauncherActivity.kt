package zhuyl.andyfirstblood.chechuang_android.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import zhuyl.andyfirstblood.chechuang_android.ui.user.LoginActivity


class LauncherActivity: BaseActivity() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        intentToLoginActivity()
    }

    private fun intentToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_BROUGHT_TO_FRONT
        startActivity(intent)
        finish()
    }
}
