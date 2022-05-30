package com.app.MessageScheduler.dao;

import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.entity.Message;
import com.app.MessageScheduler.entity.Request;
import com.app.MessageScheduler.mapperclasses.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Repository
public class MessageDao {

    Logger logger = LoggerFactory.getLogger(MessageDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void welcome() {
        System.out.println("welcome to the dao.");
    }


    public int insertMessage(Request requestBody, Client client) throws SqlErrorException {
        int result = 0;
        String query = "insert into message(message,scheduledTime,destination,clientId,createTime,isPending,isScheduled) values (?,?,?,?,?,?,?)";
        try {
            result = jdbcTemplate.update(query, requestBody.getMessage(), requestBody.getScheduledTime(),
                    requestBody.getDestination(), client.getClientId(), LocalDateTime.now(), true, true);
            logger.info("query running--> " + query);
            return result;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new SqlErrorException("error encountered while doing sql operation");
        }

    }


    public List<Message> getAllMessagesInOneMinute() throws SqlErrorException {

        String query = "select * from message where isPending = true and scheduledTime < date_add(now(),interval 1 minute)";

        List<Message> messages = Collections.emptyList();
        logger.info("polling messages at " + LocalDateTime.now());
        try {
            messages = jdbcTemplate.query(query, new MessageMapper());
            return messages;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new SqlErrorException("error while polling messages from DB");
        }
    }


    public int updateMessageStatus(Boolean isPending, Boolean isSent, String apiMessageId, LocalDateTime sentTime, Integer messageId) throws SqlErrorException {
        String query = "UPDATE message set isPending = ?, isSent=?, sentTime=?, apiMessageId=? where messageId = ?";
        logger.info("updating message status for messageId " + messageId);
        int result = 0;
        try {
            result = jdbcTemplate.update(query, isPending, isSent, sentTime, apiMessageId, messageId);
            return result;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw  new SqlErrorException("error encountered while updating status for messageID "+messageId);
        }

    }

}
