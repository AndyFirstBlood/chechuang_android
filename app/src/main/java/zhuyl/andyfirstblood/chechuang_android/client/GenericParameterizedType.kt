package zhuyl.andyfirstblood.chechuang_android.client

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class GenericParameterizedType : ParameterizedType {
    var _rawType: Type
    var _actualTypeArguments: Array<out Type>

    constructor(rawType: Type, vararg actualTypeArguments: Type) {
        this._rawType = rawType
        this._actualTypeArguments = actualTypeArguments
    }

    override fun getRawType(): Type {
        return _rawType
    }

    override fun getOwnerType(): Type? {
        return null
    }

    override fun getActualTypeArguments(): Array<out Type> {
        return _actualTypeArguments
    }
}
