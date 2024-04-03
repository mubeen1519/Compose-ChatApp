package com.example.domain.use_case.auth_use_case

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke (email : String){
        authRepository.forgetPassword(email)
    }
}