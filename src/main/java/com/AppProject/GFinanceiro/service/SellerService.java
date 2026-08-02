package com.AppProject.GFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.AppProject.GFinanceiro.entity.Seller;
import com.AppProject.GFinanceiro.repository.SellerRepository;

@Service
public class SellerService {

  private SellerRepository repository;

  public SellerService(SellerRepository repository) {
    this.repository = repository;
  }

  public List<Seller> findAll() {
    return repository.findAll();
  }

  public void saveOrUpdate(Seller sel) {
    repository.save(sel);
  }

  public void delete(Seller sel) {
    repository.delete(sel);
  }
}
