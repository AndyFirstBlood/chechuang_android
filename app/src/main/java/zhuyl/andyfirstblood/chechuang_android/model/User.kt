package zhuyl.andyfirstblood.chechuang_android.model
class User : Entity() {
    lateinit var username: String
    lateinit var mobilePhoneNumber: String
    var mobilePhoneVerified: Boolean = false
    var emailVerified: Boolean = false
}

