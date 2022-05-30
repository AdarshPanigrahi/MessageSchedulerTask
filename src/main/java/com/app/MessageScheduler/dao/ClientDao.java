package com.app.MessageScheduler.dao;


import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import com.app.MessageScheduler.mapperclasses.ClientMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao{

    Logger logger = LoggerFactory.getLogger(ClientDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Client getClientFromToken(String token) throws SqlErrorException{

        String query= "select * from client where clientToken= ?";
        Client client = null;
        try {
            client = jdbcTemplate.queryForObject(query, new ClientMapper(), token);
            logger.info("query--> " + query);
            logger.info("query result--> " + client.toString());
            return client;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new SqlErrorException("error while validating client using token");
        }

    }

}
