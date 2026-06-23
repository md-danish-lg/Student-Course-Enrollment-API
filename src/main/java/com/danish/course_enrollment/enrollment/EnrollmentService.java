package com.danish.course_enrollment.enrollment;


import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    private final EnrollmentRespository enrollmentRespository;

    public EnrollmentService(EnrollmentRespository enrollmentRespository) {
        this.enrollmentRespository = enrollmentRespository;
    }

    public void saveStudent(Enrollment enrollment){
        enrollmentRespository.save(enrollment);
    }
}
