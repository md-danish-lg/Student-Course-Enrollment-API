package com.danish.course_enrollment.enrollment;


import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void saveStudent(Enrollment enrollment){
        enrollmentRepository.save(enrollment);
    }
}
