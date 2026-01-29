package com.user.users_parking.Services;

import com.user.users_parking.Config.RabbitConfig;
import com.user.users_parking.Dto.Requests.RequestLinkVehicle;
import com.user.users_parking.Models.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class VehicleConsumer {
    private final UserServices userService;
    private static final Logger logger = LoggerFactory.getLogger(VehicleConsumer.class);
    public VehicleConsumer(UserServices userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "vehicleQueue")
    public void receiveVehicleCreated(RequestLinkVehicle requestLinkVehicle) {
            logger.info("Serviço rabbit linkVehicle iniciado");
            userService.linkVehicleToUser(requestLinkVehicle);
            logger.info("Serviço rabbit linkVehicle finalizado");

    }
}
