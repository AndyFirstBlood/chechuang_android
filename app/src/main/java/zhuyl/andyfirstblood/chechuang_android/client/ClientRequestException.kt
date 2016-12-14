package zhuyl.andyfirstblood.chechuang_android.client

import java.util.*

class ClientRequestException : CheChuangException {
    lateinit var headers: HashMap<String, String>

    constructor(detailMessage: String) : super(detailMessage) {
    }

    constructor(detailMessage: String, headers: HashMap<String, String>) : super(detailMessage) {
        this.headers = headers
    }
}
