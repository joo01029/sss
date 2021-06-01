package com.sss.service.auth

import com.sss.domain.dto.jwt.res.TokensRes
import com.sss.domain.dto.user.req.UserCreateDto
import com.sss.domain.dto.user.req.UserLoginDto
import com.sss.domain.dto.user.res.UserLoginRes
import com.sss.domain.entity.User
import com.sss.domain.repository.UserRepo
import com.sss.enum.JwtAuth
import com.sss.service.jwt.JwtService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException

@Service
class AuthServiceImpl : AuthService {
    @Autowired
    private lateinit var userRepo: UserRepo;
    @Autowired
    private lateinit var jwtService: JwtService;

    @Transactional
    override fun createUser(userCreateDto: UserCreateDto) {
        try {
            val id: String = userCreateDto.id

            val existUser: User? = userRepo.findById(id)
            if (existUser != null) {
                throw HttpClientErrorException(HttpStatus.ALREADY_REPORTED, "이미 존재하는 유저")
            }
            val modelMapper = ModelMapper()
            val user : User = modelMapper.map(userCreateDto, User::class.java)
            userRepo.save(user)
        }catch (e:Exception){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    override fun login(useLoginDto: UserLoginDto) : UserLoginRes {
        try{
            val id : String = useLoginDto.id
            val existUser : User = userRepo.findById(id)
                ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저를 발견하지 못했습니다")
            if(existUser.password != useLoginDto.password){
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다")
            }

            val accessToken : String = jwtService.createToken(existUser.idx!!, JwtAuth.ACCESS)
            val refreshToken : String = jwtService.createToken(existUser.idx!!, JwtAuth.REFRESH)

            return UserLoginRes(accessToken, refreshToken)
        }catch (e:Exception){
            throw e;
        }
    }

}