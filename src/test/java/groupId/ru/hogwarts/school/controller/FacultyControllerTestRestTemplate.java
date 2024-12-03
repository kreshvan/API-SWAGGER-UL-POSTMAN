package groupId.ru.hogwarts.school.controller;

import com.github.javafaker.Faker;
import groupId.ru.hogwarts.school.model.Faculty;
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

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {
    @Autowired
    private FacultyRepository facultyRepository;
    @LocalServerPort
    private int port; // самостоятельно делает запросы в приложении
    private final Faker faker = new Faker();
    @Autowired
    private TestRestTemplate testRestTemplate;

    //TestRestTemplate – это удобная альтернатива RestTemplate из Spring,
    // которая полезна в рамках интеграционных тестов. ИМЕЕТ МЕТОДЫ ДЛЯ ТЕСТИРОВАНИЯ.
    // Можно получить ванильный шаблон или тот, который отправляет базовую HTTP-аутентификацию
    // (с именем пользователя и паролем).
    @Test
    @DisplayName("Корректно добавляет факультет!")
    void correctAddFaculty() {
        Faculty faculty = new Faculty("name","color");
//TEST  //ResponseEntity - ОБЪЕКТ ОТВЕТА

        //check
        ResponseEntity<Faculty> response = testRestTemplate.postForEntity(
                ("http://localhost" + port + "/faculty/add"),
                faculty,
                Faculty.class);
        //test
        //System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Faculty body = response.getBody();
        assertThat(body.getColor()).isEqualTo(faculty.getColor());//ПРОАЕРКА ЧТО У body - тела есть ЦВЕТ
        assertThat(body.getName()).isEqualTo(faculty.getName());
        assertThat(body.getId()).isNotNull();
    }
    @Test
    @DisplayName("Корректно находит факультет ! ")
    void correctFindFaculty() {
        //test
        Faculty faculty = new Faculty("name","color");
    facultyRepository.save(faculty);

        //ResponseEntity - ОБЪЕКТ ОТВЕТА
        ResponseEntity<Faculty> response = testRestTemplate.getForEntity(
                ("http://localhost" + port + "/get/student/{id}"),
                Faculty.class,
                faculty.getId());
        //check
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Faculty body = response.getBody();//что тело при запросе будет одно и тоже тело
        assertThat(body.getName()).isEqualTo(faculty.getName());//ПРОвЕРКА ЧТО У body - тела есть возраст
        assertThat(body.getColor()).isEqualTo(faculty.getColor());
        assertThat(body.getId()).isNotNull();

    }
    @Test
    @DisplayName("Корректно удаляет факультет !")
    void correctDeleteStudent() {
        Faculty faculty = new Faculty("name","color");
        facultyRepository.save(faculty);

        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                ("http://localhost" + port + "/faculty/{id}/delete" ),
                HttpMethod.DELETE,//показываем какой метод
                null,
                Faculty.class,
                faculty.getId());
//check
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Faculty body = response.getBody();
        assertThat(body.getName()).isEqualTo(faculty.getName());//ПРОвЕРКА ЧТО У body - тела есть возраст
        assertThat(body.getColor()).isEqualTo(faculty.getColor());
        assertThat(body.getId()).isNotNull();
        Faculty actualFaculty = facultyRepository.findById(body.getId()).orElseThrow(null);
        assertThat(actualFaculty).isNotNull();

    }






}
