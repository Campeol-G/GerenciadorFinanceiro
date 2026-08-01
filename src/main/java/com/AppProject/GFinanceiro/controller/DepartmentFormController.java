package com.AppProject.GFinanceiro.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.util.Constraints;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Controller
public class DepartmentFormController implements Initializable {

  private Department entity;

  public void setEntity(Department entity) {
    this.entity = entity;
  }

  @FXML
  private TextField txtId;

  @FXML
  private TextField txtName;

  @FXML
  private Label labelErrorName;

  @FXML
  private Button btSave;

  @FXML
  private Button btCancel;

  @FXML
  public void onBtSaveAction() {
    System.out.println("save");
  }

  @FXML
  public void onBtCancelAction() {
    System.out.println("cancel");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    initializeNodes();
  }

  private void initializeNodes() {
    Constraints.setTextFieldInteger(txtId);
    Constraints.setTextFieldMaxLength(txtName, 30);
  }

  public void updateFormData() {
    txtId.setText(entity.getId());
    txtName.setText(entity.getName());
  }
}
