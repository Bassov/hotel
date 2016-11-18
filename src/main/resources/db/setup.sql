DROP DATABASE IF EXISTS hotel;

CREATE USER hotel_adm WITH PASSWORD 'Zz20164209';
CREATE DATABASE hotel OWNER hotel_adm;
\connect hotel hotel_adm

CREATE TABLE Employees (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  lastName VARCHAR NOT NULL
);

CREATE TABLE Users (
  emp_id INTEGER REFERENCES Employees ON DELETE CASCADE,
  login VARCHAR PRIMARY KEY,
  password VARCHAR
);

CREATE TABLE Managers (
  user_login VARCHAR PRIMARY KEY REFERENCES Users ON DELETE CASCADE
);

CREATE TABLE Hotels (
  id SERIAL PRIMARY KEY ,
  city VARCHAR NOT NULL ,
  address VARCHAR NOT NULL ,
  manager_login VARCHAR REFERENCES Managers,
  UNIQUE (city, address)
);

CREATE TABLE Administrators (
  user_login VARCHAR PRIMARY KEY REFERENCES Users ON DELETE CASCADE ,
  hotel_id INTEGER REFERENCES Hotels ON DELETE CASCADE
);

CREATE TABLE Staff (
  emp_id INTEGER PRIMARY KEY REFERENCES Employees ON DELETE CASCADE ,
  hotel_id INTEGER REFERENCES Hotels ON DELETE CASCADE
);

CREATE TABLE Guests (
  mail VARCHAR PRIMARY KEY ,
  name VARCHAR NOT NULL ,
  lastName VARCHAR NOT NULL
);

CREATE TABLE Rooms (
  hotel_id INTEGER REFERENCES Hotels ON DELETE CASCADE ,
  number INTEGER NOT NULL CHECK (number > 0) ,
  capacity INTEGER NOT NULL DEFAULT 2 ,
  PRIMARY KEY (hotel_id, number)
);

CREATE TABLE Reservations (
  id SERIAL PRIMARY KEY ,
  guest_mail VARCHAR REFERENCES Guests ON DELETE CASCADE ,
  room_number INTEGER ,
  hotel_id INTEGER ,
  st_date DATE ,
  end_date DATE ,
  approved BOOLEAN DEFAULT FALSE ,
  FOREIGN KEY (hotel_id, room_number) REFERENCES Rooms ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION create_rooms() RETURNS TRIGGER AS $$
DECLARE
  i INTEGER;
  stDate DATE;
  endDate DATE;
BEGIN
  --
  -- Perform the required operation on hotels to create rooms
  --
  << outer_loop >>
  FOR i IN 1..1000 LOOP
    INSERT INTO rooms (hotel_id, number) VALUES (NEW.id, i);
  END loop outer_loop;

  FOR i IN 1..200000 LOOP
    INSERT INTO Employees (name, lastName) VALUES (CONCAT('Mansur'), 'Khazeev');
  END LOOP;
  RETURN new;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_rooms_for_hotel
AFTER INSERT ON hotels
FOR EACH ROW EXECUTE PROCEDURE create_rooms();
