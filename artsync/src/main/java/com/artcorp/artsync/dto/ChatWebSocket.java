package com.artcorp.artsync.dto;

import com.artcorp.artsync.entity.Chat;
import org.springframework.web.multipart.MultipartFile;

public class ChatWebSocket {
    private MultipartFile media;
    private Chat chat;

    public ChatWebSocket() {
    }

    public ChatWebSocket(MultipartFile media, Chat chat) {
        this.media = media;
        this.chat = chat;
    }

    public MultipartFile getMedia() {
        return media;
    }

    public void setMedia(MultipartFile media) {
        this.media = media;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
