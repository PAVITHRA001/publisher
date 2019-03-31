package com.learn.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RequestMapping("/publisher")
@RestController
public class PublisherController {

    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    public String test(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("products_queue",false,false,false,null);
            String message= "product_details";
            channel.basicPublish("","products_queue",null,message.getBytes());
            channel.close();
            connection.close();
            return "Success";
        }
        catch (IOException | TimeoutException e){
            return e.getMessage();
        }
    }
}
