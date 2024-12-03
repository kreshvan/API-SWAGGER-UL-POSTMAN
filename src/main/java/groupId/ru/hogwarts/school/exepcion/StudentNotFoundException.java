package groupId.ru.hogwarts.school.exepcion;

import groupId.ru.hogwarts.school.model.Student;

public class  StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(long id) {

        super("Student not found by id [%s]".formatted(id));
    }

    public StudentNotFoundException(Class<?> clazz) {
        super("Student not found by id [%s]".formatted(clazz.getSimpleName()));
    }


}
