package groupId.ru.hogwarts.school.service;

import groupId.ru.hogwarts.school.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {


    Student addStudent(Student student);

    Student findStudent(long id);

    void editStudent(Student student);

    void deleteStudent(long id);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findStudentByFacultyId(long id);

    List<Student> findAllPage(Integer pageNumber, Integer pageSize);

    void saveAvatar(long id, MultipartFile file);

    boolean deleteAvatar(long id);

    List<Student> findAllStudentsNameThisA(); //STREAM

    double findAllStudentsMiddleAge();

    long findSummaStudents();
}
