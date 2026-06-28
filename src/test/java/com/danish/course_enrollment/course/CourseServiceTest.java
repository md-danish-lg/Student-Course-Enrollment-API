package com.danish.course_enrollment.course;

import com.danish.course_enrollment.enrollment.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;

    private CourseService underTest;


    @BeforeEach
    void setUp(){
        underTest = new CourseService(courseRepository, enrollmentRepository);
    }

    @Test
    void getAllCourses(){
        underTest.getCourses();
        verify(courseRepository).findAll();
    }


    @Test
    void addsCourse(){
        Course course = new Course();
        underTest.addNewCourse(course);
        verify(courseRepository).save(course);
    }

    @Test
    void throwsWhenGettingById(){
        Long id = 1L;

        assertThatThrownBy(()-> underTest.findStudentByCourseId(id))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("Course with ID: " + id + " not found");
    }

}