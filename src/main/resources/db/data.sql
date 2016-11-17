INSERT INTO hotels (city, address) VALUES ('Innopolis', 'Universitetskaya 2/1');

INSERT INTO rooms (hotel_id, number) VALUES (1, 101);
INSERT INTO rooms (hotel_id, number) VALUES (1, 102);
INSERT INTO rooms (hotel_id, number) VALUES (1, 103);
INSERT INTO rooms (hotel_id, number) VALUES (1, 104);
INSERT INTO rooms (hotel_id, number) VALUES (1, 105);

INSERT INTO employees (name, lastName) VALUES ('Admin', 'Admin');
INSERT INTO users (emp_id, login, password) VALUES (1, 'admin', 'password');
INSERT INTO administrators (user_login, hotel_id) VALUES ('admin', 1);

INSERT INTO employees(name, lastName) VALUES ('Owner', 'Owner');
INSERT INTO users (emp_id, login, password) VALUES (2, 'owner', 'password');
INSERT INTO owners (user_login) VALUES ('owner');
UPDATE hotels SET owner_login = 'owner' WHERE id = 1;

INSERT INTO employees (name, lastName) VALUES ('staff1', 'staff1'), ('staff2', 'staff2'), ('staff3', 'staff3');
INSERT INTO staff (emp_id, hotel_id) VALUES (3, 1), (4, 1), (5, 1);