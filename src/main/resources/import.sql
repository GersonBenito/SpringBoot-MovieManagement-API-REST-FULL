INSERT INTO roles VALUES(1, "ADMIN");
INSERT INTO roles VALUES(2, "USER");
INSERT INTO roles VALUES(3, "INVITED");

INSERT INTO users VALUES(1, "gerson.benito@gmail.com", "https://res.cloudinary.com/dokr7v5et/image/upload/v1695446663/ublw2vxl7fqzglaoumkk.png", "$2a$10$S/ebf0ruh7s53vzF6A0XkOSBVyJFv.MHyY5NGMnVFYT2tFNcpP3bW","Gerson Benito");
INSERT INTO users VALUES(2, "susana.montero@gmail.com", "https://res.cloudinary.com/dokr7v5et/image/upload/v1695446663/ublw2vxl7fqzglaoumkk.png", "$2a$10$S/ebf0ruh7s53vzF6A0XkOSBVyJFv.MHyY5NGMnVFYT2tFNcpP3bW","Susana Montero");
INSERT INTO users VALUES(3, "diana.soto@gmail.com", "https://res.cloudinary.com/dokr7v5et/image/upload/v1695446663/ublw2vxl7fqzglaoumkk.png", "$2a$10$S/ebf0ruh7s53vzF6A0XkOSBVyJFv.MHyY5NGMnVFYT2tFNcpP3bW","Diana Soto");

INSERT INTO user_role VALUES(1,1);
INSERT INTO user_role VALUES(2,2);
INSERT INTO user_role VALUES(3,3);

INSERT INTO genre VALUES(1, "Action");
INSERT INTO genre VALUES(2, "Science fiction");
INSERT INTO genre VALUES(3, "Animation");
INSERT INTO genre VALUES(4, "Romantic comedy");
INSERT INTO genre VALUES(5, "Documentary");
INSERT INTO genre VALUES(6, "Romance");
INSERT INTO genre VALUES(7, "Adventure");
INSERT INTO genre VALUES(8, "Police");
INSERT INTO genre VALUES(9, "History");
INSERT INTO genre VALUES(10, "Crime");

INSERT INTO stock VALUES(69, 20);
INSERT INTO stock VALUES(79, 40);

INSERT INTO movie VALUES(1, 1,"", "desc", 9.0, "posterimage", 50.0,30.0,"DHOOM", 69);
INSERT INTO movie VALUES(2, 1,"", "desc", 9.0, "posterimage", 50.0,30.0,"DHOOM2", 79);
INSERT INTO movie VALUES(3, 1,"", "desc", 9.0, "posterimage", 50.0,30.0,"DHOOM3", null);

INSERT INTO movie_genre VALUES(1,1);
INSERT INTO movie_genre VALUES(1,6);
INSERT INTO movie_genre VALUES(1,10);
INSERT INTO movie_genre VALUES(2,1);
INSERT INTO movie_genre VALUES(2,6);
