package com.sss.domain.dto.chat;

import com.sss.service.chat.ChatServiceImpl
import org.springframework.web.socket.WebSocketSession

class ChatRoom (val roomId:String, val name:String){
    private val sessions : HashSet<WebSocketSession> = HashSet();

    fun handleActions(session: WebSocketSession, chatMessage: ChatMessage, chatService : ChatServiceImpl){
        if(chatMessage.type.equals(ChatMessage.Message.ENTER)){
            sessions.add(session);
            chatMessage.message = chatMessage.sender + "님이 참여하셨습니다";
        }
        sendMessage(chatMessage,chatService);
    }
    fun <T> sendMessage ( message : T, chatService : ChatServiceImpl){
        sessions.parallelStream().forEach { s : WebSocketSession ->
            chatService.sendMessage(s, message)
        };
    }
}
