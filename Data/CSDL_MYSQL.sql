CREATE DATABASE university_db;
USE university_db;

-- Tạo bảng School (thay vì Department)
CREATE TABLE School (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department VARCHAR(100),
    department_code VARCHAR(50) NOT NULL UNIQUE
);

-- Tạo bảng Branch
CREATE TABLE Branch (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_school BIGINT,
    branch VARCHAR(100),
    branch_code VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (id_school) REFERENCES School(id)
);

-- Tạo bảng Subject
CREATE TABLE Subject (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_branch BIGINT,
    subject VARCHAR(100),
    subject_code VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (id_branch) REFERENCES Branch(id)
);

-- Tạo bảng Account
CREATE TABLE Account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gmail VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Tạo bảng User
CREATE TABLE User (
    id_account INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(50),
    identifier VARCHAR(50) UNIQUE,
    department VARCHAR(100),
    id_school BIGINT,
    FOREIGN KEY (id_account) REFERENCES Account(id),
    FOREIGN KEY (id_school) REFERENCES School(id)
);

-- Tạo bảng Curriculum
CREATE TABLE Curriculum (
    curriculum_code VARCHAR(20) PRIMARY KEY,
    identifier VARCHAR(50),
    name_curriculum VARCHAR(100),
    lecturer VARCHAR(100),
    description TEXT,
    url VARCHAR(255),
    subject VARCHAR(100),
    branch VARCHAR(100),
    department VARCHAR(100),
    subject_code VARCHAR(50),
    branch_code VARCHAR(50),
    department_code VARCHAR(50),
    FOREIGN KEY (identifier) REFERENCES User(identifier) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (subject_code) REFERENCES Subject(subject_code) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (branch_code) REFERENCES Branch(branch_code) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (department_code) REFERENCES School(department_code) ON DELETE SET NULL ON UPDATE CASCADE
);
