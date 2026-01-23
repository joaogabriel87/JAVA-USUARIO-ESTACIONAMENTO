package com.user.users_parking.Models;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "veiculos")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placa", unique = true)
    private String plate;
    @Column(name = "marca")
    private String brand;
    @Column(name = "cor")
    private String color;
    @Column(name = "modelo")
    private String model;
    @Column(name="tipo")
    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Vehicle(TypeEnum type, String model, String color, String brand, String plate) {
        this.type = type;
        this.model = model;
        this.color = color;
        this.brand = brand;
        this.plate = plate;
    }
}

