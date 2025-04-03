-- Create the database
DROP DATABASE IF EXISTS catmeme;
CREATE DATABASE IF NOT EXISTS catmeme;

-- Use the database
USE catmeme;
-- Drop tables
Drop Table IF Exists memes;

-- Create the table
CREATE TABLE IF NOT EXISTS memes (
id INT AUTO_INCREMENT PRIMARY KEY,
url TEXT NOT NULL
);

-- Insert data into the cat table
INSERT INTO memes (id, url) VALUES
(1, 'https://tenor.com/view/cat-funny-looking-camera-cat-smurf-camera-man-camera-cat-gif-1964556476916788109'),
(2, 'https://tenor.com/view/nnn0011-gif-27088152'),
(3, 'https://tenor.com/view/stare-staring-staring-c-cat-dozing-gif-11824910910481019099'),
(4, 'https://tenor.com/view/cat-angry-cat-mad-judging-cat-cat-stare-mad-cat-gif-792695931806940428'),
(5, 'https://tenor.com/view/cat-angry-angry-cat-staring-cat-staring-gif-27226858');