package com.app.MessageScheduler.controller;


import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.entity.Request;
import com.app.MessageScheduler.entity.Response;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import com.app.MessageScheduler.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/schedule/")
public class MessageController {

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageUtil messageUtil;


    @PostMapping("/message")
    public Response scheduleMessageHandler(@RequestBody  @Valid Request requestBody , HttpServletRequest request){

        Response response;

        try {
            Client client=(Client)request.getAttribute("client");
            int result= messageUtil.saveMessage(requestBody,client);
            response= new Response(1000,"Message Scheduled Successfully");
        } catch (SqlErrorException e){
            response=new Response(e.getErrorCode(),e.getErrorMessage());
        } catch (Exception e){
            response = new Response(1004,"Something went wrong");
        }

        return response;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    Response onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.info("validation exception occured...");
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String errorMessage = fieldError.getDefaultMessage();
        Response response = new Response(1002, errorMessage);
        return response;
    }

}
