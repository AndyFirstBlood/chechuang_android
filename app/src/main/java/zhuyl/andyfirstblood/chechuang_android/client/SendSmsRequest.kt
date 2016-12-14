package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Request

class SendSmsRequest : GenericRequest<String>() {
    fun prepare(): SendSmsRequest {
        endpoint = "sms/{mobilePhoneNumber}"
        return this
    }

    fun interpolation(): SendSmsRequest {
        interpolations.put("mobilePhoneNumber", 18616367889)
        return this
    }

    override fun buildRequest(baseUrl: String): Request {
        return buildRequestBuilder(baseUrl).get().build()
    }
}

