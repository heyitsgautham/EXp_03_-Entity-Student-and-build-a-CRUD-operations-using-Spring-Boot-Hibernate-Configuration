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
