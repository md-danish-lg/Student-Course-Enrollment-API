package com.danish.course_enrollment.enrollment;

public class AlreadyEnrolledException extends IllegalStateException{
    public AlreadyEnrolledException(Long StudentId, Long CourseId){
        super("Student with id: " + StudentId + " already Enrolled in Course ID: " + CourseId);
    }

}
