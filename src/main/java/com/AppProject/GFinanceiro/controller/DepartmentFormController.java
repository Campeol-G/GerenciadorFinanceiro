package com.AppProject.GFinanceiro.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.exception.DbException;
import com.AppProject.GFinanceiro.exception.ValidationException;
import com.AppProject.GFinanceiro.listeners.DataChangeListener;
import com.AppProject.GFinanceiro.service.DepartmentService;
import com.AppProject.GFinanceiro.util.Alerts;
import com.AppProject.GFinanceiro.util.Constraints;
import com.AppProject.GFinanceiro.util.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Controller
public class DepartmentFormController implements Initializable {

  private Department entity;

  public void setEntity(Department entity) {
    this.entity = entity;
  }

  private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
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
      nofityDataChangeListeners();
      Utils.currentStage(event).close();

    } catch (DbException e) {
      Alerts.showAlert("Erro saving object", null, e.getMessage(), AlertType.ERROR);
    } catch (ValidationException e) {
      setErrorsMessages(e.getErrors());
    }
  }

  private void nofityDataChangeListeners() {
    for (DataChangeListener listener : dataChangeListeners) {
      listener.onDataChanged();
    }
  }

  private Department getFormData() {
    Department obj = new Department();

    ValidationException exception = new ValidationException("Validation error");

    obj.setId(txtId.getText());

    if (txtName.getText() == null || txtName.getText().trim().equals("")) {
      exception.addError("name", "Field can't be empty");
    }
    obj.setName(txtName.getText());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
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

  public void subscribeDataChangeListener(DataChangeListener listener) {
    dataChangeListeners.add(listener);
  }

  private void setErrorsMessages(Map<String, String> error) {
    Set<String> fields = error.keySet();

    if (fields.contains("name")) {
      labelErrorName.setText(error.get("name"));
    }
  }
}
