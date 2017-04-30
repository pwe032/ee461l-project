package com.example.a123cook;

import java.util.Date;

/**
 * The Message class helps to create chat messages between
 * two people in a conversation.
 * By: Gaurav Nagar
 * Date: 4/29/2017
 */
public class Message {

    private String message;
    private String senderID;
    private String senderName;
    private long timestamp;

    public Message() {} //for Firebase data snapshot

    public Message(String message, User sender) {
        this.message = message;
        this.senderID = sender.getUserID();
        this.senderName = sender.getName();
        this.timestamp = new Date().getTime();
    }

    public Message(String message, String senderID, String senderName) {
        this.message = message;
        this.senderID = senderID;
        this.senderName = senderName;
        this.timestamp = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
