package zhuyl.andyfirstblood.chechuang_android.client

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object GsonUtils {
    fun <T> fromJson(json: String, type: Type): T {
        return gson().fromJson<T>(json, type)
    }

    fun <T> toJson(src: T, type: Type): String {
        return gson().toJson(src, type)
    }

    fun <T> toJson(src: T): String {
        return gson().toJson(src)
    }

    fun gson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create()
    }
}
