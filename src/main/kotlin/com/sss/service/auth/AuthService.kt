package com.sss.service.auth

import com.sss.domain.dto.user.UserCreateDto

interface AuthService {
    fun createUser(userCreateDto:UserCreateDto);
}