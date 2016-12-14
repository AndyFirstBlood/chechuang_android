package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Request
import okhttp3.Response

class CaptchaImageGetRequest : GenericRequest<Any>() {
    fun prepare(): CaptchaImageGetRequest {
        endpoint = "captcha/image"
        return this
    }

    override fun buildRequest(baseUrl: String): Request {
        return buildRequestBuilder(baseUrl).get().build()
    }

    override fun processSuccessfulResponse(response: Response): Any {
        println("response = ${response.body().string()}")
        return super.processSuccessfulResponse(response)
    }

}
