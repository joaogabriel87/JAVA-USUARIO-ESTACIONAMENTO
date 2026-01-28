package com.user.users_parking.Services;

import com.user.users_parking.Controller.UserController;
import com.user.users_parking.Dto.Requests.RequestLinkVehicle;
import com.user.users_parking.Dto.Response.ResponseFindUserByEmail;
import com.user.users_parking.Models.Users;
import com.user.users_parking.Models.Vehicle;
import com.user.users_parking.Repository.UserRepository;
import com.user.users_parking.Repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    public UserServices(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseFindUserByEmail findByEmail(String email){
        logger.info("Busca por ususario {} começada", email);

        Optional<Users> users =  userRepository.findUserByEmail(email);
        if (users.isEmpty()){
            logger.warn("User nao encontrado {}", email);
            return new ResponseFindUserByEmail(
                    null,
                    null
            );
        }
        logger.info("Busca por ususario {} finalizada", users);

        return new ResponseFindUserByEmail(
                users.get().getName(),
                users.get().getEmail()
        );
    }
    @Transactional
    public void linkVehicleToUser(RequestLinkVehicle requestLinkVehicle) {
        logger.info("Processo de vincular o veiculo com o usuario começado");

        Users user = userRepository.findUserByEmail(requestLinkVehicle.email())
                .orElseThrow(() -> {
                    logger.warn("User não encontrado: {}", requestLinkVehicle.email());
                    return new IllegalArgumentException("Usuário não encontrado");
                });

        Vehicle vehicle = vehicleRepository.findByPlate(requestLinkVehicle.plate())
                .orElseGet(() -> {
                    logger.info("Veiculo ainda nao cadastrado {}, criando um novo", requestLinkVehicle.plate());
                    return new Vehicle(
                            requestLinkVehicle.type(),
                            requestLinkVehicle.model(),
                            requestLinkVehicle.color(),
                            requestLinkVehicle.brand(),
                            requestLinkVehicle.plate()
                    );
                });

        vehicle.setUser(user);

        if (!user.getVehicles().contains(vehicle)) {
            user.getVehicles().add(vehicle);
        }
        logger.info("Veiculo com o usuario {} finalizado", user);
        vehicleRepository.save(vehicle);
    }
}
