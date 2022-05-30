package com.app.MessageScheduler.apiCall;

import com.app.MessageScheduler.entity.Message;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import com.app.MessageScheduler.util.MessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

@Component
public class Scheduler extends TimerTask {

    @Autowired
    MessageUtil messageUtil;

    Logger logger = LoggerFactory.getLogger(Scheduler.class);
    public static String encodeParam(String data) {
        String result = "";
        try {
            result = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static byte[] getParamsByte(Map<String, Object> params) {
        byte[] result = null;
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(encodeParam(param.getKey()));
            postData.append('=');
            postData.append(encodeParam(String.valueOf(param.getValue())));
        }
        result = postData.toString().getBytes(StandardCharsets.UTF_8);
        return result;
    }




    @Override
    public void run() {
        List<Message> messageList = null;

        try {
            messageList=messageUtil.pollMessagesFromDatabase();
        }catch (SqlErrorException e){
            logger.info(e.getMessage());
            return;
        }

        if (messageList.isEmpty()) {
            logger.info("messagelist is empty");
            return;
        }


        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection con = null;

        for(Message ms : messageList){
            logger.info("Running for message id :"+ ms.getMessageId());

            try{
                url = new URL("https://api.gupshup.io/sm/api/v1/msg");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);

                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("apikey", "uqy1sl0viia4sz40hdnddgvph4fbrrxk");
                con.setRequestProperty("Accept", "application/json");

                OutputStream outputStream = con.getOutputStream();

                HashMap<String, String> message = new HashMap<String, String>();
                message.put("type", "text");
                message.put("text", ms.getMessage());

                String jsonString = gson.toJson(message);
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                Map<String, Object> body = new HashMap<>();
                body.put("channel", "whatsapp");
                body.put("source", "917834811114");
                body.put("destination", ms.getDestination());
                body.put("message", jsonObject);
                body.put("src.name", "MessageScheduler");

                outputStream.write(getParamsByte(body));
                logger.info("outputstream here--> " + outputStream);
                logger.info("response code here--> " + con.getResponseCode());

                if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> response = objectMapper.readValue(con.getInputStream(), Map.class);
                    logger.info("response--> " + response.toString());
                    int result = messageUtil.updateMessageStatus(false, true, response.get("messageId"), LocalDateTime.now(), ms.getMessageId());
                    if (result < 1) {
                        logger.info("Error occured while updating status..");
                    } else logger.info("Status of message is updated--> " + result);
                } else {

                    int result = messageUtil.updateMessageStatus(false, false, null, null, ms.getMessageId());
                    logger.info("Message sending failed for mesageId " + ms.getMessageId());
                }

            }catch (Exception e) {

                logger.info("Exception occured during sending messages through Gupshup Whatsapp API");
                e.printStackTrace();
            }
        }

    }
}
