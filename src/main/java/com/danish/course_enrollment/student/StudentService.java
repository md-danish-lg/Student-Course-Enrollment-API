package com.danish.course_enrollment.student;

import com.danish.course_enrollment.course.Course;
import com.danish.course_enrollment.enrollment.Enrollment;
import com.danish.course_enrollment.enrollment.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        studentRepository.save(student);
    }


    public void removeStudentById(Long id) {
        if(!(studentRepository.existsById(id))){
            throw new IllegalStateException();
        }

        studentRepository.deleteById(id);
    }

    public List<Course> getAllCoursesByStudentId(Long studentId) {
        if (!(studentRepository.existsById(studentId))){
            throw new StudentNotFoundException(studentId);
        }
        return enrollmentRepository.findByStudentIdWithCourse(studentId)
                .stream()
                .map(Enrollment::getCourse)
                .toList();
    }
}
