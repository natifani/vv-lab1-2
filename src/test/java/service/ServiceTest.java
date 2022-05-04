package service;
import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;

    @BeforeEach
    void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    // ne letezzen a grade, letezzen a student es hw
    @Test
    void saveGrade() {
        String idStudent = "7";
        String idHomework = "1";
        double valGrade = 7.5;
        int delivered = 8;
        String feedback = "done";

        int actual = service.saveGrade(idStudent, idHomework, valGrade, delivered, feedback);
        assertEquals(1, actual);
    }

    // ne letezzen a student
    @Test
    void saveStudent() {
        String id = "9";
        String name= "Lili";
        int group = 123;

        int actual = service.saveStudent(id, name, group);
        assertSame(1, actual);
    }

    // letezzen 5-os student
    @Test
    public void updateStudent() {
        String id = "5";
        String name = "Marcika";
        int group = 123;

        service.updateStudent(id, name, group);

        Collection<Student> students = (Collection<Student>) service.findAllStudents();
        Student updatedStudent = students.stream().filter(st -> st.getID() == "5").findFirst().get();

        assertAll("Should return the newly updated student data",
                () -> assertEquals("Marcika", updatedStudent.getName()),
                () -> assertEquals(123, updatedStudent.getGroup()));
    }

    // ne letezzen 3-as homework
    @Test
    public void deleteHomework() {
        String id = "3";
        assertFalse(1 == service.deleteHomework(id));
    }

    // ne letezzen 3-as student, letezzen 9-es
    @ParameterizedTest
    @MethodSource("provideStringsForDelete")
    public void deleteStudent(String id, Integer expected) {
        assertEquals(expected, service.deleteStudent(id));
    }

    private static Stream<Arguments> provideStringsForDelete() {
        return Stream.of(
                Arguments.of("9", 1),
                Arguments.of("3", 0)
        );
    }

}