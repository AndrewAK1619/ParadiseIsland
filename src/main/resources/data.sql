INSERT INTO user_role(id, role, description) VALUES 
  (1, "ROLE_USER", "default role for user"),
  (2, "ROLE_ADMIN", "role for admin");

INSERT INTO users(user_id, email, first_name, last_name, mobile_phone, password, pesel) VALUES
  (1, 'admin@example.com', 'John', 'Smith', '123123123', 
	'{bcrypt}$2a$10$/qm/jZiwBGAEaS8LCW695egoqzUNoLoXWpX4kUH3Ch09KvyLE1yeW', 
	'12312312312'),
  (2, 'user@example.com', 'Michael', 'Jones', '321321321', 
	'{bcrypt}$2a$10$Jvi.vrRv34OB.QpUmRK6h.pAub2/Os9o9/9pTdqAV5iS85bSavg8.', 
	'32132132132');

INSERT INTO users_roles(user_user_id, roles_id) VALUES
  (1, 2),
  (2, 1);

INSERT INTO hotels(hotel_id, description, hotel_name) VALUES
  (1, 
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
  "The Glory Hotel"),
  (2, 
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
  "Cute Mountains"),
  (3, 
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
  "Royal Place"),
  (4, 
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
  "Purple Orchid"),
  (5, 
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
  "Dream Connect"),
  (6, 
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 
  "Astro Resort");
  
INSERT INTO hotel_images(hotel_image_id, image_path, top_image, hotel_id) VALUES
  (1, 'src/main/resources/static/img/hotels/2020041811111.jpeg', 1, 1),
  (2, 'src/main/resources/static/img/hotels/2020041822222.jpg', 1, 2),
  (3, 'src/main/resources/static/img/hotels/2020041833333.jpg', 1, 3),
  (4, 'src/main/resources/static/img/hotels/2020041844444.jpg', 1, 4),
  (5, 'src/main/resources/static/img/hotels/2020041855555.jpg', 1, 5),
  (6, 'src/main/resources/static/img/hotels/2020041866666.jpg', 1, 6);

INSERT INTO room_categories(room_category_id, name) VALUES
  (1, 'Standard'),
  (2, 'High Standard'),  
  (3, 'Exclusive'),
  (4, 'Apartment');
  
INSERT INTO rooms(room_id, number_of_double_beds, number_of_single_beds, room_price, room_category_id, hotel_id) VALUES
  (1, 1, 0, 200, 1, 1),
  (2, 1, 0, 300, 3, 1),
  (3, 1, 0, 400, 4, 1),
  (4, 1, 0, 250, 2, 1),
  (5, 1, 2, 400, 3, 1),
  
  (6, 1, 0, 300, 2, 2),
  (7, 1, 2, 250, 1, 2),
  (8, 1, 0, 200, 1, 2),
  (9, 1, 0, 300, 3, 2),
  (10, 1, 0, 400, 4, 2),
  
  (11, 1, 0, 200, 1, 3),
  (12, 1, 0, 300, 3, 3),
  (13, 1, 0, 400, 4, 3),
  (14, 1, 0, 250, 2, 3),
  (15, 1, 2, 400, 3, 3),
  
  (16, 1, 0, 200, 1, 4),
  (17, 1, 0, 300, 3, 4),
  (18, 1, 0, 400, 4, 4),
  (19, 1, 0, 250, 2, 4),
  (20, 1, 2, 400, 3, 4),
  
  (21, 1, 0, 200, 1, 5),
  (22, 1, 0, 300, 3, 5),
  (23, 1, 0, 400, 4, 5),
  (24, 1, 0, 250, 2, 5),
  (25, 1, 2, 400, 3, 5),
  
  (26, 1, 0, 200, 1, 6),
  (27, 1, 0, 300, 3, 6),
  (28, 1, 0, 400, 4, 6),
  (29, 1, 0, 250, 2, 6),
  (30, 1, 2, 400, 3, 6);

  
INSERT INTO room_images(room_image_id, image_path, top_image, room_id) VALUES
  (1, 'src/main/resources/static/img/rooms/2020040211111.jpg', 1, 1),
  (2, 'src/main/resources/static/img/rooms/2020040211112.jpg', 1, 2),
  (3, 'src/main/resources/static/img/rooms/2020040211113.jpg', 1, 3),
  (4, 'src/main/resources/static/img/rooms/2020040211114.jpg', 1, 4),
  (5, 'src/main/resources/static/img/rooms/2020040211115.jpg', 1, 5),
  
  (6, 'src/main/resources/static/img/rooms/2020040222221.jpg', 1, 6),
  (7, 'src/main/resources/static/img/rooms/2020040222222.jpg', 1, 7),
  (8, 'src/main/resources/static/img/rooms/2020042122223.jpg', 1, 8),
  (9, 'src/main/resources/static/img/rooms/2020042122224.jpg', 1, 9),
  (10, 'src/main/resources/static/img/rooms/2020042122225.jpg', 1, 10),
  
  (11, 'src/main/resources/static/img/rooms/2020042133331.jpg', 1, 11),
  (12, 'src/main/resources/static/img/rooms/2020042133332.jpg', 1, 12),
  (13, 'src/main/resources/static/img/rooms/2020042133333.jpg', 1, 13),
  (14, 'src/main/resources/static/img/rooms/2020042133334.jpg', 1, 14),
  (15, 'src/main/resources/static/img/rooms/2020042133335.jpg', 1, 15),
  
  (16, 'src/main/resources/static/img/rooms/2020042144441.jpg', 1, 16),
  (17, 'src/main/resources/static/img/rooms/2020042144442.jpg', 1, 17),
  (18, 'src/main/resources/static/img/rooms/2020042144443.jpg', 1, 18),
  (19, 'src/main/resources/static/img/rooms/2020042144444.jpg', 1, 19),
  (20, 'src/main/resources/static/img/rooms/2020042144445.jpg', 1, 20),
  
  (21, 'src/main/resources/static/img/rooms/2020042155551.jpg', 1, 21),
  (22, 'src/main/resources/static/img/rooms/2020042155552.jpg', 1, 22),
  (23, 'src/main/resources/static/img/rooms/2020042155553.jpg', 1, 23),
  (24, 'src/main/resources/static/img/rooms/2020042155554.jpg', 1, 24),
  (25, 'src/main/resources/static/img/rooms/2020042155555.jpg', 1, 25),
  
  (26, 'src/main/resources/static/img/rooms/2020042166661.jpg', 1, 26),
  (27, 'src/main/resources/static/img/rooms/2020042166662.jpg', 1, 27),
  (28, 'src/main/resources/static/img/rooms/2020042166663.jpg', 1, 28),
  (29, 'src/main/resources/static/img/rooms/2020042166664.jpg', 1, 29),
  (30, 'src/main/resources/static/img/rooms/2020042166665.jpg', 1, 30);
