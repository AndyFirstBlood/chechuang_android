package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.OkHttpClient
import rx.Observable
import rx.schedulers.Schedulers
import java.io.IOException

class CheChuangClient(val client: OkHttpClient, val baseUrl: String, val userAgent: String) {
    @Throws(IOException::class, ClientRequestException::class)
    fun <T> invoke(request: GenericRequest<T>): T {
        var client = this.client
//        if (request is AuthorizedRequest) {
//            client = client.newBuilder().addInterceptor(RefreshTokenInterceptor(tokenHolder)).build()
//        }

        request.headerString("User-Agent", userAgent)
        val httpRequest = request.buildRequest(baseUrl)
        val call = client.newCall(httpRequest)
        val response = call.execute()
        return request.processResponse(response)
    }

    fun <T> rxInvoke(request: GenericRequest<T>): Observable<T> {
        return Observable.fromCallable {
            invoke(request)
        }.subscribeOn(Schedulers.io()).first()
    }
}
