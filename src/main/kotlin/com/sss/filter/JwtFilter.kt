package com.sss.filter;

import com.sss.domain.entity.User
import com.sss.lib.AuthorizationConfilm
import com.sss.service.jwt.JwtService
import org.apache.catalina.core.ApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(2)
class JwtFilter(private val handlerExceptionResolver: HandlerExceptionResolver) : Filter {
    @Autowired
    private lateinit var jwtService: JwtService

    override fun init(filterConfig: FilterConfig) {
        val ctx: WebApplicationContext = WebApplicationContextUtils
            .getRequiredWebApplicationContext(filterConfig.servletContext)
        jwtService = ctx.getBean(JwtService::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            val token: String = AuthorizationConfilm.subString(request as HttpServletRequest, "Bearer")
            if (request.method != "OPTIONS") {
                val user: User? = jwtService.validateToken(token)

                request.setAttribute("user", user)
            }
            chain.doFilter(request, response)
        } catch (e: Exception) {
            handlerExceptionResolver
                .resolveException(request as HttpServletRequest, response as HttpServletResponse, null, e)
        }

    }
}
