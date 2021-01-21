CREATE TABLE IF NOT EXISTS Users (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT    NOT NULL,
    phone    TEXT    NOT NULL
                     UNIQUE,
    username TEXT    NOT NULL
                     UNIQUE,
    password TEXT    NOT NULL
                     UNIQUE
)

//====Users
create table if not exists Users(
	id  INTEGER PRIMARY KEY AUTOINCREMENT,
	name text not null,
	phone text not null UNIQUE,
	username text not null unique,
	password text not null UNIQUE
)


//===Categories
create table if not exists Categories(
	id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	name text not null UNIQUE CHECK(length(name)>0)
)

//===Products with foreign key
create table if not exists Products(
	id  INTEGER PRIMARY KEY AUTOINCREMENT,
	name text not null,
	price text not null UNIQUE,
	categoryId INT not null,
	FOREIGN KEY (categoryId) REFERENCES Categories(id)
)