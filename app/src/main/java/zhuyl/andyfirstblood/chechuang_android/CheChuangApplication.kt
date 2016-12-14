package zhuyl.andyfirstblood.chechuang_android

import android.app.Activity
import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build.CPU_ABI
import android.os.Build.VERSION.RELEASE
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import zhuyl.andyfirstblood.chechuang_android.client.CheChuangClient
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

class CheChuangApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    lateinit var cheChuangClient: CheChuangClient

    override fun onCreate() {
        super.onCreate()
        initCheChuangClient()
    }

    fun initCheChuangClient() {
        val client = buildOkHttpClient()
        val baseUrl = "http://chechuang.leanapp.cn/api/v1/"

        val userAgent = "CheChuang/" + packageInfo.versionName + "-" + packageInfo.versionCode + "(Android; U; " + CPU_ABI + "-" + RELEASE + "; " +
                Locale.getDefault().language + "-" + Locale.getDefault().country + ")"
        cheChuangClient = CheChuangClient(client, baseUrl, userAgent)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)

        Stetho.initializeWithDefaults(this)
        builder.addNetworkInterceptor(StethoInterceptor())

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        builder.addNetworkInterceptor(logging)

        val baseDir = cacheDir
        if (baseDir != null) {
            val httpResponseDiskCacheMaxSize = 10 * 1024 * 1024
            val cacheDir = File(baseDir, "HttpResponseCache")
            builder.cache(Cache(cacheDir, httpResponseDiskCacheMaxSize.toLong()))
        }

        return builder.build()
    }

    val packageInfo: PackageInfo
        get() {
            try {
                return packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException(e)
            }

        }

    override fun onActivityPaused(p0: Activity?) {

    }

    override fun onActivityStarted(p0: Activity?) {
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityResumed(p0: Activity?) {
    }
}
