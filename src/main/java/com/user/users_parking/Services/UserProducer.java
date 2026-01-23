package com.user.users_parking.Services;

import com.user.users_parking.Config.RabbitConfig;
import com.user.users_parking.Models.Users;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

        public void  sendUserCreated(Users user){
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "userRoutingKey", user);
        }
}
