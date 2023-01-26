CREATE TABLE IF NOT EXISTS `films` (
    `film_id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255),
    `description` varchar(255),
    `release_date` date,
    `duration` int,
    `mpa_rating_id` int
);

CREATE TABLE IF NOT EXISTS `users` (
    `user_id` int PRIMARY KEY AUTO_INCREMENT,
    `email` varchar(255),
    `login` varchar(255),
    `name` varchar(255),
    `birthday` date
);

CREATE TABLE IF NOT EXISTS `user_friends` (
    `user_id` int,
    `friend_id` int
);

CREATE TABLE IF NOT EXISTS `film_likes` (
    `film_id` int,
    `user_id` int
);

CREATE TABLE IF NOT EXISTS `film_genres` (
    `film_id` int PRIMARY KEY,
    `genre_id` int
);

CREATE TABLE IF NOT EXISTS `genres` (
    `genre_id` int PRIMARY KEY,
    `name` varchar(255)
);

CREATE TABLE IF NOT EXISTS `mpa_ratings` (
    `mpa_rating_id` int PRIMARY KEY,
    `name` varchar(255)
);

ALTER TABLE `film_genres` ADD FOREIGN KEY (`film_id`) REFERENCES `films` (`film_id`);

ALTER TABLE `film_likes` ADD FOREIGN KEY (`film_id`) REFERENCES `films` (`film_id`);

ALTER TABLE `films` ADD FOREIGN KEY (`mpa_rating_id`) REFERENCES `mpa_ratings` (`mpa_rating_id`);

ALTER TABLE `user_friends` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `user_friends` ADD FOREIGN KEY (`friend_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `film_likes` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `film_genres` ADD FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`);
