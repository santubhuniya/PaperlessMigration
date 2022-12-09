package com.paperless.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.paperless.app.datamodel.LoginResponse
import com.paperless.app.datamodel.UserProfile
import com.paperless.app.repo.SharedPrefRepo
import com.paperless.app.repo.SharedPrefRepo.Companion.USER_PROFILE

open class BaseViewModel(
    application : Application,
    val sharedPrefRepo: SharedPrefRepo
) : AndroidViewModel(application) {

    fun saveUserProfile(loginResponse: LoginResponse){
        sharedPrefRepo.saveSharedPref(SharedPrefRepo.USER_PROFILE,
            sharedPrefRepo.getSharedPref<UserProfile>(USER_PROFILE)
                ?.apply {
                    userId = loginResponse.userId
                    jwt = loginResponse.jwt
                } ?: UserProfile(
                userId = loginResponse.userId,
                jwt = loginResponse.jwt,
                profileImage = null
            )
        )
    }
    fun getUserProfile(): UserProfile? = sharedPrefRepo.getSharedPref<UserProfile>(USER_PROFILE)
}