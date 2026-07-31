package com.AppProject.GFinanceiro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.repository.DepartmentRepository;

@Service
public class DepartmentService {

  private final DepartmentRepository repository;

  public DepartmentService(DepartmentRepository repository) {
    this.repository = repository;
  }

  public List<Department> findAll() {
    return repository.findAll();
  }
}
