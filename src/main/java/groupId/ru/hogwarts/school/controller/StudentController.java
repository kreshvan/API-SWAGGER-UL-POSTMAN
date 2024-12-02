package groupId.ru.hogwarts.school.controller;

import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import groupId.ru.hogwarts.school.service.StudentService;
import groupId.ru.hogwarts.school.service.StudentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/student")

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService, StudentServiceImpl studentServiceImpl) {

        this.studentService = studentService;
        this.studentServiceImpl = studentServiceImpl;
    }
    private final StudentServiceImpl studentServiceImpl;


    @GetMapping("/sum-students-by-id")
    public List<GetSumAndId> getSumStudentsById(){
        return studentServiceImpl.getSumStudentsById();
    }






    @GetMapping("/{id}/get")
    public ResponseEntity<Student> findStudents(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student); //ок - ок вызвать студента
    }


    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);

    }

    @PutMapping("/{id}/edit")
    public void editStudent(@RequestBody Student student) {
        studentService.editStudent(student);

    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/by/age-between")
    public List<Student> findByAgeBetween(int min, int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/get/faculty/{id}")
    List<Student> findStudentByFacultyId(long id) {

        return studentService.findStudentByFacultyId(id);
    }


}
