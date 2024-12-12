package groupId.ru.hogwarts.school.service;

import groupId.ru.hogwarts.school.controller.*;
import groupId.ru.hogwarts.school.controller.GetSumParameterStudents;
import groupId.ru.hogwarts.school.exepcion.StudentNotFoundException;
import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service

public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @Value("${image.path}")
    private String uploadDir;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Autowired
    public List<Student> findAllPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber  -1, pageSize);
        return studentRepository.findAll(pageable).getContent();    }











    @Override
    public void saveAvatar(long id, MultipartFile file) {
        try {
        if (Files.notExists(Path.of(uploadDir))) {
                Files.createDirectory(Path.of(uploadDir));
        }
        Path path = Path.of(uploadDir, UUID.randomUUID() + file.getOriginalFilename());
        file.transferTo(path);
    } catch(IOException e)
    {
        throw new RuntimeException(e);
    }
}


    @Override
    public boolean deleteAvatar(long id) {
        Path path = Paths.get(uploadDir + "/" + id + "_avatar.jpg");
        File avatarFile = path.toFile();
        if (avatarFile.exists()) {
            return avatarFile.delete();
        }
        return false;

    }





    public List<GetSumParameterStudents>getSumStudents(){

        return studentRepository.getSumStudents();
}

public List<GetAvgStudents>getResultAvgAgeStudents(){
        return studentRepository.getResultAvgAgeStudents();
}

public List<getLastFiveStudentsById>getLastFiveStudentsByIdResults(){
        return studentRepository.getLastFiveStudentsByIdResult();
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



