package com.sss.service.auth

import com.sss.domain.dto.user.UserCreateDto
import com.sss.domain.entity.User
import com.sss.domain.repository.UserRepo
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

    @Transactional
    override fun createUser(userCreateDto: UserCreateDto) {
        try {
            var id: String = userCreateDto.id;

            var existUser: User? = userRepo.findById(id)
            if (existUser != null) {
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "이미 존재하는 유저");
            }
            val modelMapper = ModelMapper();
            var user : User = modelMapper.map(userCreateDto, User::class.java)
            userRepo.save(user);
        }catch (e:Exception){
            throw e;
        }
    }

}