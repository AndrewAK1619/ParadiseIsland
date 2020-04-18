INSERT INTO users(user_id, email, first_name, last_name, mobile_phone, password, pesel) VALUES
  (1, 'example1@example.com', 'Jan', 'Kowalski', '600600600', 'user1', '90101222457'),
  (2, 'example2@example.com', 'Maciej', 'Zalewski', '605605605', 'user2', '87112242456'),
  (3, 'example3@example.com', 'Aneta', 'Korczyńska', '610610610', 'user3', '76061536749'),
  (4, 'example4@example.com', 'Wojciech', 'Sokolik', '615615615', 'user4', '82010877245');

INSERT INTO room_categories(room_category_id, name) VALUES
  (1, 'Standard'),
  (2, 'High Standard'),  
  (3, 'Exclusive'),
  (4, 'Apartment');
  
INSERT INTO rooms(room_id, number_of_double_beds, number_of_single_beds, room_price, room_category_id) VALUES
  (1, 1, 0, 200, 1),
  (2, 1, 0, 300, 3),
  (3, 1, 0, 400, 4),
  (4, 1, 0, 250, 2),
  (5, 0, 2, 400, 3),
  (6, 1, 0, 300, 2),
  (7, 0, 2, 250, 1);
  
INSERT INTO room_images(room_image_id, image_path, top_image, room_id) VALUES
  (1, 'src/main/resources/static/img/rooms/2020040211111.jpg', 1, 1),
  (2, 'src/main/resources/static/img/rooms/2020040222222.jpg', 1, 2),
  (3, 'src/main/resources/static/img/rooms/2020040233333.jpg', 1, 3),
  (4, 'src/main/resources/static/img/rooms/2020040244444.jpg', 1, 4),
  (5, 'src/main/resources/static/img/rooms/2020040255555.jpg', 1, 5),
  (6, 'src/main/resources/static/img/rooms/2020040266666.jpg', 1, 6),
  (7, 'src/main/resources/static/img/rooms/2020040277777.jpg', 1, 7);
  
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