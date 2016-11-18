INSERT INTO hotels (city, address) VALUES ('Innopolis', 'Universitetskaya 2/1');
INSERT INTO hotels (city, address) VALUES ('Annapa', 'Universitetskaya 2/2');
INSERT INTO hotels (city, address) VALUES ('Boston', 'Universitetskaya 2/3');
INSERT INTO hotels (city, address) VALUES ('Cabimas', 'Universitetskaya 2/3');
INSERT INTO hotels (city, address) VALUES ('Moscow', 'Universitetskaya 2/3');

INSERT INTO employees (name, lastName) VALUES ('Admin', 'Admin');
INSERT INTO users (emp_id, login, password) VALUES (1, 'admin', 'password');
INSERT INTO administrators (user_login, hotel_id) VALUES ('admin', 1);

INSERT INTO employees(name, lastName) VALUES ('Manager', 'Manager');
INSERT INTO users (emp_id, login, password) VALUES (2, 'manager', 'password');
INSERT INTO managers (user_login) VALUES ('manager');
UPDATE hotels SET manager_login = 'manager' WHERE id = 1;

INSERT INTO employees (name, lastName) VALUES ('staff1', 'staff1'), ('staff2', 'staff2'), ('staff3', 'staff3');
INSERT INTO staff (emp_id, hotel_id) VALUES (3, 1), (4, 1), (5, 1);

CREATE INDEX staff_fkey_emp ON staff (emp_id);
CREATE INDEX staff_fkey_hotel ON staff (hotel_id);
CREATE INDEX users_fkey ON users (emp_id);
CREATE INDEX administrators_fkey ON administrators (user_login);
CREATE INDEX managers_fkey ON managers (user_login);
CREATE INDEX reservations_fkey_room ON reservations (hotel_id, room_number);

CREATE INDEX reservations_st_date ON reservations (st_date);
CREATE INDEX reservations_end_date ON reservations (end_date);

CREATE INDEX hotels_by_city ON hotels (city ASC);