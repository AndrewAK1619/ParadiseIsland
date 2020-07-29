INSERT INTO user_role(id, role, description) VALUES 
  (1, "ROLE_USER", "default role for user"),
  (2, "ROLE_ADMIN", "role for admin");

INSERT INTO users(user_id, email, first_name, last_name, mobile_phone, password) VALUES
  (1, 'admin@example.com', 'John', 'Smith', '123-123-123', 
	'{bcrypt}$2a$10$/qm/jZiwBGAEaS8LCW695egoqzUNoLoXWpX4kUH3Ch09KvyLE1yeW'),
  (2, 'user@example.com', 'Michael', 'Jones', '321-321-321', 
	'{bcrypt}$2a$10$Jvi.vrRv34OB.QpUmRK6h.pAub2/Os9o9/9pTdqAV5iS85bSavg8.');

INSERT INTO users_roles(user_user_id, roles_id) VALUES
  (1, 2),
  (2, 1);

INSERT INTO airlines(airline_id, airline_name, details) VALUES
  (1, 'Speed Flight Airline', ''),
  (2, 'West Europe Airways', ''),
  (3, 'Modification Possible Airways', '');

INSERT INTO room_categories(room_category_id, name) VALUES
  (1, 'Standard'),
  (2, 'High Standard'),  
  (3, 'Exclusive'),
  (4, 'Apartment');