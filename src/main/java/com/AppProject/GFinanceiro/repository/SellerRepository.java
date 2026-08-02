package com.AppProject.GFinanceiro.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.AppProject.GFinanceiro.entity.Seller;

public interface SellerRepository extends MongoRepository<Seller, String> {

  List<Seller> findByDepartmentId(String departmentId);

  @Query("{ 'DepartmentId': { $in: [?0, ?1] } }")
  List<Seller> findByDepartmentIdAsStringOrObjectId(String departmentId, ObjectId departmentObjectId);
}
