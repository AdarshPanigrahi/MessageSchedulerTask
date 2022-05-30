package com.app.MessageScheduler.entity;

public class Request {

    private String message;

    private String destination;

    private String scheduledTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "message='" + message + '\'' +
                ", destination='" + destination + '\'' +
                ", scheduledTime='" + scheduledTime + '\'' +
                '}';
    }

    public Request(String message, String destination, String scheduledTime) {
        this.message = message;
        this.destination = destination;
        this.scheduledTime = scheduledTime;
    }
}
