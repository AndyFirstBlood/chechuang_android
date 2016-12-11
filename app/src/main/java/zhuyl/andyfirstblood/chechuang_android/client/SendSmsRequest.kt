package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Request

class SendSmsRequest : GenericListRequest<String>() {
    fun prepare(): SendSmsRequest {
        endpoint = "/api/v1/sms/{mobilePhoneNumber}"
        return this
    }

    fun interpolation(): SendSmsRequest {
        interpolations.put("mobilePhoneNumber", 15751158939)
        return this
    }

    override fun buildRequest(baseUrl: String): Request {
        return buildRequestBuilder().get().build()
    }
}

