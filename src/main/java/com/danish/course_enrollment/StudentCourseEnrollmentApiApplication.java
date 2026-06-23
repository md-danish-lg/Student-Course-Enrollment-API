package com.danish.course_enrollment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentCourseEnrollmentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCourseEnrollmentApiApplication.class, args);
    }

    @GetMapping
    public String getInfo(){
        return "STUDENT COURSE ENROLLMENT API";
    }
}
