package com.danish.course_enrollment.enrollment;


import com.danish.course_enrollment.course.Course;
import com.danish.course_enrollment.student.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String enrolledAt;

    @Column(nullable = true)
    private String grade;


}
