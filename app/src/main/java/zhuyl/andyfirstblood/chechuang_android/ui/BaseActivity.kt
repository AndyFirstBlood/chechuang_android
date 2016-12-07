package zhuyl.andyfirstblood.chechuang_android.ui

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import zhuyl.andyfirstblood.chechuang_android.R
import zhuyl.andyfirstblood.chechuang_android.util.bindOptionalView

abstract class BaseActivity : AppCompatActivity() {
    var lastBackTime: Long = 0
    protected var currentDialog: Dialog? = null
    val menuButton: TextView? by bindOptionalView(R.id.toolbar_menu)
    val toolbar: Toolbar? by bindOptionalView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemViews()
        super.onCreate(savedInstanceState)

        initContentView()
        initToolbar()
    }

    fun confirmAndExit() {
        val currentBackTime = System.currentTimeMillis()
        if (currentBackTime - lastBackTime > 2 * 1000) {
            Toast.makeText(this, "再按一次返回键退出", LENGTH_SHORT).show()
            lastBackTime = currentBackTime
        } else {
            finishAffinity()
            System.exit(0)
        }
    }

    abstract fun logoutUserWithError(error: Any)

    abstract fun initData(savedInstanceState: Bundle?)

    protected fun initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            toolbar!!.setNavigationOnClickListener { onBackPressed() }
        }
    }

    abstract fun initContentView()

    protected open fun initSystemViews() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
    }
}


