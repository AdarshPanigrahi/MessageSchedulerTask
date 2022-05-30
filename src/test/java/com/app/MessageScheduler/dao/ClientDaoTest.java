package com.app.MessageScheduler.dao;

import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ClientDaoTest {

    @Autowired
    ClientDao clientDao;


    Logger logger = LoggerFactory.getLogger(ClientDaoTest.class);

    @Test
    void getClientUsingToken() throws SqlErrorException {
        String token = "dummytoken";
        Client expectedClient = new Client(101, "dummy", "dummytoken");
        Client actualResult = clientDao.getClientFromToken(token);
        assertThat(actualResult.toString()).isEqualTo(expectedClient.toString());
    }


    @Test
    void getClientUsingTokenInvalid() {
        Client actualResult = null;
        try {
            actualResult = clientDao.getClientFromToken("Invalid token");
            System.out.println("actualresult " + actualResult);
        } catch (SqlErrorException e) {
            logger.info(e.getMessage());
            assertThat(actualResult).isEqualTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
