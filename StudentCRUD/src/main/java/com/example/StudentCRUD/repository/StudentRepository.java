package com.example.StudentCRUD.repository;



import com.example.StudentCRUD.Model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
