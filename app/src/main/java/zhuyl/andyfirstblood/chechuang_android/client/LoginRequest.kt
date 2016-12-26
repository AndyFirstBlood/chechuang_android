package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Request
import zhuyl.andyfirstblood.chechuang_android.model.LoginObject

class LoginRequest : GenericRequest<LoginObject>() {
    fun prepare(): LoginRequest {
        endpoint = "/v1/auth"
        return this
    }

    fun formMobilePhoneNumber(mobilePhoneNumber: String): LoginRequest {
        formString("mobilePhoneNumber", mobilePhoneNumber)
        return this
    }

    fun formSmsCode(smsCode: String): LoginRequest {
        formString("smsCode", smsCode)
        return this
    }

    fun formUserName(userName: String): LoginRequest {
        formString("username", userName)
        return this
    }

    override fun buildRequest(baseUrl: String): Request {
        return buildRequestBuilder(baseUrl).post(buildRequestBody()).build()
    }

}
