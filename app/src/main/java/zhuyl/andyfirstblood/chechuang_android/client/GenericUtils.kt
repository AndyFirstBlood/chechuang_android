package zhuyl.andyfirstblood.chechuang_android.client

import java.lang.reflect.ParameterizedType

object GenericUtils {
    fun <T> getSuperClassTypeArgument(target: Any, index: Int): Class<T>? {
        return getSuperClassTypeArgument(target.javaClass, index)
    }

    fun <T> getSuperClassTypeArgument(clazz: Class<*>, index: Int): Class<T>? {
        val genericClazz = findHaveGenericSuperclass(clazz) ?: return null

        val parameterizedType = genericClazz.genericSuperclass as ParameterizedType
        return getActualTypeArguments(parameterizedType, index)
    }

    private fun findHaveGenericSuperclass(clazz: Class<*>): Class<*>? {
        var currentClass: Class<*>? = clazz

        while (currentClass != null) {
            if (currentClass.genericSuperclass is ParameterizedType) {
                return currentClass
            }

            currentClass = currentClass.superclass
        }

        return null
    }

    private fun <T> getActualTypeArguments(parameterizedType: ParameterizedType, index: Int): Class<T> {
        var type = parameterizedType.actualTypeArguments[index]
        if (type is ParameterizedType) {
            type = type.rawType
        }

        return type as Class<T>
    }
}

