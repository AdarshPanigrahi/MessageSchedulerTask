package com.app.MessageScheduler.entity;

public class Message {

    Integer messageId;

    String message;

    Integer clientId;

    String destination;

    String createTime;

    String scheduledTime;

    boolean isScheduled;

    boolean isPending;

    String sentTime;

    boolean isSent;

    String apiMessageId;

    public Message(Integer messageId, String message, Integer clientId, String destination, String createTime, String scheduledTime, boolean isScheduled, boolean isPending, String sentTime, boolean isSent, String apiMessageId) {
        this.messageId = messageId;
        this.message = message;
        this.clientId = clientId;
        this.destination = destination;
        this.createTime = createTime;
        this.scheduledTime = scheduledTime;
        this.isScheduled = isScheduled;
        this.isPending = isPending;
        this.sentTime = sentTime;
        this.isSent = isSent;
        this.apiMessageId = apiMessageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getApiMessageId() {
        return apiMessageId;
    }

    public void setApiMessageId(String apiMessageId) {
        this.apiMessageId = apiMessageId;
    }


    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", clientId=" + clientId +
                ", destination='" + destination + '\'' +
                ", createTime='" + createTime + '\'' +
                ", scheduledTime='" + scheduledTime + '\'' +
                ", isScheduled=" + isScheduled +
                ", isPending=" + isPending +
                ", sentTime='" + sentTime + '\'' +
                ", isSent=" + isSent +
                ", apiMessageId='" + apiMessageId + '\'' +
                '}';
    }

    public Message() {
        super();
    }
}
