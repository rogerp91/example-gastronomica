-- Create table for Eventos
CREATE TABLE Eventos (
    id INTEGER PRIMARY KEY,
    title TEXT UNIQUE,
    color TEXT,
    category TEXT,
    fecha TEXT,
    place TEXT,
    image TEXT,
    description TEXT,
    price INTEGER,
    confirmation INTEGER,
    org_name TEXT,
    org_descrip TEXT
);

-- Create table for Cerca de Mi
CREATE TABLE Cerca (
    id INTEGER PRIMARY KEY,
    title TEXT UNIQUE,
    image TEXT,
	menu_region TEXT,
	price INTEGER,
	responseData TEXT,
	distance TEXT
);

-- Create table for Colaboradores
CREATE TABLE Colaboradores (
    id INTEGER PRIMARY KEY,
    email TEXT UNIQUE,
    first_name TEXT,
    last_name TEXT,
    avatar TEXT,
    titles TEXT,
    description TEXT
);

-- Create table for Publicaciones
CREATE TABLE Publicaciones (
    id INTEGER PRIMARY KEY,
    id_colaboradores INTEGER,
    title  TEXT,
    type TEXT,
    content  TEXT,
    location TEXT,
    latitude TEXT,
    longitude TEXT,
    client TEXT,
    imagen TEXT,
    date_publicated TEXT,
    FOREIGN KEY(id_colaboradores) REFERENCES Colaboradores(id)
);
