package com.sss.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import com.sss.filter.JwtFilter

@Configuration
class JwtFilterConfig (private val handlerExceptionResolver : HandlerExceptionResolver){
    @Bean
    fun authFilter(): FilterRegistrationBean<JwtFilter> {
        val registrationBean: FilterRegistrationBean<JwtFilter> = FilterRegistrationBean<JwtFilter>()
        registrationBean.filter = JwtFilter(handlerExceptionResolver)

        registrationBean.order = 2

        return registrationBean
    }
}