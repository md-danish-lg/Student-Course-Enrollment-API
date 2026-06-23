package com.danish.course_enrollment.student;


import com.danish.course_enrollment.course.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/{id}/courses")
    public List<Course> getStudentCourse(@PathVariable Long id){
        return studentService.getAllCoursesByStudentId(id);
    }

    @PostMapping
    public void addStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    };

    @DeleteMapping("{id}")
    public ResponseEntity<String> removeStudent(@PathVariable Long id){
        studentService.removeStudentById(id);
        return ResponseEntity.ok("User Deleted Successfully");

    }




}
