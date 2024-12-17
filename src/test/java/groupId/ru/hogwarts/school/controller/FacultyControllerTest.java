package groupId.ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.service.FacultyServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean // можем взять часть из FacultyServiceImpl и замокировать-(сделать ненастоящее) если нужно
// Spy - наполовину
    private FacultyServiceImpl facultyService;

    @MockBean //MockBean позволяет заменять реальные компоненты Spring контекста на их моки
    // (искусственные объекты, имитирующие поведение настоящих объектов).
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;// ФУНКЦИЯ  преобразование объекта Java в строку JSON И  наоборот


    @Test
    void correctAddFaculty() throws Exception {
        Faculty faculty = new Faculty("Oleg", "color");
        String facultyString = objectMapper.writeValueAsString(faculty);
when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
//test and check
        mockMvc.perform(post("/faculty/add/")
                        .content(facultyString)
                        .contentType(MediaType.APPLICATION_JSON))//Когда вы видите Content-Type:
                // application/json в заголовках HTTP-запроса или ответа, это означает,
                // что тело сообщения содержит данные в формате JSON.
                .andExpect(status().isOk());//andExpect — это метод, используемый в рамках тестирования с библиотеками,
        // такими как JUnit или Spring Test в языке программирования Java.
        //Обычно применяется для проверки результатов выполнения HTTP-запросов.

    }

    @Test
    void CorrectEditFaculty() throws  Exception {

//TEST//CHECK
        long id = nextInt(10, 100);
        Faculty faculty = new Faculty("Oleg", "color");

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).getReferenceById(id);  //getReferenceById - получить ссылку по id


        mockMvc.perform(get("/faculty/" + id + "/edit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor())
                );
    }


    @Test
    void getFaculty() throws Exception {

        Faculty faculty = new Faculty("Oleg", "color");
        String facultyString = objectMapper.writeValueAsString(faculty);

//test and check
        mockMvc.perform(post("/faculty/get/")
                        .content(facultyString)
                        .contentType(MediaType.APPLICATION_JSON))//Когда вы видите Content-Type:
                // application/json в заголовках HTTP-запроса или ответа, это означает,
                // что тело сообщения содержит данные в формате JSON.
                .andExpect(status().isOk());//andExpect — это метод, используемый в рамках тестирования с библиотеками,
        // такими как JUnit или Spring Test в языке программирования Java.
        //Обычно применяется для проверки результатов выполнения HTTP-запросов.


    }


    @Test
    void correctFindFacultyById() throws Exception {
        long id = nextInt(10, 100);
        Faculty faculty = new Faculty("Oleg", "color");
        faculty.setId(id);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/" + faculty.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor())
                );


    }
}