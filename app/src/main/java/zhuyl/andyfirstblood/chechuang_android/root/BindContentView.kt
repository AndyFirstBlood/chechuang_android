package zhuyl.andyfirstblood.chechuang_android.root

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FILE

@Retention(RUNTIME)
@Target(CLASS, FILE)
annotation class BindContentView(val value: Int)

