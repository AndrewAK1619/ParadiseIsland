INSERT INTO users(id_user, email, first_name, last_name, mobile_phone, password, pesel) VALUES
  (1, 'example1@example.com', 'Jan', 'Kowalski', '600600600', 'user1', '90101222457'),
  (2, 'example2@example.com', 'Maciej', 'Zalewski', '605605605', 'user2', '87112242456'),
  (3, 'example3@example.com', 'Aneta', 'Korczyńska', '610610610', 'user3', '76061536749'),
  (4, 'example4@example.com', 'Wojciech', 'Sokolik', '615615615', 'user4', '82010877245');

INSERT INTO room_categories(id_room_category, name) VALUES
  (1, 'Standard'),
  (2, 'High Standard'),  
  (3, 'Exclusive'),
  (4, 'Apartment');
  
INSERT INTO rooms(id_room, number_of_double_beds, number_of_single_beds, room_price, room_category) VALUES
  (1, 0, 1, 150, 1),
  (2, 1, 0, 200, 2),
  (3, 0, 1, 150, 1),
  (4, 1, 0, 200, 3),
  (5, 1, 0, 200, 4),
  (6, 0, 1, 150, 1);