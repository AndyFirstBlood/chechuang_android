package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Request

class SendSmsRequest : GenericRequest<String>() {
    fun prepare(): SendSmsRequest {
        endpoint = "/v1/sms/{mobilePhoneNumber}"
        return this
    }

    fun interpolation(mobilePhoneNumber: String): SendSmsRequest {
        interpolations.put("mobilePhoneNumber", mobilePhoneNumber)
        return this
    }

    override fun buildRequest(baseUrl: String): Request {
        return buildRequestBuilder(baseUrl).get().build()
    }
}

