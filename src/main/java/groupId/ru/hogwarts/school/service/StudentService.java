package groupId.ru.hogwarts.school.service;

import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    void editStudent( Student student);

    void deleteStudent(long id);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findStudentByFacultyId(long id);
}
