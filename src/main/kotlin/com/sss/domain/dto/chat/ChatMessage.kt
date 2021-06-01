package com.sss.domain.dto.chat

class ChatMessage (var type : Message, var roomId:String, var sender:String, var message:String){
    public enum class Message{
        ENTER, TALK
    }

}