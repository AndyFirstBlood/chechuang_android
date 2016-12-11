package zhuyl.andyfirstblood.chechuang_android.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotterknife.bindOptionalView
import rx.Observable
import zhuyl.andyfirstblood.chechuang_android.CheChuangApplication
import zhuyl.andyfirstblood.chechuang_android.R
import zhuyl.andyfirstblood.chechuang_android.client.CheChuangClient
import zhuyl.andyfirstblood.chechuang_android.client.GenericRequest
import zhuyl.andyfirstblood.chechuang_android.root.ViewUtils

abstract class BaseActivity : AppCompatActivity() {
    var lastBackTime: Long = 0
    protected var currentDialog: Dialog? = null
    val menuButton: TextView? by bindOptionalView(R.id.toolbar_menu)
    val toolbar: Toolbar? by bindOptionalView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        initSystemViews()
        super.onCreate(savedInstanceState)
        initContentView()
        initToolbar()
        initData(savedInstanceState)

        application()
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

    protected fun initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            toolbar!!.setNavigationOnClickListener { onBackPressed() }
        }
    }

    protected open fun initContentView() {
        val viewId = ViewUtils.findContentViewId(this)
        if (viewId != null) {
            setContentView(viewId)
        }
    }
    protected open fun initSystemViews() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
    }

    protected open fun initData(savedInstanceState: Bundle?) {
    }

    fun showCurrentDialog(dialog: Dialog) {
        if (isFinishing) {
            return
        }

        runOnUiThread {
            hideCurrentDialog()
            dialog.show()
            currentDialog = dialog
        }
    }

    fun showCurrentDialog(builder: AlertDialog.Builder) {
        runOnUiThread { showCurrentDialog(builder.create()) }
    }

    fun hideCurrentDialog() {
        if (isFinishing) {
            return
        }

        if (currentDialog != null && currentDialog!!.isShowing) {
            runOnUiThread { currentDialog!!.cancel() }
        }
    }

    fun progressDialog(message: String) {
        runOnUiThread {
            val dialog = ProgressDialog(this)
            dialog.setCancelable(false)
            dialog.setMessage(message)
            showCurrentDialog(dialog)
        }
    }

    fun alertDialog(message: String) {
        alertDialogWithCallback(message).subscribe()
    }

    fun alertDialogWithCallback(message: String): Observable<Int> {
        return Observable.create<Int> { subscriber ->
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(false)
            builder.setMessage(message)
            builder.setPositiveButton("确定") { dialog, which ->
                subscriber.onNext(which)
                subscriber.onCompleted()
            }

            showCurrentDialog(builder)
        }
    }

    fun application(): CheChuangApplication {
        return this.application as CheChuangApplication
    }

    fun client(): CheChuangClient {
        return application().cheChuangClient
    }

    fun <T> invokeRequestBackground(request: GenericRequest<T>): Observable<T> {
        return client().rxInvoke(request)
    }
}


