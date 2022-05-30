package com.app.MessageScheduler.util;

import com.app.MessageScheduler.dao.ClientDao;
import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationUtil {

    Logger logger = LoggerFactory.getLogger(AuthorizationUtil.class);

    @Autowired
    ClientDao clientDao;

    public Client validateToken(String token) throws SqlErrorException {
        logger.info("in validate token service");
        Client client = clientDao.getClientFromToken(token);
        return client;
    }

}
