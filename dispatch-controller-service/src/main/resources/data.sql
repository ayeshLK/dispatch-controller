-- Insert dummy data for Drone table
INSERT INTO drone (serial_number, model, weight_limit, battery_capacity, state)
VALUES ('ABC123', 'Lightweight', 400.00, 0.80, 'IDLE'),
       ('DEF456', 'Middleweight', 500.00, 0.75, 'LOADING'),
       ('GHI789', 'Cruiserweight', 600.00, 0.70, 'LOADED'),
       ('JKL012', 'Heavyweight', 700.00, 0.65, 'DELIVERING'),
       ('MNO345', 'Lightweight', 400.00, 0.60, 'DELIVERED'),
       ('PQR678', 'Middleweight', 500.00, 0.55, 'RETURNING'),
       ('STU901', 'Cruiserweight', 600.00, 0.50, 'IDLE'),
       ('VWX234', 'Heavyweight', 700.00, 0.45, 'LOADING'),
       ('YZA567', 'Lightweight', 400.00, 0.40, 'LOADED'),
       ('BCD890', 'Middleweight', 500.00, 0.35, 'DELIVERED');

-- Insert dummy data for Shipment table
INSERT INTO shipment (drone_id, status)
VALUES (2, 'IN_PROGRESS'),
       (3, 'IN_PROGRESS'),
       (4, 'IN_PROGRESS'),
       (5, 'DELIVERED'),
       (6, 'IN_PROGRESS'),
       (8, 'IN_PROGRESS'),
       (9, 'IN_PROGRESS'),
       (10, 'DELIVERED');

-- Insert dummy data for Medication table
INSERT INTO medication (shipment_id, code, name, weight_limit, image)
VALUES (1, 'MED001', 'Medication 1', 100.00, 'https://example.com/medication1.png'),
       (1, 'MED002', 'Medication 2', 100.00, 'https://example.com/medication2.png'),
       (2, 'MED003', 'Medication 3', 100.00, 'https://example.com/medication3.png'),
       (2, 'MED004', 'Medication 4', 150.00, 'https://example.com/medication4.png'),
       (3, 'MED005', 'Medication 5', 100.00, 'https://example.com/medication5.png'),
       (3, 'MED006', 'Medication 6', 250.00, 'https://example.com/medication6.png'),
       (4, 'MED007', 'Medication 7', 300.00, 'https://example.com/medication7.png'),
       (4, 'MED008', 'Medication 8', 200.00, 'https://example.com/medication8.png'),
       (5, 'MED009', 'Medication 9', 60.00, 'https://example.com/medication9.png'),
       (5, 'MED010', 'Medication 10', 80.00, 'https://example.com/medication10.png'),
       (6, 'MED011', 'Medication 11', 90.00, 'https://example.com/medication11.png'),
       (6, 'MED012', 'Medication 12', 90.00, 'https://example.com/medication12.png'),
       (7, 'MED015', 'Medication 15', 200.00, 'https://example.com/medication15.png'),
       (8, 'MED016', 'Medication 16', 100.00, 'https://example.com/medication16.png'),
       (8, 'MED017', 'Medication 17', 150.00, 'https://example.com/medication17.png'),
       (8, 'MED018', 'Medication 18', 180.00, 'https://example.com/medication18.png'),
       (8, 'MED019', 'Medication 19', 70.00, 'https://example.com/medication19.png');
