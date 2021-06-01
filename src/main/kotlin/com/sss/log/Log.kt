package com.sss.log

import org.slf4j.LoggerFactory
import org.slf4j.Logger

interface Log {
    val logger: Logger? get() = LoggerFactory.getLogger(this.javaClass)
}