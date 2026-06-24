# 🎓 Student Course Enrollment API

A RESTful backend API for managing students, courses, and enrollments. Built with Java and Spring Boot, featuring a multi-entity relational data model, custom business rules, and proper error handling throughout.

## Tech Stack

- **Java 21** + **Spring Boot 3**
- **Spring Data JPA** + **Hibernate** — ORM and data access
- **PostgreSQL** — relational database
- **Docker** — containerised database setup

## Architecture

Clean three-layer, feature-based package structure:

```
com.danish.course_enrollment
├── student/       → Student entity, controller, service, repository
├── course/        → Course entity, controller, service, repository
├── enrollment/    → Enrollment entity, controller, service, repository, request DTO
└── GlobalExceptionHandler.java
```

Request flow:
```
HTTP Request → Controller → Service → Repository → PostgreSQL
```

## Entities & Relationships

```
Student ──< Enrollment >── Course
```

- A **Student** can enroll in many Courses
- A **Course** can have many Students
- **Enrollment** is a join entity with its own fields (`enrolledAt`, `grade`)

## Endpoints

### Students
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/students` | Get all students |
| `GET` | `/api/v1/students/{id}/courses` | Get all courses a student is enrolled in |
| `POST` | `/api/v1/students` | Add a new student |
| `DELETE` | `/api/v1/students/{id}` | Delete a student |

### Courses
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/courses` | Get all courses |
| `GET` | `/api/v1/courses/{id}/students` | Get all students enrolled in a course |
| `POST` | `/api/v1/courses` | Add a new course |

### Enrollments
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/enrollments` | Enroll a student in a course |
| `PATCH` | `/api/v1/enrollments/{id}/grade` | Assign a grade to an enrollment |

## Business Rules

- A student cannot be enrolled in the same course twice — returns `400`
- Enrollment fails if the course has reached `maxCapacity` — returns `400`
- All invalid IDs return `404` with a descriptive message, never a generic 500
- `enrolledAt` is set automatically on enrollment creation via `@PrePersist`
- `addedDate` on students is set automatically via `@PrePersist`

## Error Handling

Global exception handling via `@RestControllerAdvice` with custom exception classes:

| Exception | HTTP Status |
|-----------|-------------|
| `StudentNotFoundException` | 404 Not Found |
| `CourseNotFoundException` | 404 Not Found |
| `AlreadyEnrolledException` | 400 Bad Request |
| `MaxCapacityException` | 400 Bad Request |
| `EmptyInputException` | 400 Bad Request |

## Getting Started

### Prerequisites
- Java 21
- Maven
- Docker

### Run the database
```bash
docker-compose up -d
```

### Run the application
```bash
./mvnw spring-boot:run
```

API available at `http://localhost:8080`

## Example Requests

**Add a student**
```bash
curl -X POST http://localhost:8080/api/v1/students \
  -H "Content-Type: application/json" \
  -d '{"name": "Danish", "email": "danish@example.com"}'
```

**Add a course**
```bash
curl -X POST http://localhost:8080/api/v1/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Physics", 
    "instructor": "Albert Einstein",
    "maxCapacity": 30
  }'
```

**Enroll a student**
```bash
curl -X POST http://localhost:8080/api/v1/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId": 1, "courseId": 1}'
```

**Assign a grade**
```bash
curl -X PATCH http://localhost:8080/api/v1/enrollments/1/grade \
  -H "Content-Type: application/json" \
  -d '"A+"'
```

**Get all courses for a student**
```bash
curl http://localhost:8080/api/v1/students/1/courses
```

## What I Learned

- Designing a multi-entity relational data model with a join entity (`Enrollment`)
- Spring Data JPA relationships — `@OneToMany`, `@ManyToOne`, `mappedBy`, `FetchType.LAZY`
- Solving the N+1 problem with `JOIN FETCH` JPQL queries
- Custom exception classes per error type with `@RestControllerAdvice`
- Business rule enforcement at the service layer (capacity limits, duplicate prevention)
- Feature-based package structure vs layer-based