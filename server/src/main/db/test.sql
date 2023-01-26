-- DELETE FROM book;
ALTER TABLE book AUTO_INCREMENT = 1001;

-- DELETE FROM category;
ALTER TABLE category AUTO_INCREMENT = 1001;

INSERT INTO `category` (`name`) VALUES ('Fiction'),('Social-Science'),('Arts'),('Cooking');
-- FICTION
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Call Me By Your Name', 'André Aciman', '', 1.62, 0, False, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Where The Crawdads Sing', 'Delia Owens', '', 1.35, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Silent Patient', 'Alex Michaelides', '', 1.89, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Silent Patient', 'Alex Michaelides', '', 1.89, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('1984', 'George Orwell', '', 1.33, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Midnight Sun', 'Stephenie Meyer', '', 1.23, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Outsiders', 'S E Hinton', '', 1.11, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Four Winds', 'Kristin Hannah', '', 1.33, 0, False, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Bluest Eye', 'Toni Morrison', '', 1.33, 0, TRUE, FALSE, 1001);
-- Social Science

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Mythos', 'TStephen Fry', '', 7.95, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Outliers: Malcolm Gladwell', 'Stephen Fry','', 3.95, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Becoming', 'Michelle Obama ','', 11.95, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Crying in H Mart: A Memoir', '','Michelle Zauner', 2.95, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Coraline', 'Neil Gaiman', '',8.99, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Noise: A Flaw in Human Judgment', 'Daniel Kahneman','', 19.02, 0, FALSE, FALSE, 1002);

-- Arts

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Matisse: The Books', 'Louise Rogers Lalaurie', '',10.02, 0, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Arts: A Visual Encyclopedia', 'DK', '', 21.80, 0, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Art of Mondo', 'Mondo, Tim League','', 21.80, 0, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Arts: A Visual Encyclopedia', 'DK','', 21.80, 0, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Art: The Definitive Visual Guide', 'Andrew Graham Dixon and Ross King', '',44.99, 0, TRUE, FALSE, 1003);

-- Cooking
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Magnolia Table', 'Joanna Gaines', '',13.99, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Cook, Eat, Repeat: Ingredients, Recipes, and Stories', 'Nigella Lawson', '',14.16, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Cooking for One Cookbook: 100 Easy Recipes', 'Cindy Kerschner ','', 10.72, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Pacific Natural: Simple Seasonal Entertaining', 'Jenni Kayne ','', 34.76, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Taste: My Life Through Food', 'Stanley Tucci', '',17.59, 0, TRUE, FALSE, 1004);



delete FROM book Where title = "The Silent Patient" and category_id =1001








-- update category set name = 'SocialScience' where name = "Social Science";
-- update book set author = 'Michelle Zauner', description = '' where book_id = 1013;

-- delete from book where book_id =  1019;