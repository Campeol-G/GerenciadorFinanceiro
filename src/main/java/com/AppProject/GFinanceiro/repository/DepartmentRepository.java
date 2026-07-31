package com.AppProject.GFinanceiro.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.AppProject.GFinanceiro.entity.Department;

public interface DepartmentRepository extends MongoRepository<Department, String> {
}
