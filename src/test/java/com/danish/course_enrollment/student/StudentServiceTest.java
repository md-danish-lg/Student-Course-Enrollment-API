package com.danish.course_enrollment.student;

import com.danish.course_enrollment.enrollment.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;

    private StudentService underTest;


    @BeforeEach
    void setUp(){
        underTest = new StudentService(studentRepository, enrollmentRepository);
    }

    @Test
    void getsAllStudents(){
        underTest.getStudents();
        verify(studentRepository).findAll();
    }


    @Test
    void addsStudent(){
        Student student = new Student();
        underTest.addNewStudent(student);
        verify(studentRepository).save(student);
    }

    @Test
    void throwsWhenDeleting(){
        Long id = 1L;

        assertThatThrownBy(()-> underTest.removeStudentById(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with ID: " + id + " not found!");
    }

}