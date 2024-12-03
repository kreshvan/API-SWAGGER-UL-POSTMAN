package groupId.ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import groupId.ru.hogwarts.school.service.StudentService;
import groupId.ru.hogwarts.school.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)

public class StudentControllerTestByWebMvcTest {
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    private StudentServiceImpl studentServiceImpl;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;
    private final Faker faker = new Faker();
@MockBean
private StudentRepository studentRepository;
    @Autowired
    private ObjectMapper objectMapper;// ФУНКЦИЯ преобразование объекта Java в строку JSON И  наоборот
@Test
    void correctAddStudent() throws Exception{
    Student student = new Student(faker.harryPotter().character(),nextInt());
    String studentString = objectMapper.writeValueAsString(student);

    mockMvc.perform(post("/student/add")
            .content(studentString) //проверяет наличия тела - в данном случае строку student
            .contentType(MediaType.APPLICATION_JSON)) //указывает тип содержимого объекта - формат json
            .andExpect(status().isOk());//проверяем статус - что не null
}




@Test
void correctFindStudent() throws Exception{
   long id = nextInt(1,100);
    Student student = new Student(faker.harryPotter().character(),nextInt());
   String studentString = objectMapper.writeValueAsString(student);
    mockMvc.perform(post("/student/get"+student.getId())

                    .content(studentString) //проверяет наличия тела - в данном случае строку student
                    .contentType(MediaType.APPLICATION_JSON)) //указывает тип содержимого объекта - формат json
            .andExpect(status().isOk());//проверяем статус - что не null


}
@Test
void CorrectEditStudent()  throws Exception {

//TEST//CHECK
    long id = nextInt(10, 100);
    Student student = new Student(faker.harryPotter().character(), nextInt());

    when(studentRepository.findById(id)).thenReturn(Optional.of(student));
    doNothing().when(studentRepository).getReferenceById(id);  //getReferenceById - получить ссылку по id


    mockMvc.perform(get("/student/edit/" + student.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.name").value(student.getName()))
            .andExpect(jsonPath("$.color").value(student.getAge())
            );


}









}
