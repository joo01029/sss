package com.sss.domain.repository

import com.sss.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepo : JpaRepository<User, Long> {
    fun findById(id:String) : User?
    fun findByIdx(idx:Long) : User?
}