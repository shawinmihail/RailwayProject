CREATE TABLE Ways
(
    way_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    way_name TEXT UNIQUE NOT NULL
);

CREATE TABLE Stations
(
    station_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    station_name TEXT UNIQUE NOT NULL,
    station_index INTEGER NOT NULL,
    way_name TEXT NOT NULL,
    UNIQUE(way_name,station_index),
    FOREIGN KEY(way_name) REFERENCES Ways(way_name)
);

CREATE TABLE Intersections
(
    intersection_id INTEGER PRIMARY KEY NOT NULL,
    first_station_name TEXT NOT NULL,
    second_station_name TEXT NOT NULL,
    UNIQUE(first_station_name,second_station_name),
    FOREIGN KEY(first_station_name) REFERENCES Stations(station_name),
    FOREIGN KEY(second_station_name) REFERENCES Stations(station_name)
);

CREATE TABLE LoadsGrowth
(
    stationFrom_name TEXT NOT NULL,
    stationTo_name TEXT NOT NULL,
    amount DOUBLE NOT NULL,

    UNIQUE(stationFrom_name,stationTo_name),
    FOREIGN KEY(stationFrom_name) REFERENCES Stations(station_name),
    FOREIGN KEY(stationTo_name) REFERENCES Stations(station_name)
);

CREATE TABLE Trains
(
    train_id INTEGER PRIMARY KEY,
    train_name TEXT UNIQUE NOT NULL
);

CREATE TABLE Routes
(
    train_name TEXT NOT NULL,
    station_name TEXT NOT NULL,
    station_index INTEGER NOT NULL,

    UNIQUE (train_name,station_index),
    FOREIGN KEY(station_name) REFERENCES Stations(station_name),
    FOREIGN KEY(train_name) REFERENCES Trains(train_name)
);

CREATE TABLE Times
(

    first_station_name TEXT NOT NULL,
    second_station_name TEXT NOT NULL,
    time INTEGER NOT NULL,

    UNIQUE (first_station_name,second_station_name),
    FOREIGN KEY(first_station_name) REFERENCES Stations(station_name),
    FOREIGN KEY(second_station_name) REFERENCES Stations(station_name)
);

INSERT INTO Ways (way_name) VALUES ("A");
INSERT INTO Ways (way_name) VALUES ("B");
INSERT INTO Ways (way_name) VALUES ("C");
INSERT INTO Ways (way_name) VALUES ("D");

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A1","A",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A2","A",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A3","A",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A4","A",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A5","A",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B1","B",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B2","B",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B3","B",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B4","B",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B5","B",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C1","C",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C2","C",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C3","C",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C4","C",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C5","C",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D1","D",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D2","D",1);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A1","A2",17);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A2","A3",21);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A3","A4",12);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A4","A5",10);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B1","B2",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B2","B3",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B3","B4",19);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B4","B5",18);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C1","C2",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C2","C3",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C3","C4",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C4","C5",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D1","D2",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A3","B3",0);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A4","C2",0);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C4","D1",0);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A5","A1",0);

INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("A3","B3");
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("A4","C2");
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("C4","D1");




INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("A1","D2",5);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("A2","D2",3);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("A3","D1",8);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("A4","C3",8);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("A4","D2",4);

INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("B1","C1",3);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("B2","A4",14);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("B3","C1",14);

INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("C5","B5",19);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("C4","B5",13);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("C3","B4",3);

INSERT INTO Trains (train_id,train_name) VALUES (1,"Train1");
INSERT INTO Trains (train_id,train_name) VALUES (2,"Train2");
INSERT INTO Trains (train_id,train_name) VALUES (3,"Train3");

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","A1",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","A2",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","A3",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","A4",3);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","C2",4);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","C3",5);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","C4",6);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","D1",7);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train1","D2",8);

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","B1",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","B2",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","B3",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","A3",3);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","A4",4);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","C2",5);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train2","C1",6);

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","C5",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","C4",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","C3",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","C2",3);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","A4",4);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","A3",5);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","B3",6);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","B4",7);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Train3","B5",8);
