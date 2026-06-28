package com.danish.course_enrollment.enrollment;

import com.danish.course_enrollment.course.Course;
import com.danish.course_enrollment.course.CourseNotFoundException;
import com.danish.course_enrollment.course.CourseRepository;
import com.danish.course_enrollment.student.Student;
import com.danish.course_enrollment.student.StudentNotFoundException;
import com.danish.course_enrollment.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    private EnrollmentService underTest;

    @BeforeEach
    void setUp(){
        underTest = new EnrollmentService(enrollmentRepository, courseRepository, studentRepository);
    }

    @Test
    void throwsWhenStudentNotFound(){
        Long id=1L;
        EnrollmentRequest request = new EnrollmentRequest(id, id);

        when(enrollmentRepository.existsByStudentIdAndCourseId(id, id)).thenReturn(false);
        when(courseRepository.existsById(id))
                .thenReturn(true);
        when(studentRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(()->underTest.enrollNewStudent(request))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with ID: " + id + " not found!");
    }


    @Test
    void throwsWhenCourseNotFound(){
        Long id=2L;
        EnrollmentRequest request = new EnrollmentRequest(id, id);

        when(enrollmentRepository.existsByStudentIdAndCourseId(id, id)).thenReturn(false);
        when(courseRepository.existsById(id))
                .thenReturn(false);

        assertThatThrownBy(()->underTest.enrollNewStudent(request))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("Course with ID: " + id + " not found");


    }

    @Test
    void throwsWhenAlreadyEnrolled(){
        Long id= 1L;
        EnrollmentRequest request = new EnrollmentRequest(id, id);


        when(enrollmentRepository.existsByStudentIdAndCourseId(id, id)).thenReturn(true);
        assertThatThrownBy(()->underTest.enrollNewStudent(request))
                .isInstanceOf(AlreadyEnrolledException.class)
                .hasMessageContaining("Student with id: " + id + " already Enrolled in Course ID: " + id);
    }

    @Test
    void throwsWhenMaxCapacity(){
        Long id= 1L;
        EnrollmentRequest request = new EnrollmentRequest(id, id);

        Course course = new Course();
        course.setId(id);
        course.setMaxCapacity(2);

        Student student = new Student();
        student.setId(id);
        student.setName("DEMO");

        List<Enrollment> enrollments = List.of(
                new Enrollment(),
                new Enrollment()
        );


        when(enrollmentRepository.existsByStudentIdAndCourseId(id, id)).thenReturn(false);
        when(courseRepository.existsById(id)).thenReturn(true);
        when(studentRepository.existsById(id)).thenReturn(true);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourseId(id)).thenReturn(enrollments);



        assertThatThrownBy(()->underTest.enrollNewStudent(request))
                .isInstanceOf(MaxCapacityException.class)
                .hasMessageContaining("Course with ID: " + id + " is at Max Capacity: " + 2);

    }

    @Test
    void successfullyEnrollsStudent(){
        Long id= 1L;
        EnrollmentRequest request = new EnrollmentRequest(id, id);

        Course course = new Course();
        course.setId(id);
        course.setMaxCapacity(3);

        Student student = new Student();
        student.setId(id);
        student.setName("DEMO");

        List<Enrollment> enrollments = List.of(
                new Enrollment(),
                new Enrollment()
        );


        when(enrollmentRepository.existsByStudentIdAndCourseId(id, id)).thenReturn(false);
        when(courseRepository.existsById(id)).thenReturn(true);
        when(studentRepository.existsById(id)).thenReturn(true);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByCourseId(id)).thenReturn(enrollments);


        underTest.enrollNewStudent(request);
        ArgumentCaptor<Enrollment> captor = ArgumentCaptor.forClass(Enrollment.class);

        verify(enrollmentRepository).save(captor.capture());
        Enrollment savedEnrollment = captor.getValue();

        assertThat(savedEnrollment.getStudent()).isEqualTo(student);
        assertThat(savedEnrollment.getCourse()).isEqualTo(course);

    }


    @Test
    void throwsWhenEnrollmentNotFound(){
        Long id = 1L;
        assertThatThrownBy(()->underTest.assignGrade(id, "A"))
                .isInstanceOf(EnrollmentNotFoundException.class)
                .hasMessageContaining("Enrollment with id: " + id +" Not Found");
    }

    @Test
    void assignsGradeCorrectly(){
        Long id = 1L;

        Enrollment e = new Enrollment();

        e.setId(id);
        e.setGrade(null);

        when(enrollmentRepository.findById(id)).thenReturn(Optional.of(e));

        underTest.assignGrade(id, "A");

        assertThat(e.getGrade()).isEqualTo("A");
        verify(enrollmentRepository).save(e);


    }
}

