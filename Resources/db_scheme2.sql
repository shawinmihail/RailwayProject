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
INSERT INTO Ways (way_name) VALUES ("E");
INSERT INTO Ways (way_name) VALUES ("F");
INSERT INTO Ways (way_name) VALUES ("G");


INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A1","A",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A2","A",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A3","A",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A4","A",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A5","A",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A6","A",5);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A7","A",6);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A8","A",7);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A9","A",8);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A10","A",9);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A11","A",10);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A12","A",11);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A13","A",12);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A14","A",13);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A15","A",14);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A16","A",15);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("A17","A",16);

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B1","B",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B2","B",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B3","B",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B4","B",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B5","B",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B6","B",5);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B7","B",6);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B8","B",7);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B9","B",8);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B10","B",9);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B11","B",10);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B12","B",11);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B13","B",12);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("B14","B",13);

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C1","C",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C2","C",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C3","C",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C4","C",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C5","C",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C6","C",5);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C7","C",6);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C8","C",7);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C9","C",8);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("C10","C",9);

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D1","D",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D2","D",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D3","D",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D4","D",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D5","D",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D6","D",5);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D7","D",6);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("D8","D",7);

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("E1","E",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("E2","E",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("E3","E",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("E4","E",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("E5","E",4);

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F1","F",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F2","F",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F3","F",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F4","F",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F5","F",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F6","F",5);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F7","F",6);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F8","F",7);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F9","F",8);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("F10","F",9);

INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G1","G",0);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G2","G",1);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G3","G",2);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G4","G",3);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G5","G",4);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G6","G",5);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G7","G",6);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G8","G",7);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G9","G",8);
INSERT INTO Stations (station_name,way_name,station_index) VALUES ("G10","G",9);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A1","A2",17);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A2","A3",21);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A3","A4",12);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A4","A5",10);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A5","A6",25);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A6","A7",40);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A7","A8",31);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A8","A9",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A9","A10",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A10","A11",24);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A11","A12",12);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A12","A13",8);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A13","A14",19);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A14","A15",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A15","A16",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A16","A17",25);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B1","B2",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B2","B3",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B3","B4",19);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B4","B5",18);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B5","B6",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B6","B7",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B7","B8",19);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B8","B9",18);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B9","B10",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B10","B11",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B11","B12",19);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B12","B13",18);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B13","B14",15);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C1","C2",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C2","C3",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C3","C4",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C4","C5",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C5","C6",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C6","C7",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C7","C8",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C8","C9",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("C9","C10",13);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D1","D2",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D2","D3",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D3","D4",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D4","D5",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D5","D6",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D6","D7",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("D7","D8",14);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("E1","E2",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("E2","E3",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("E3","E4",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("E4","E5",13);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F1","F2",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F2","F3",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F3","F4",15);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F4","F5",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F5","F6",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F6","F7",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F7","F8",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F8","F9",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F9","F10",13);

INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G1","G2",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G2","G3",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G3","G4",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G4","G5",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G5","G6",16);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G6","G7",11);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G7","G8",14);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G8","G9",13);
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G9","G10",13);


INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("A1","B1");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A1","B1",0);
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("A10","C4");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("A10","C4",0);
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("B5","E1");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B5","E1",0);
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("B9","C10");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("B9","C10",0);
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("F1","D5");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F1","D5",0);
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("F5","A13");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("F5","A13",0);
INSERT INTO Intersections (first_station_name,second_station_name) VALUES ("G8","A17");
INSERT INTO Times (first_station_name,second_station_name,time) VALUES ("G8","A17",0);


INSERT INTO Trains (train_id,train_name) VALUES (1,"Route1");
INSERT INTO Trains (train_id,train_name) VALUES (2,"Route2");
INSERT INTO Trains (train_id,train_name) VALUES (3,"Route3");
INSERT INTO Trains (train_id,train_name) VALUES (4,"Route4");
INSERT INTO Trains (train_id,train_name) VALUES (5,"Route5");


INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B14",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B13",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B12",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B11",3);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B10",4);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B9",5);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B8",6);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B7",7);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B6",8);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B5",9);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B4",10);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B3",11);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B2",12);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","B1",13);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A1",14);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A2",15);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A3",16);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A4",17);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A5",18);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A6",19);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A7",20);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A8",21);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A9",22);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A10",23);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A11",24);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A12",25);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","A13",26);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","F5",27);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","F6",28);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","F7",29);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","F8",30);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","F9",31);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route1","F10",32);

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","E5",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","E4",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","E3",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","E2",3);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","E1",4);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","B5",5);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","B6",6);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","B7",7);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","B8",8);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","B9",9);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C10",10);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C9",11);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C8",12);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C7",13);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C6",14);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C5",15);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C4",16);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C3",17);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C2",18);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route2","C1",19);

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","D1",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","D2",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","D3",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","D4",3);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","D5",4);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","F1",5);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","F2",6);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","F3",7);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","F4",8);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","F5",9);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","A13",10);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","A14",11);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","A15",12);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","A16",13);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","A17",14);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G8",15);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G7",16);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G6",17);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G5",18);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G4",19);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G3",20);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G2",21);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route3","G1",22);

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route4","G8",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route4","G9",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route4","G10",2);

INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route5","D5",0);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route5","D6",1);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route5","D7",2);
INSERT INTO Routes (train_name,station_name,station_index) VALUES ("Route5","D8",3);


INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("E5","G1",0.25);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("E5","F8",0.08);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("E4","C3",0.11);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("E4","A17",0.32);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("E4","G10",0.04);

INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("B14","D8",0.32);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("B14","A7",0.01);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("B14","G1",0.19);

INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("D1","G1",0.49);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("D1","D8",1.23);
INSERT INTO LoadsGrowth (stationFrom_name,stationTo_name,amount) VALUES ("D1","F10",0.62);
