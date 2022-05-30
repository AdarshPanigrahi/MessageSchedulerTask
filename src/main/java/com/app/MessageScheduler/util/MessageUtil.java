package com.app.MessageScheduler.util;

import com.app.MessageScheduler.dao.MessageDao;
import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.entity.Message;
import com.app.MessageScheduler.entity.Request;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageUtil {

    @Autowired
    MessageDao messageDao;

    public int saveMessage(Request requestBody, Client client) throws SqlErrorException{
        return messageDao.insertMessage(requestBody,client);
    }

    public int updateMessageStatus(Boolean isPending, Boolean isSent, String apiMessageId, LocalDateTime sentTime, Integer messageId) throws SqlErrorException {

        return messageDao.updateMessageStatus(isPending, isSent, apiMessageId, sentTime, messageId);
    }

    public List<Message> pollMessagesFromDatabase() throws SqlErrorException {
        return messageDao.getAllMessagesInOneMinute();
    }
}
