package com.danish.course_enrollment.enrollment;


import com.danish.course_enrollment.course.Course;
import com.danish.course_enrollment.course.CourseRepository;
import com.danish.course_enrollment.student.Student;
import com.danish.course_enrollment.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public void enrollNewStudent(EnrollmentRequest enrollmentRequest) {
        if ((enrollmentRequest.getStudentId() == null && enrollmentRequest.getCourseId() == null)){
            throw new IllegalStateException();
        }

        Long courseId = enrollmentRequest.getCourseId();
        Long studentId = enrollmentRequest.getStudentId();


        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)){
            throw new IllegalStateException("Student already enrolled");
        }
        if (!(courseRepository.existsById(courseId) && studentRepository.existsById(studentId))){
            throw new IllegalStateException();
        };


        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        int currentEnrollments = enrollmentRepository.findByCourseId(courseId).size();
        if(currentEnrollments >= course.getMaxCapacity()){
            throw new IllegalStateException();
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

    }

    public void assignGrade(Long id, String grade) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow();

        enrollment.setGrade(grade);
        enrollmentRepository.save(enrollment);
    }
}
