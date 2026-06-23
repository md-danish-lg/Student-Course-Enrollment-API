package com.danish.course_enrollment.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRespository extends JpaRepository<Enrollment, Long> {
}
