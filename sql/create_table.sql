## 创建数据库
CREATE DATABASE IF NOT EXISTS class_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

## 学生表
CREATE TABLE student (
                         studentNumber INT AUTO_INCREMENT PRIMARY KEY COMMENT '系统编号',
                         id VARCHAR(10) NOT NULL UNIQUE COMMENT '学号',
                         name VARCHAR(50) NOT NULL COMMENT '姓名',
                         birthday DATE NOT NULL COMMENT '出生日期',
                         age INT NOT NULL COMMENT '年龄',
                         sex ENUM('男', '女') NOT NULL COMMENT '性别'
) COMMENT '学生表';

## 插入学生数据
INSERT INTO student (id, name, birthday, age, sex)
VALUES
    ('S001', '张山', '1992-02-03', 23, '男'),
    ('S002', '王水', '1999-02-03', 33, '女'),
    ('S003', '刘云', '1992-01-03', 21, '男'),
    ('S004', '李天', '1984-02-03', 24, '男'),
    ('S005', '谢空', '2021-02-03', 26, '女');

## 教师表
CREATE TABLE lecturer (
                          lecturerNumber INT AUTO_INCREMENT PRIMARY KEY COMMENT '系统编号',
                          id VARCHAR(10) NOT NULL UNIQUE COMMENT '工号',
                          name VARCHAR(50) NOT NULL COMMENT '姓名',
                          birthday DATE NOT NULL COMMENT '出生日期',
                          age INT NOT NULL COMMENT '年龄',
                          sex ENUM('男', '女') NOT NULL COMMENT '性别',
                          course VARCHAR(100) NOT NULL COMMENT '担任课程'
) COMMENT '教师表';

## 插入教师数据
INSERT INTO lecturer (id, name, birthday, age, sex, course)
VALUES
    ('A001', '李一', '1990-01-01', 33, '男', '高级程序设计'),
    ('A002', '王二', '1992-02-12', 30, '男', '高等数学'),
    ('A003', '刘三', '1996-05-09', 32, '女', '大学语文'),
    ('A004', '赵四', '2000-04-01', 28, '男', '离散数学'),
    ('A005', '闫五', '1991-12-01', 30, '女', '线性代数');
