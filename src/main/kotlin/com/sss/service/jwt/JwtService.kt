package com.sss.service.jwt

import com.sss.domain.entity.User
import com.sss.enum.JwtAuth

interface JwtService {
    fun createToken(idx: Long, authType: JwtAuth): String;
    fun validateToken(token: String?): User?;
    fun refreshToken(refreshToken: String?): String?;
}