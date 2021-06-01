package com.sss.lib


import com.sss.log.Log
import org.springframework.stereotype.Component
import java.util.*

@Component
class Logging {
    companion object : Log;

    fun info(message : String){
        val str =   "\n======================================================="+
                    "\n["+ Date().toString()+"]"+" : "+ message +
                    "\n======================================================="
        logger?.info(str);
    }
    fun error(message : String){
        val str =   "\n======================================================="+
                    "\n["+ Date().toString()+"]"+" : "+ message +
                    "\n======================================================="
        logger?.error(str);
    }
}