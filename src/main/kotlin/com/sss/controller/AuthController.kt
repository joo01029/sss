package com.sss.controller

import com.sss.domain.dto.user.req.UserCreateDto
import com.sss.domain.dto.user.req.UserLoginDto
import com.sss.domain.dto.user.res.UserLoginRes
import com.sss.domain.response.Response
import com.sss.domain.response.ResponseData
import com.sss.service.auth.AuthService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    private lateinit var authService: AuthService;

    @PostMapping("/user")
    @ApiOperation(value= "회원가입")
    fun createUser(@RequestBody @Valid userCreateDto: UserCreateDto) : Response{
        try{
            authService.createUser(userCreateDto);
            return Response(HttpStatus.OK, "성공");
        }catch (e : HttpClientErrorException){
            throw e;
        }catch (e : Exception){
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    fun login(@RequestBody @Valid userLoginDto: UserLoginDto) : ResponseData<UserLoginRes>{
        try{
            val userLoginRes: UserLoginRes = authService.login(userLoginDto);
            return ResponseData(HttpStatus.OK,"성공", userLoginRes);
        }catch (e : HttpClientErrorException){
            throw e;
        }catch (e : Exception){
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }
}