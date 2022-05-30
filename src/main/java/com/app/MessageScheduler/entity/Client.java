package com.app.MessageScheduler.entity;

public class Client {


    private int clientId;

    private String clientName;

    private String clientToken;

    public Client(int clientId, String clientName, String clientToken) {
        super();
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientToken = clientToken;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientToken='" + clientToken + '\'' +
                '}';
    }

    public Client() {

    }
}
