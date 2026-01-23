package com.user.users_parking.Services;

import com.user.users_parking.Config.RabbitConfig;
import com.user.users_parking.Dto.Requests.RequestLinkVehicle;
import com.user.users_parking.Models.Vehicle;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class VehicleConsumer {
    private final UserServices userService;

    public VehicleConsumer(UserServices userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "vehicleQueue")
    public void receiveVehicleCreated(RequestLinkVehicle requestLinkVehicle) {
        try {
            // Aqui você linka o veículo ao usuário
            userService.linkVehicleToUser(requestLinkVehicle);
            System.out.println("Veículo vinculado com sucesso: " + requestLinkVehicle.plate());
        } catch (Exception e) {
            System.err.println("Erro ao vincular veículo: " + e.getMessage());
        }
    }
}
