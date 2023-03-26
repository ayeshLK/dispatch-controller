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
       ('BCD890', 'Middleweight', 500.00, 0.35, 'DELIVERING');

-- Insert dummy data for Shipment table
INSERT INTO shipment (drone_id)
VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);

-- Insert dummy data for Medication table
INSERT INTO medication (shipment_id, code, name, image)
VALUES (1, 'MED001', 'Medication 1', 'medication1.jpg'),
       (1, 'MED002', 'Medication 2', 'medication2.jpg'),
       (2, 'MED003', 'Medication 3', 'medication3.jpg'),
       (2, 'MED004', 'Medication 4', 'medication4.jpg'),
       (3, 'MED005', 'Medication 5', 'medication5.jpg'),
       (3, 'MED006', 'Medication 6', 'medication6.jpg'),
       (4, 'MED007', 'Medication 7', 'medication7.jpg'),
       (4, 'MED008', 'Medication 8', 'medication8.jpg'),
       (5, 'MED009', 'Medication 9', 'medication9.jpg'),
       (5, 'MED010', 'Medication 10', 'medication10.jpg'),
       (6, 'MED011', 'Medication 11', 'medication11.jpg'),
       (6, 'MED012', 'Medication 12', 'medication12.jpg'),
       (7, 'MED013', 'Medication 13', 'medication13.jpg'),
       (7, 'MED014', 'Medication 14', 'medication14.jpg'),
       (8, 'MED015', 'Medication 15', 'medication15.jpg'),
       (8, 'MED016', 'Medication 16', 'medication16.jpg'),
       (9, 'MED017', 'Medication 17', 'medication17.jpg'),
       (9, 'MED018', 'Medication 18', 'medication18.jpg'),
       (10, 'MED019', 'Medication 19', 'medication19.jpg'),
       (10, 'MED020', 'Medication 20', 'medication20.jpg');
