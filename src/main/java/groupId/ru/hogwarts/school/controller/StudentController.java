package groupId.ru.hogwarts.school.controller;

import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import groupId.ru.hogwarts.school.service.StudentService;
import groupId.ru.hogwarts.school.service.StudentServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/avatars-page")
    public ResponseEntity<List<Student>> findAllPage(@RequestParam("page") Integer pageNumber,
                                    @RequestParam("size") Integer pageSize){
        List<Student> students = studentServiceImpl.findAllPage(pageNumber,pageSize);
        return ResponseEntity.ok(students);
    }
@PostMapping(value = "/avatar/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
ResponseEntity<String>uploadAvatar(@PathVariable long id,
                                    @RequestPart("file") MultipartFile file){
    studentServiceImpl.saveAvatar(id, file);
    if (id>=0|| file!=null){
        return new ResponseEntity<>("Аватарка успешно загружена", HttpStatus.OK);
    }
        return new ResponseEntity<>("Ошибка при загрузке аватарки", HttpStatus.INTERNAL_SERVER_ERROR);
    }

@DeleteMapping("/avatar/{id}")
ResponseEntity<String> deleteAvatar(@PathVariable long id){
    boolean deleteAvatar =  studentService.deleteAvatar(id);

    if (deleteAvatar) {
        return new ResponseEntity<>("Аватарка успешно удалена", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Ошибка при удалении аватарки", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @GetMapping("/sum-students")
    public List<GetSumParameterStudents> getSumStudents(){

        return studentServiceImpl.getSumStudents();
    }



    @GetMapping("/avg-age")
    public List<GetAvgStudents> getResultAvgAgeStudents(){
        return studentServiceImpl.getResultAvgAgeStudents();


    }

    @GetMapping("/desk-five-limit")
    public List<getLastFiveStudentsById>getLastFiveStudentsByIdResults(){
        return studentServiceImpl.getLastFiveStudentsByIdResults();
    }





    @GetMapping("/get/{id}")
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

    @PutMapping ("/edit/{id}")
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
