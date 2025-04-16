-- Create database
DROP DATABASE IF EXISTS cat_memes;
CREATE DATABASE IF NOT EXISTS cat_memes;

-- Use database
USE cat_memes_test;

-- Drop tables
DROP TABLE IF EXISTS memes;

-- Create table
CREATE TABLE IF NOT EXISTS memes
(
id          INT AUTO_INCREMENT PRIMARY KEY,
url          TEXT NOT NULL,
text_top    VARCHAR(55),
text_bottom VARCHAR(55),
created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample data
INSERT INTO memes (url, text_top, text_bottom) VALUES
('https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/memes/pepe1713241230001.png', 'When the build works', 'But the deploy fails'),
('https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/memes/pepe1713241240002.png', 'Hibernate', 'Why you so angry'),
('https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/memes/pepe1713241250003.png', 'When the logs say', 'NullPointerException'),
('https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/memes/pepe1713241260004.png', 'Your S3 permissions are wrong', 'Again'),
('https://bug-busters-cat-meme.s3.us-east-2.amazonaws.com/memes/pepe1713241270005.png', 'Elastic Beanstalk', 'Is secretly plotting against me');