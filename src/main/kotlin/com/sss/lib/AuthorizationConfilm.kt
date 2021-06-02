package com.sss.lib;

import org.apache.logging.log4j.util.Strings
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuthorizationConfilm {
    companion object {
        fun subString(req: HttpServletRequest, type: String): String {
            val token : Enumeration<String> = req.getHeaders("Authorization")

            if(token.hasMoreElements()){
                val value:String = token.nextElement()
                if(value.toLowerCase().startsWith(type.toLowerCase())){
                    return value.substring(type.length).trim()
                }
            }

            return Strings.EMPTY;
        }
    }
}
