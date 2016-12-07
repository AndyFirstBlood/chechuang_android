package zhuyl.andyfirstblood.chechuang_android.client

import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

abstract class GenericRequest<out T> : BaseRequest() {
    var type: Type

    init {
        type = GenericUtils.getSuperClassTypeArgument<T>(this, 0)!!
    }

    @Throws(IOException::class, ClientRequestException::class)
    fun processResponse(response: Response): T {
        if (response.isSuccessful) {
            return processSuccessfulResponse(response)
        } else {
            return processErrorResponse(response)
        }
    }

    @Throws(IOException::class, ClientRequestException::class)
    protected open fun processSuccessfulResponse(response: Response): T {
        return GsonUtils.fromJson(bodyString(response), type)
    }

    @Throws(IOException::class, ClientRequestException::class)
    protected open fun processErrorResponse(response: Response): T {
        throw ClientRequestException("未知错误")
    }


    @Throws(IOException::class)
    fun bodyString(response: Response): String {
        try {
            return response.body().string()
        } finally {
            response.body().close()
        }
    }
}

class ClientRequestException : CheChuangException {
    lateinit var headers: HashMap<String, String>

    constructor(detailMessage: String) : super(detailMessage) {
    }

    constructor(detailMessage: String, headers: HashMap<String, String>) : super(detailMessage) {
        this.headers = headers
    }
}

open class CheChuangException(detailMessage: String) : Exception(detailMessage)

