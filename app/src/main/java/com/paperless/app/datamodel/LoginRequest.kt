package com.paperless.app.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "userEmail") val userEmail : String,
    @Json(name = "password") val password : String,
    @Json(name = "loginMode") val loginMode : LoginMode,
    @Json(name = "deviceInfo") val deviceInfo : DeviceInfo
)

@JsonClass(generateAdapter = true)
data class DeviceInfo(
    @Json(name = "deviceType") val deviceType : String,
    @Json(name = "deviceName") val deviceName : String,
    @Json(name = "deviceId") val deviceId : String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "userId") val userId : Long,
    @Json(name = "jwt") val jwt : String
)
@JsonClass(generateAdapter = true)
data class LoginReponseData(
    @Json(name = "data") val loginResponse : LoginResponse?,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class UserProfile(
    @Json(name = "userId") var userId : Long?,
    @Json(name = "jwt") var jwt : String?,
    @Json(name = "profileImage") var profileImage : String?
)

enum class LoginMode{
    FINGERPRINT,
    PASSCODE
}