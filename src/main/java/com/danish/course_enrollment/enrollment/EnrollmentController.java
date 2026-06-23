package com.danish.course_enrollment.enrollment;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public void enrollStudent(@RequestBody EnrollmentRequest enrollmentRequest){
        enrollmentService.enrollNewStudent(enrollmentRequest);
    }

    @PatchMapping("{id}/grade")
    public ResponseEntity<String> assignStudentGrade(@PathVariable Long id, @RequestBody String grade){
        enrollmentService.assignGrade(id, grade);
        return ResponseEntity.ok("grade changed successfully");
    }
}
