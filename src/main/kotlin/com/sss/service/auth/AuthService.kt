package com.sss.service.auth

import com.sss.domain.dto.jwt.res.TokensRes
import com.sss.domain.dto.user.req.UserCreateDto
import com.sss.domain.dto.user.req.UserLoginDto
import com.sss.domain.dto.user.res.UserLoginRes

interface AuthService {
    fun createUser(userCreateDto: UserCreateDto)
    fun login(userLoginRes: UserLoginDto): UserLoginRes
}