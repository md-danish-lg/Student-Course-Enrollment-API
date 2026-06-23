package com.danish.course_enrollment.course;


import com.danish.course_enrollment.student.Student;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getCourses();
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsInCourse(@PathVariable Long id){
        return courseService.findStudentByCourseId(id);
    }

    @PostMapping()
    public void addCourse(@RequestBody Course course){
        courseService.addNewCourse(course);
    }



}
