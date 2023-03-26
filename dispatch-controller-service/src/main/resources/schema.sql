CREATE TABLE drone (
    id INTEGER NOT NULL AUTO_INCREMENT,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    model ENUM('Lightweight', 'Middleweight', 'Cruiserweight', 'Heavyweight') NOT NULL,
    weight_limit DECIMAL(5, 2) NOT NULL,
    battery_capacity DECIMAL(3, 2) NOT NULL,
    state ENUM('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING') NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE shipment (
    id INTEGER NOT NULL AUTO_INCREMENT,
    drone_id INTEGER NOT NULL,
    status ENUM('IN_PROGRESS', 'DELIVERED') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (drone_id) REFERENCES drone(id)
);

CREATE TABLE medication (
    id INTEGER NOT NULL AUTO_INCREMENT,
    shipment_id INTEGER NOT NULL,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    weight_limit DECIMAL(5, 2) NOT NULL,
    image VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (shipment_id) REFERENCES shipment(id)
);
