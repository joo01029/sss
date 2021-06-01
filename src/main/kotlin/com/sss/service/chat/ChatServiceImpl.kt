package com.sss.service.chat

import com.fasterxml.jackson.databind.ObjectMapper
import com.sss.domain.dto.chat.ChatRoom
import com.sss.lib.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException
import java.util.*
import javax.annotation.PostConstruct
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

@Service
class ChatServiceImpl : ChatService{
    @Autowired
    private  lateinit var logging: Logging;

    @Autowired
    private lateinit var objectMapper: ObjectMapper;
    private lateinit var chatRooms: LinkedHashMap<String, ChatRoom>;

    @PostConstruct
    private fun init() {
        chatRooms = LinkedHashMap();
    }

    override fun findAllRoom(): ArrayList<ChatRoom> {
        return ArrayList(chatRooms.values);
    }

    override fun findRoomById(roomId: String): ChatRoom? {
        return chatRooms[roomId];
    }

    override fun createRoom(name: String): ChatRoom {
        val randomId: String = UUID.randomUUID().toString();
        val chatRoom : ChatRoom = ChatRoom(randomId, name);
        chatRooms[randomId] = chatRoom;
        return chatRoom;
    }
    override fun <T> sendMessage(session: WebSocketSession, message : T){
        try{
            session.sendMessage(TextMessage(objectMapper.writeValueAsString(message)));
        } catch (e:IOException){
            logging.error(e.toString());
        }
    }
}