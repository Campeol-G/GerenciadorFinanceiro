package com.AppProject.GFinanceiro.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.exception.DbException;
import com.AppProject.GFinanceiro.service.DepartmentService;
import com.AppProject.GFinanceiro.util.Alerts;
import com.AppProject.GFinanceiro.util.Constraints;
import com.AppProject.GFinanceiro.util.Utils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

@Controller
public class DepartmentFormController implements Initializable {

  private Department entity;

  public void setEntity(Department entity) {
    this.entity = entity;
  }

  @Autowired
  private DepartmentService service;

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
  public void onBtSaveAction(ActionEvent event) {
    if (entity == null) {
      throw new IllegalStateException("Entity was null");
    }
    try {
      entity = getFormData();
      service.saveOrUpdate(entity);
    } catch (DbException e) {
      Alerts.showAlert("Erro saving object", null, e.getMessage(), AlertType.ERROR);
    }
    Utils.currentStage(event).close();
  }

  private Department getFormData() {
    Department obj = new Department();
    obj.setId(txtId.getText());
    obj.setName(txtName.getText());
    return obj;
  }

  @FXML
  public void onBtCancelAction(ActionEvent event) {
    Utils.currentStage(event).close();
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
    if (entity == null) {
      throw new IllegalStateException("Entity was null");
    }

    txtId.setText(entity.getId());
    txtName.setText(entity.getName());
  }
}
