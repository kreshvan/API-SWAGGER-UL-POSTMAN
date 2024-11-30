package groupId.ru.hogwarts.school.service;

import groupId.ru.hogwarts.school.controller.FacultyRepository;
import groupId.ru.hogwarts.school.exepcion.StudentNotFoundException;
import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;
import groupId.ru.hogwarts.school.controller.StudentRepository;
import org.webjars.NotFoundException;

import java.util.Collection;
import java.util.List;

@Service

public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Student addStudent(Student student) {
        //put-поместить
        return studentRepository.save(student);
    }


    @Override
    public Student findStudent(long id) {

        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public void editStudent(Student student) {
        studentRepository.save(student);

    }

    @Override
    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.deleteById(student.getId());
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public List<Student> findStudentByFacultyId(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(Faculty.class));

        List<Student> students = faculty.getStudents();
        if (students.isEmpty()) {
            throw new StudentNotFoundException(Faculty.class);
        }
        return students;
    }
}



