DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, '2020-03-01 10:15:00', 'description-1(user1)', 500),
  (100000, '2020-03-01 11:15:00', 'description-2(user1)', 600),
  (100001, '2020-03-01 12:15:00', 'description-1(user2)', 700),
  (100001, '2020-03-01 13:15:00', 'description-2(user2)', 800);
