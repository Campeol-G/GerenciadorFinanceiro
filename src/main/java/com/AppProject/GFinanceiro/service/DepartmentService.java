package com.AppProject.GFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.entity.Seller;
import com.AppProject.GFinanceiro.exception.DbException;
import com.AppProject.GFinanceiro.repository.DepartmentRepository;
import com.AppProject.GFinanceiro.repository.SellerRepository;

@Service
public class DepartmentService {

  private final DepartmentRepository repository;
  private final SellerRepository sellerRepository;

  public DepartmentService(DepartmentRepository repository, SellerRepository sellerRepository) {
    this.repository = repository;
    this.sellerRepository = sellerRepository;
  }

  public List<Department> findAll() {
    return repository.findAll();
  }

  public void saveOrUpdate(Department dep) {
    repository.save(dep);
  }

  public void delete(Department dep) {
    List<Seller> sellers = sellerRepository.findByDepartment(dep);
    if (!sellers.isEmpty()) {
      throw new DbException("Department has sellers and can't be deleted");
    }
    repository.delete(dep);
  }
}
