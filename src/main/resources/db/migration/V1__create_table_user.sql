
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE users_vehicle_id (
    user_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, vehicle_id),
    CONSTRAINT fk_user_vehicle FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE veiculos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(50) NOT NULL UNIQUE,
    marca VARCHAR(100),
    cor VARCHAR(50),
    modelo VARCHAR(100),
    tipo VARCHAR(50),
    user_id BIGINT,
    CONSTRAINT fk_vehicle_user FOREIGN KEY (user_id) REFERENCES users(id)
);