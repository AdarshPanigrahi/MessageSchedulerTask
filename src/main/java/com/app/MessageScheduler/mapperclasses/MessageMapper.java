package com.app.MessageScheduler.mapperclasses;

import com.app.MessageScheduler.entity.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {


    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {

        Message message = new Message();
        message.setMessageId(rs.getInt("messageId"));
        message.setMessage(rs.getString("message"));
        message.setScheduledTime(rs.getString("scheduledTime"));
        message.setDestination(rs.getString("destination"));

        message.setClientId(rs.getInt("clientId"));
        message.setCreateTime(rs.getString("createTime"));
        message.setScheduled(rs.getBoolean("isScheduled"));
        message.setSentTime(rs.getString("sentTime"));

        message.setSent(rs.getBoolean("isSent"));
        message.setApiMessageId(rs.getString("apiMessageId"));

        return message;
    }
}
