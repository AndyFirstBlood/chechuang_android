package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Request
import okhttp3.Response
import zhuyl.andyfirstblood.chechuang_android.model.LoginObject
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

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

    override fun buildRequest(baseUrl: String): Request {
        return buildRequestBuilder(baseUrl).post(buildRequestBody()).build()
    }

    override fun processErrorResponse(response: Response): LoginObject {
        if (HTTP_UNAUTHORIZED == response.code()) {
            throw ClientRequestException("用户名或密码输入有误，请重试")
        }
        return super.processErrorResponse(response)
    }
}
