//задание 2

CREATE TABLE people (
    Id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    Age INTEGER CHECK(age > 0) NOT NULL,
    document BOOLEAN NOT NULL,
    car_id TEXT REFERENCES car (id)

);
CREATE TABLE car (
    Id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    prise MONEY NOT NULL,

);

//задание 3

scripts423.sql
//1
SELECT student.name, student.age, faculty.name
FROM student
INNER JOIN faculty ON student.id = faculty.id  //точка отделение названия таблицы от колонки в этой таблице
//2
SELECT student.avatar, faculty.avatar
FROM student
RIGHT JOIN faculty ON student.id = faculty.id

