package zhuyl.andyfirstblood.chechuang_android.client

import java.util.*

abstract class GenericListRequest<T> : GenericRequest<ArrayList<T>>() {
    init {
        val klass = type

        type = GenericParameterizedType(ArrayList::class.java, klass)
    }
}
