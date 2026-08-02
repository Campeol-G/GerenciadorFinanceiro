package com.AppProject.GFinanceiro.service;

import java.util.List;

import org.bson.types.ObjectId;
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
    ObjectId departmentObjectId = null;
    try {
      departmentObjectId = new ObjectId(dep.getId());
    } catch (IllegalArgumentException e) {
      // id is not a valid ObjectId (e.g. app-generated String id)
    }
    List<Seller> sellers = sellerRepository.findByDepartmentIdAsStringOrObjectId(dep.getId(), departmentObjectId);
    if (!sellers.isEmpty()) {
      throw new DbException("Department has sellers and can't be deleted");
    }
    repository.deleteById(dep.getId());
  }
}
