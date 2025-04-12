-- Create database
DROP DATABASE IF EXISTS cat_memes;
CREATE DATABASE IF NOT EXISTS cat_memes;
-- Use database
USE cat_memes;
-- Drop tables
DROP TABLE IF EXISTS memes;
-- Create table
CREATE TABLE IF NOT EXISTS memes
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    ur          TEXT NOT NULL,
    text_top    VARCHAR(55),
    text_bottom VARCHAR(55),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);