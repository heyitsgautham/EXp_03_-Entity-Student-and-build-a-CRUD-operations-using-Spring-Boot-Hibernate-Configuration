# EXp_03_-Entity-Student-and-build-a-CRUD-operations-using-Spring-Boot-Hibernate-Configuration

## AIM:
To develop a Spring Boot application that performs CRUD (Create, Read, Update, Delete) operations on a Student entity using Spring Data JPA (Hibernate).

## ALGORITHM:
Create Spring Boot Project

Add dependencies: Spring Web, Spring Data JPA, H2 Database or MySQL, Spring Boot DevTools

Configure application.properties

Define database connection

Enable Hibernate auto DDL

Create Student Entity Class

Annotate with @Entity

Define fields with @Id, @GeneratedValue, etc.

Create StudentRepository

Extend JpaRepository<Student, Long> for CRUD methods

Create StudentController

Handle HTTP methods:

POST /students → Add student

GET /students → Get all students

GET /students/{id} → Get student by ID

PUT /students/{id} → Update student

DELETE /students/{id} → Delete student

##PROGRAM CODE

### pom.xml
```
<dependencies>
		<!-- Web Starter -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- JPA (Hibernate) -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<!-- MySQL Connector -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- DevTools (optional) -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
	</dependencies>
```
 ### application.properties
 ```

spring.application.name=StudentCRUD
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=Root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

```
### StudentEntity.java
```
package com.example.StudentCRUD.Model;

import jakarta.persistence.*;

@Entity
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
 public void setAge(int age) { this.age = age; }
}
```
### StudentRepository.java
```
package com.example.StudentCRUD.repository;



import com.example.StudentCRUD.Model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
```

### StudentController.java
```
package com.example.StudentCRUD.controller;

import com.example.StudentCRUD.Model.StudentEntity;
import com.example.StudentCRUD.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Create
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentEntity student) {
        try {
            StudentEntity saved = studentRepository.save(student);
            return ResponseEntity.ok("Student created with ID: " + saved.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create student.");
        }
    }

    // Read All
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<StudentEntity> list = studentRepository.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.status(404).body("No students found.");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        Optional<StudentEntity> student = studentRepository.findById(id);
        return student.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Student not found with ID: " + id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentEntity details) {
        Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(404).body("Student not found with ID: " + id);
        }

        StudentEntity student = optionalStudent.get();
        student.setName(details.getName());
        student.setAge(details.getAge());
        student.setDepartment(details.getDepartment());

        studentRepository.save(student);
        return ResponseEntity.ok("Student updated successfully.");
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Student not found with ID: " + id);
        }

        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student with ID " + id + " deleted successfully.");
    }
}
```
### StudentCrudApplication.java
```
package com.example.StudentCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentCrudApplication.class, args);
	}

}
```
# RESULT:
Hence the Crud operations have been implemented successully
