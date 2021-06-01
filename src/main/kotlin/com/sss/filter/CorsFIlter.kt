package com.sss.filter;

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(0)
class CorsFIlter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        var res : HttpServletResponse = response as HttpServletResponse;
        var req : HttpServletRequest = request as HttpServletRequest;
        res.setHeader("Access-Control-Allow-Origin", "*")
        res.setHeader("Access-Control-Allow-Credentials", "true")
        res.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE,PATCH,HEAD")
        res.setHeader("Access-Control-Max-Age", "3600")
        res.setHeader(
            "Access-Control-Allow-Headers",
            "X-Request-With, Content-Type, ContentType, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-header, Cache-Control, Pragma, Expires, Media-Type"
        )
        res.setHeader("Access-Control-Expose-Headers", "content-disposition")
        res.setHeader("X-Content-Type-Options", "nosniff")
        if (req.getMethod() == "OPTIONS") res.status = HttpServletResponse.SC_OK

        chain!!.doFilter(request, response)
    }
}
