package zhuyl.andyfirstblood.chechuang_android.client

import com.damnhandy.uri.template.UriTemplate
import okhttp3.*
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.util.*

abstract class BaseRequest {
    lateinit var endpoint: String
    var interpolations: MutableMap<String, Any?> = HashMap()
    var filters: MutableList<Pair<String, String>> = LinkedList()
    var headers: MutableList<Pair<String, String>> = LinkedList()
    var forms: MutableList<Pair<String, String>> = LinkedList()
    var formFiles: MutableList<Pair<String, Pair<String, RequestBody>>> = LinkedList()
    var localization: MutableMap<String, String> = HashMap()

    protected fun buildRequestBuilder(): Request.Builder {
        val url = buildRequestUrl("http://chechuang.leanapp.cn")
        val urlBuilder = HttpUrl.parse(url).newBuilder()
        for ((key, value) in filters) {
            urlBuilder.addQueryParameter(key, value)
        }
        val builder = Request.Builder().url(urlBuilder.build().toString())
        if (!headers.isEmpty()) {
            for ((key, value) in headers) {
                builder.addHeader(key, value)
            }
        }
        return builder
    }

    private fun buildRequestUrl(baseUrl: String): String {
        var target: String = endpoint
        if (target.contains("{")) {
            target = UriTemplate.fromTemplate(target).set(interpolations).expand()
        }

        if (target.contains("://")) {
            return target
        } else {
            return baseUrl + target
        }
    }

    fun headerString(name: String, value: String?) {
        headers.add(Pair(name, value ?: ""))
    }

    protected fun filterString(name: String, value: String?) {
        filters.add(Pair(name, value ?: ""))
    }

    protected fun filterInteger(name: String, value: Int?) {
        filterString(name, value.toString())
    }

    protected fun filterBoolean(name: String, value: Boolean?) {
        if (value!!) {
            filterString(name, "1")
        } else {
            filterString(name, "0")
        }
    }

    protected fun formatHeaders(headers: Headers): HashMap<String, String> {
        val result = HashMap<String, String>()
        for (name in headers.names()) {
            result.put(name.toLowerCase(), headers.get(name))
        }

        return result
    }

    private fun buildMultipartBodyBuilder(): MultipartBody.Builder {
        val builder = MultipartBody.Builder()
        for ((key, value) in forms) {
            builder.addFormDataPart(key, value)
        }

        for ((key, value) in formFiles) {
            val fileBody = value
            builder.addFormDataPart(key, fileBody.first, fileBody.second)
        }

        return builder
    }

    fun formFiles(type: String, value: Pair<String, RequestBody>) {
        formFiles.add(Pair(type, value))
    }

    fun formDate(name: String, date: DateTime) {
        val value = ISODateTimeFormat.date().print(date)
        formString(name, value)
    }

    fun formInteger(name: String, value: Int?) {
        formString(name, value.toString())
    }

    fun formString(name: String, value: String?) {
        forms.add(Pair(name, value ?: ""))
    }

    fun formDouble(name: String, value: Double?) {
        formString(name, value.toString())
    }

    private fun buildFormBody(): FormBody {
        val builder = FormBody.Builder()
        for ((key, value) in forms) {
            builder.add(key, value)
        }
        return builder.build()
    }

    protected fun buildRequestBody(): RequestBody {
        if (formFiles.isEmpty()) {
            return buildFormBody()
        } else {
            return buildMultipartBodyBuilder().build()
        }
    }

    abstract fun buildRequest(baseUrl: String): Request
}

