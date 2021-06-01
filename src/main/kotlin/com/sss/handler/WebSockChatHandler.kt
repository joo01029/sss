package com.sss.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.sss.domain.dto.chat.ChatMessage
import com.sss.domain.dto.chat.ChatRoom
import com.sss.lib.Logging
import com.sss.service.chat.ChatServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.lang.Exception


@Component
class WebSockChatHandler : TextWebSocketHandler() {
    @Autowired
    private lateinit var objectMapper: ObjectMapper;

    @Autowired
    private lateinit var chatService: ChatServiceImpl;

    @Autowired
    private lateinit var logging: Logging;

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val payload = message.payload
            logging.info("payload " + payload);
            var chatMessage: ChatMessage = objectMapper.readValue(payload, ChatMessage::class.java);
            var room: ChatRoom? = chatService.findRoomById(chatMessage.roomId);
            room?.handleActions(session, chatMessage, chatService);
        } catch (e: Exception) {
            throw e;
        }
    }
}