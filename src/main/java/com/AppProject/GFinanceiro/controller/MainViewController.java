package com.AppProject.GFinanceiro.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

@Controller
public class MainViewController implements Initializable {

  @FXML
  private MenuItem menuItemSeller;
  @FXML
  private MenuItem menuItemDepartment;
  @FXML
  private MenuItem menuItemAbout;

  @FXML
  public void onMenuItemSellerAction() {
    System.out.println("onMenuItemSellerAction");
  }

  @FXML
  public void onMenuItemDepartmentAction() {
    System.out.println("onMenuItemDepartmentAction");
  }

  @FXML
  public void onMenuItemAboutAction() {
    System.out.println("onMenuItemAboutAction");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }

}
