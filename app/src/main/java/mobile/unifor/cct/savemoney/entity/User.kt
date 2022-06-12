package mobile.unifor.cct.savemoney.entity

import android.telephony.PhoneNumberUtils

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val finances: HashMap<String, Finance> = hashMapOf()
)