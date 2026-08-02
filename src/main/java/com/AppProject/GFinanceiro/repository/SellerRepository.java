package com.AppProject.GFinanceiro.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.entity.Seller;

public interface SellerRepository extends MongoRepository<Seller, String> {

  List<Seller> findByDepartment(Department department);
}
