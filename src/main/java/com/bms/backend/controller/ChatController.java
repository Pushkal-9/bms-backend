package com.bms.backend.controller;

import com.bms.backend.models.Message;
import com.bms.backend.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ChatController {

    private Set<String> users = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message) {
        handleUserStatus(message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        message.setMessageStatus(Status.DELIVERED);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        return message;
    }

    @MessageMapping("/message-read")
    public void handleMessageRead(@Payload Message message) {
        message.setMessageStatus(Status.READ);
        simpMessagingTemplate.convertAndSendToUser(message.getSenderName(), "/read", message);
    }


    @MessageMapping("/typing")
    public void handleTyping(@Payload Message message) {
        if (message.getStatus() == Status.TYPING) {
            simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/typing", message);
        }
    }


    private void handleUserStatus(Message message) {
        if (message.getStatus() == Status.JOIN) {
            users.add(message.getSenderName());
        } else if (message.getStatus() == Status.LEAVE) {
            users.remove(message.getSenderName());
        }
        broadcastUserList();
    }

    private void broadcastUserList() {
        Message userListMessage = new Message();
        userListMessage.setUserList(new ArrayList<>(users));
        simpMessagingTemplate.convertAndSend("/chatroom/userlist", userListMessage);
    }
}