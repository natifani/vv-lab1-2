package service;
import domain.Grade;
import domain.Homework;
import domain.Pair;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceTestMock {
    private Service service;
    private StudentXMLRepository fileRepository1;
    private HomeworkXMLRepository fileRepository2;
    private GradeXMLRepository fileRepository3;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.initMocks(service);
        fileRepository1 = mock(StudentXMLRepository.class);
        fileRepository2 = mock(HomeworkXMLRepository.class);
        fileRepository3 = mock(GradeXMLRepository.class);
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void saveGrade() {
        assertNotNull(fileRepository1);
        assertNotNull(fileRepository2);
        assertNotNull(fileRepository3);

        String idStudent = "7";
        String studentName = "Ana";
        int studentGroup = 224;
        String idHomework = "1";
        String description = "File";
        int deadline = 7;
        int startline = 6;
        double valGrade = 5;
        int delivered = 8;
        String feedback = "done";

        Grade gradeToSave = new Grade(new Pair<>(idStudent, idHomework), valGrade, delivered, feedback);
        Student studentReturn = new Student(idStudent, studentName, studentGroup);
        Homework homeworkReturn = new Homework(idHomework, description, deadline, startline);

        when(fileRepository1.findOne(anyString())).thenReturn(studentReturn);
        when(fileRepository2.findOne(anyString())).thenReturn(homeworkReturn);
        when(fileRepository3.save(gradeToSave)).thenReturn(null);

        int actual = service.saveGrade(idStudent, idHomework, valGrade, delivered, feedback);
        verify(fileRepository1).findOne(anyString());
        verify(fileRepository2, times(2)).findOne(anyString());

        assertEquals(1, actual);
    }

    @Test
    void saveStudent() {
        assertNotNull(fileRepository1);
        String id = "9";
        String name= "Lili";
        int group = 123;

        Student studentToSave = new Student(id, name, group);
        when(fileRepository1.save(studentToSave)).thenReturn(null);

        int actual = service.saveStudent(id, name, group);
        verify(fileRepository1).save(studentToSave);

        assertSame(1, actual);
    }

    @Test
    public void deleteHomework() {
        assertNotNull(fileRepository2);

        String id = "3";
        when(fileRepository2.delete(id)).thenReturn(null);
        int actual = service.deleteHomework(id);
        verify(fileRepository2).delete(id);

        assertFalse(1 == actual);
    }
}
