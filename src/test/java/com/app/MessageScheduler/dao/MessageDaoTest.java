package com.app.MessageScheduler.dao;

import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.entity.Message;
import com.app.MessageScheduler.entity.Request;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MessageDaoTest {

    @Autowired
    MessageDao messageDao;

    Logger logger = LoggerFactory.getLogger(MessageDaoTest.class);

    @Test
    void insertMessage() throws SqlErrorException {
        Client dummyclient = new Client(101,"dummy","dummytoken");
        Request request = new Request("dummy message for testing purpose", "8455997196", "2022-03-30T18:05:20");
        int actualResult = messageDao.insertMessage(request,dummyclient);
        assertEquals(1,actualResult);
    }


    @Test
    void insetMessageAsClientIsNull() {
        Client dummyclient = new Client(101,"dummy","dummytoken");
        Request request = new Request("dummy message for testing purpose", "8455997196", "2022-03-30T18:05:20");
        int actualResult = 0;
        try {
            actualResult = messageDao.insertMessage(request,null);
        }
        catch (SqlErrorException e){
            logger.info(e.getMessage());
            assertEquals(0,actualResult);
        }catch (Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
        }

    }


    @Test
    void getAllMessagesInOneMinute() throws SqlErrorException {
        List<Message> messageList= messageDao.getAllMessagesInOneMinute();
        assertThat(messageList).isNotNull();
    }

    @Test
    void updateMessageStatus() throws SqlErrorException {
        int actualResult =  messageDao.updateMessageStatus(false,true,"dummy_whatsapp_Id", LocalDateTime.now(),23);
        assertEquals(1,actualResult);
    }
}
