package groupId.ru.hogwarts.school.controller;

import groupId.ru.hogwarts.school.model.Faculty;
import groupId.ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
    //int minNumber = 0;
    //int maxNumber = MAX_VALUE;
    List<Student> findByAgeBetween(int min, int max);


    @Query(value = "SELECT COUNT (*) FROM Students", nativeQuery = true)
    List<GetSumParameterStudents>getSumStudents();

     @Query(value = "SELECT AVG(Age) FROM Students", nativeQuery = true)  //таблица студенты (Students)
    List<GetAvgStudents> getResultAvgAgeStudents();

     @Query(value = "SELECT * FROM Students BY id DESC LIMIT 5", nativeQuery = true)
    List<getLastFiveStudentsById>getLastFiveStudentsByIdResult();

List<Student>findAllPage(Pageable pageable);

    Student getById(int i,int q);
}
