package com.example.a123cook;

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
    private String receiverID;
    private String receiverName;
    private long timestamp;

    public Message() {} //for Firebase data snapshot

    public Message(String message, User sender, User receiver, long timestamp) {
        this.message = message;
        this.senderID = sender.getUserID();
        this.senderName = sender.getName();
        this.receiverID = receiver.getUserID();
        this.receiverName = receiver.getName();
        this.timestamp = timestamp;
    }

    public Message(String message, String senderID, String senderName, String receiverID,
                   String receiverName, long timestamp) {
        this.message = message;
        this.senderID = senderID;
        this.senderName = senderName;
        this.receiverID = receiverID;
        this.receiverName = receiverName;
        this.timestamp = timestamp;
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

    public String getReceiverID() {
        return receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
