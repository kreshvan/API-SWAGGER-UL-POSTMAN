package groupId.ru.hogwarts.school.service;

import groupId.ru.hogwarts.school.controller.StudentRepository;
import groupId.ru.hogwarts.school.exepcion.StudentNotFoundException;
import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import groupId.ru.hogwarts.school.controller.FacultyRepository;

import java.util.List;

@Service

public class FacultyServiceImpl implements FacultyService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository =  facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundException(Faculty.class.getName()));
    }

    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        facultyRepository.deleteById(faculty.getId());
    }

    @Override
    public List<Faculty> findFacultyByNameOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findFacultyByNameOrColorIgnoreCase(name, color);
    }


    @Override
    public Faculty findFacultyByStudentId(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(Student.class));
        Faculty faculty = student.getFaculty();
        if (faculty == null) {
            throw new StudentNotFoundException(Faculty.class);
        }
        return faculty;
    }
}



