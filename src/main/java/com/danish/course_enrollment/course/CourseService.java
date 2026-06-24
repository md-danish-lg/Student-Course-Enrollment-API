package com.danish.course_enrollment.course;

import com.danish.course_enrollment.enrollment.Enrollment;
import com.danish.course_enrollment.enrollment.EnrollmentRepository;
import com.danish.course_enrollment.student.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public void addNewCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Student> findStudentByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseIdWithStudent(courseId)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }


}
