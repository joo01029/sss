package com.sss.service.chat

import com.sss.domain.dto.chat.ChatRoom
import org.springframework.web.socket.WebSocketSession

interface ChatService {
    fun findAllRoom(): ArrayList<ChatRoom>
    fun findRoomById(roomId: String): ChatRoom?
    fun createRoom(name: String): ChatRoom
    fun <T> sendMessage(session: WebSocketSession, message: T)
}