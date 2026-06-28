package com.danish.course_enrollment.enrollment;


import com.danish.course_enrollment.course.Course;
import com.danish.course_enrollment.course.CourseNotFoundException;
import com.danish.course_enrollment.course.CourseRepository;
import com.danish.course_enrollment.student.Student;
import com.danish.course_enrollment.student.StudentNotFoundException;
import com.danish.course_enrollment.student.StudentRepository;
import org.springframework.stereotype.Service;



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
        if ((enrollmentRequest.getStudentId() == null || enrollmentRequest.getCourseId() == null)){
            throw new EmptyInputException("Student ID or Course ID Not specified");
        }

        Long courseId = enrollmentRequest.getCourseId();
        Long studentId = enrollmentRequest.getStudentId();


        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)){
            throw new AlreadyEnrolledException(studentId, courseId);
        }
        if(!(courseRepository.existsById(courseId))) {
            throw new CourseNotFoundException(courseId);
        }if(!(studentRepository.existsById(studentId))) {
            throw new StudentNotFoundException(studentId);
        }


        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        int currentEnrollments = enrollmentRepository.findByCourseId(courseId).size();
        if(currentEnrollments >= course.getMaxCapacity()){
            throw new MaxCapacityException(courseId, course.getMaxCapacity());
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

    }

    public void assignGrade(Long id, String grade) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(()-> new EnrollmentNotFoundException(id));

        enrollment.setGrade(grade);
        enrollmentRepository.save(enrollment);
    }
}
