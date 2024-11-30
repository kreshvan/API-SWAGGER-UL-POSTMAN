package groupId.ru.hogwarts.school.controller;

import com.github.javafaker.Faker;
import groupId.ru.hogwarts.school.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @Autowired
    private StudentRepository studentRepository;
    @LocalServerPort
    private int port; // амостоятельно делает запросы в приложении
    private final Faker faker = new Faker();
    @Autowired
    private TestRestTemplate testRestTemplate;

    //TestRestTemplate – это удобная альтернатива RestTemplate из Spring,
    // которая полезна в рамках интеграционных тестов. ИМЕЕТ МЕТОДЫ ДЛЯ ТЕСТИРОВАНИЯ.
    // Можно получить ванильный шаблон или тот, который отправляет базовую HTTP-аутентификацию
    // (с именем пользователя и паролем).
    @Test
    @DisplayName("Корректно добавляет студента!")
    void addStudent() {
        Student student = new Student(faker.harryPotter().character(), nextInt());
//TEST  //ResponseEntity - ОБЪЕКТ ОТВЕТА

        //check
        ResponseEntity<Student> response = testRestTemplate.postForEntity(
                configureUrl("http://localhost" + port + "/student/add"),
                student,
                Student.class);
        //test
        //System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Student body = response.getBody();
        assertThat(body.getAge()).isEqualTo(student.getAge());//ПРОАЕРКА ЧТО У body - тела есть возраст
        assertThat(body.getName()).isEqualTo(student.getName());
        assertThat(body.getFaculty()).isEqualTo(student.getFaculty());
        assertThat(body.getId()).isNotNull();
    }

    @Test
    @DisplayName("Корректно находит студента ! ")
    void findStudents() {
        //test
        Student student = new Student(faker.harryPotter().character(), nextInt());
        studentRepository.save(student);

        //ResponseEntity - ОБЪЕКТ ОТВЕТА
        ResponseEntity<Student> response = testRestTemplate.getForEntity(
                configureUrl("/student/{id}/get"),
                Student.class,
                student.getId());
        //check
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Student body = response.getBody();
        assertThat(body.getAge()).isEqualTo(student.getAge());//ПРОвЕРКА ЧТО У body - тела есть возраст
        assertThat(body.getName()).isEqualTo(student.getName());
        assertThat(body.getFaculty()).isEqualTo(student.getFaculty());
        assertThat(body.getId()).isNotNull();

    }


    @Test
    @DisplayName("Корректно удаляет студента !")
    void deleteStudent() {
        Student student = new Student(faker.harryPotter().character(), nextInt());
        studentRepository.save(student);

        ResponseEntity<Student> response = testRestTemplate.exchange(
                configureUrl("http://localhost" + port + "/student/add"),
                HttpMethod.DELETE,
                null,
                Student.class,
                student.getId());
//check
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Student body = response.getBody();
        assertThat(body.getAge()).isEqualTo(student.getAge());//ПРОвЕРКА ЧТО У body - тела есть возраст
        assertThat(body.getName()).isEqualTo(student.getName());
        assertThat(body.getFaculty()).isEqualTo(student.getFaculty());
        assertThat(body.getId()).isNotNull();
        Student actualStudent = studentRepository.findById(body.getId()).orElseThrow(null);
        assertThat(actualStudent).isNotNull();

    }


    @Test
    @DisplayName("Корректно редактирует студента !")
    void editStudent() {
        Student student = new Student("Oleg", nextInt(18, 80));
        studentRepository.save(student);
        Student newStudent = new Student("Ivan", nextInt(18, 90));
        testRestTemplate.put(configureUrl("/{id}/edit"), newStudent, student.getId());
//check
        Student actual = studentRepository.findById(student.getId()).orElseThrow();
        assertThat(actual.getAge()).isEqualTo(newStudent.getAge());//ПРОвЕРКА ЧТО У body - тела есть возраст
        assertThat(actual.getName()).isEqualTo(newStudent.getName());
        assertThat(actual.getFaculty()).isEqualTo(newStudent.getFaculty());
        assertThat(newStudent.getId()).isNotNull();
    }


    private String configureUrl(String path) {

        return "http://localhost:%d%s".formatted(port, path);
    }
}