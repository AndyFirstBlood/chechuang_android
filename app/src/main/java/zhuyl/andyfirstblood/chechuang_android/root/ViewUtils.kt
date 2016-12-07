package zhuyl.andyfirstblood.chechuang_android.root

object ViewUtils {

    fun findContentViewId(klass: Class<*>): Int? {
        if (klass.isAnnotationPresent(BindContentView::class.java)) {
            val annotation = klass.getAnnotation(BindContentView::class.java)
            return annotation.value
        }

        val superclass = klass.superclass
        if (superclass != null) {
            return findContentViewId(superclass)
        }

        return null
    }

    fun findContentViewId(instance: Any): Int? {
        return findContentViewId(instance.javaClass)
    }
}