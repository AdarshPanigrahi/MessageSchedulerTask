package com.app.MessageScheduler.mapperclasses;

import com.app.MessageScheduler.entity.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {

        Client client = new Client();
        client.setClientId(rs.getInt("clientId"));
        client.setClientName(rs.getString("clientName"));
        client.setClientToken(rs.getString("clientToken"));
        return client;
    }
}
