-- liquibase formatted sql

-- changeset Dmitriev:1
CREATE TABLE students (
    id SERIAL,
    email TEXT
)

-- changeset Dmitriev:2
 ALTER TABLE users ADD name TEXT;


-- changeset Dmitriev:2
CREATE INDEX students_index-name ON students(name);


-- changeset Dmitriev:3
CREATE INDEX faculties-index_color_name ON faculties (name, color);



