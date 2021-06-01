package com.sss.controller;

import com.sss.domain.dto.chat.ChatRoom
import com.sss.lib.Logging
import com.sss.service.chat.ChatServiceImpl
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@Api(tags = arrayOf("1.USER"))
@RestController
@RequestMapping("/chat")
class ChatController {
    @Autowired
    private lateinit var chatService : ChatServiceImpl;
    @Autowired
    private lateinit var log : Logging;

    @PostMapping
    @ApiOperation(value = "createRoom", notes = "채팅방 생성")
    fun createRoom(@RequestParam name: String) : ChatRoom? {
        log.info("aaa")
        return chatService.createRoom(name);
    }

    @GetMapping
    @ApiOperation(value = "get Room list", notes = "채팅방 받아오기")
    fun findAllRoom() : ArrayList<ChatRoom>? {
        return chatService.findAllRoom();
    }
}
