 DROP DATABASE IF EXISTS piesshopdb;

 CREATE DATABASE IF NOT EXISTS piesshopdb;
 
 USE piesshopdb;

CREATE TABLE `pie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `price` double NOT NULL,
  `filename` varchar(200) NOT NULL,
  `ingredients` varchar(200) NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE pie;

INSERT INTO `pie` (`name`, `price`, `filename`, `ingredients`,`description`) VALUES
('Spanakopita', 4, 'images/spanakopita.jpg', 'Spinach, Feta','Indulge your taste buds in a culinary journey with our exquisite Spanakopita pies, a delightful blend of flaky, golden pastry and a sumptuous filling that captures the essence of Greek cuisine. Immerse yourself in layers of buttery phyllo dough, expertly crafted to perfection, enveloping a rich and savory mixture of spinach, feta cheese, and aromatic herbs. Each bite unveils a harmonious marriage of textures and flavors, with the crispiness of the crust giving way to the creamy, tangy notes of feta and the earthy freshness of spinach. Perfectly seasoned and baked to a golden brown, our Spanakopita pies offer a taste of the Mediterranean that will transport you to the sun-soaked landscapes of Greece. Savor the tradition and craftsmanship in every slice, as these pies embody the spirit of authenticity and culinary excellence. Elevate your dining experience with our Spanakopita pies – a savory symphony that promises to delight your senses and leave you craving for more.'),
('Manitaropita', 5.5, 'images/manitaropita.jpg', 'Mushrooms, Butter','Delight your palate with our Manitaropita, a savory pie that elevates the earthy richness of mushrooms to culinary perfection. Crafted with meticulous care, this pie features a luscious blend of sautéed mushrooms enveloped in layers of flaky phyllo dough, creating a symphony of textures that captivates with every bite. The indulgent combination of tender mushrooms and rich butter creates a filling that is both hearty and sophisticated, offering a taste experience that transcends the ordinary.'),
('Prasopita', 3.5, 'images/prasopita.jpg', 'Leeks, Feta','Introducing our Prasopita – a culinary masterpiece that celebrates the delicate harmony of leeks and feta within a flaky embrace of golden phyllo pastry. Immerse yourself in the subtle yet distinct flavors of this savory pie, where the sweetness of leeks and the creamy tang of feta unite to create a symphony of taste thats both comforting and sophisticated.'),
('Boureki', 4.5, 'images/boureki.jpg', 'Zucchini, Potatoes','Embark on a gastronomic adventure with our Boureki, a culinary marvel that brings together the rustic charm of zucchini and the comforting wholesomeness of potatoes. Immerse yourself in a symphony of flavors and textures as layers of thinly sliced zucchini and potatoes intermingle within a golden phyllo embrace, creating a pie thats both visually stunning and delectably satisfying.'),
('Tyropita', 2.5, 'images/tyropita.jpg', 'Cheese, Phyllo','Indulge in the timeless allure of our Tyropita, a delectable Greek pastry that pays homage to the rich tradition of combining cheese and flaky phyllo dough. A symphony of flavors awaits as layers of creamy cheese seamlessly intertwine with the crispiness of golden-brown phyllo, creating a savory experience thats both comforting and sophisticated.'),
('Kolokythopita', 5.5, 'images/kolokythopita.jpg', 'Zucchini, Olive oil','Embark on a culinary journey with our Kolokythopita, a tantalizing Greek pie that celebrates the essence of zucchini and the subtle allure of olive oil. Immerse yourself in the symphony of flavors as layers of thinly sliced zucchini meld with the richness of olive oil, all encased in a golden phyllo embrace. This pie is a harmonious blend of earthy and savory notes, promising a taste experience that transports you to the sun-drenched landscapes of the Mediterranean.'),
('Kimadopita', 3.5, 'images/kimadopita.jpg', 'Beef, Eggs','Embark on a gastronomic adventure with our Kimadopita, a savory Greek pie that masterfully marries the hearty essence of beef with the richness of eggs. Immerse yourself in the robust flavors and textures as seasoned beef is encased in layers of golden-brown phyllo, creating a pie thats both satisfying and full of culinary intrigue.');

CREATE TABLE `area` (
  `id` int NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP table area;

INSERT INTO area (id,description) VALUES
(1, 'Ampelokipoi'),
(2, 'Papagou'),
(3, 'Athens center'),
(4, 'Zografou'),
(5, 'Kifisia'),
(6, 'Nea Smyrni'),
(7, 'Chalandri'),
(8, 'Glyfada'),
(9, 'Marousi'),
(10, 'Kolonaki');

CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `area_id` int NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `tel` varchar(20) NOT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `offer` tinyint DEFAULT NULL,
  `payment` varchar(20) DEFAULT NULL,
  `stamp` datetime NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_fk_area` (`area_id`),
  KEY `order_fk_user` (`user_id`),
  CONSTRAINT `order_fk_area` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`),
  CONSTRAINT `order_fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE piesshopdb.order;

CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `pie_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `order_item_fk_order` (`order_id`),
  KEY `order_item_fk_pie` (`pie_id`),
  CONSTRAINT `order_item_fk_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `order_item_fk_pie` FOREIGN KEY (`pie_id`) REFERENCES `pie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE piesshopdb.order_item;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `fullname` varchar(200) NOT NULL,
  `email` varchar(50) NOT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `session` varchar(45) DEFAULT NULL,
  `salt` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE piesshopdb.user;

SELECT * FROM user;

DELETE FROM user;

CREATE TABLE piesshopdb.role (
    role_id INT PRIMARY KEY,
    description VARCHAR(45)
);

DROP table piesshopdb.role;

INSERT INTO piesshopdb.role (role_id,description)
VALUES (1,'admin'),
       (2,'customer');

SELECT * FROM role;
	
CREATE TABLE piesshopdb.user_role (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

DROP table piesshopdb.user_role;

INSERT INTO user_role (user_id,role_id)
VALUES (1,1),
       (2,2);

SELECT * FROM user_role;