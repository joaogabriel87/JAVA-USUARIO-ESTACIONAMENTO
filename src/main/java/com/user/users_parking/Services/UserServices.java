package com.user.users_parking.Services;

import com.user.users_parking.Dto.Requests.RequestLinkVehicle;
import com.user.users_parking.Dto.Response.ResponseFindUserByEmail;
import com.user.users_parking.Models.Users;
import com.user.users_parking.Models.Vehicle;
import com.user.users_parking.Repository.UserRepository;
import com.user.users_parking.Repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public UserServices(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseFindUserByEmail findByEmail(String email){
        Optional<Users> users =  userRepository.findUserByEmail(email);
        if (users.isEmpty()){
            return new ResponseFindUserByEmail(
                    null,
                    null
            );
        }

        return new ResponseFindUserByEmail(
                users.get().getName(),
                users.get().getEmail()
        );
    }
    @Transactional
    public void linkVehicleToUser(RequestLinkVehicle requestLinkVehicle) {
        Users user = userRepository.findUserByEmail(requestLinkVehicle.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Vehicle vehicle = vehicleRepository.findByPlate(requestLinkVehicle.plate())
                .orElseGet(() -> new Vehicle(
                        requestLinkVehicle.type(),
                        requestLinkVehicle.model(),
                        requestLinkVehicle.color(),
                        requestLinkVehicle.brand(),
                        requestLinkVehicle.plate()
                ));

        vehicle.setUser(user);

        if (!user.getVehicles().contains(vehicle)) {
            user.getVehicles().add(vehicle);
        }

        vehicleRepository.save(vehicle);
    }
}
