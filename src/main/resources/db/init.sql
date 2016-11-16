DROP DATABASE IF EXISTS hotel;

CREATE USER hotel_adm WITH PASSWORD 'Zz20164209';
CREATE DATABASE hotel OWNER hotel_adm;
\connect hotel hotel_adm

CREATE TABLE EKeys (
  id SERIAL PRIMARY KEY
);

CREATE TABLE Employees (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  lastName VARCHAR NOT NULL,
  key_id INTEGER REFERENCES EKeys
);

CREATE TABLE Users (
  emp_id INTEGER REFERENCES Employees ON DELETE CASCADE,
  login VARCHAR PRIMARY KEY,
  password VARCHAR
);

CREATE TABLE Owners (
  user_login VARCHAR PRIMARY KEY REFERENCES Users ON DELETE CASCADE
);

CREATE TABLE Hotels (
  id SERIAL PRIMARY KEY ,
  city VARCHAR NOT NULL ,
  address VARCHAR NOT NULL ,
  owner_login VARCHAR REFERENCES Owners,
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
  lastName VARCHAR NOT NULL ,
  phone VARCHAR NOT NULL
);

CREATE TABLE Rooms (
  hotel_id INTEGER REFERENCES Hotels ON DELETE CASCADE ,
  number INTEGER NOT NULL CHECK (number > 0) ,
  capacity INTEGER NOT NULL ,
  PRIMARY KEY (hotel_id, number)
);

CREATE TABLE Reservations (
  id SERIAL PRIMARY KEY ,
  guest_mail VARCHAR REFERENCES Guests ON DELETE CASCADE ,
  room_number INTEGER ,
  hotel_id INTEGER ,
  st_date DATE ,
  end_date DATE ,
  key_id INTEGER REFERENCES EKeys,
  approved BOOLEAN,
  FOREIGN KEY (hotel_id, room_number) REFERENCES Rooms ON DELETE CASCADE
);

CREATE TABLE RoomsKeys (
  hotel_id INTEGER ,
  room_number INTEGER ,
  key_id INTEGER REFERENCES EKeys ON DELETE CASCADE ,
  FOREIGN KEY (hotel_id, room_number) REFERENCES Rooms ON DELETE CASCADE ,
  PRIMARY KEY (hotel_id, room_number, key_id)
);
