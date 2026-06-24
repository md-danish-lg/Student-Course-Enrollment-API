package com.danish.course_enrollment.enrollment;

public class MaxCapacityException extends IllegalStateException {
    public MaxCapacityException(Long id, int MaxCapacity) {

        super("Course with ID: " + id + " is at Max Capacity: " + MaxCapacity);
    }
}
