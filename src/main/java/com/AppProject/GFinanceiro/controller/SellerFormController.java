package com.AppProject.GFinanceiro.controller;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.entity.Seller;
import com.AppProject.GFinanceiro.exception.DbException;
import com.AppProject.GFinanceiro.exception.ValidationException;
import com.AppProject.GFinanceiro.listeners.DataChangeListener;
import com.AppProject.GFinanceiro.service.DepartmentService;
import com.AppProject.GFinanceiro.service.SellerService;
import com.AppProject.GFinanceiro.util.Alerts;
import com.AppProject.GFinanceiro.util.Constraints;
import com.AppProject.GFinanceiro.util.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

@Controller
public class SellerFormController implements Initializable {

  private Seller entity;

  @Autowired
  private SellerService service;
  @Autowired
  private DepartmentService depService;

  public void setEntity(Seller entity) {
    this.entity = entity;
  }

  private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

  @FXML
  private TextField txtId;

  @FXML
  private TextField txtName;

  @FXML
  private TextField txtEmail;

  @FXML
  private TextField txtBaseSalary;

  @FXML
  private DatePicker dpBirthDate;

  @FXML
  private ComboBox<Department> comboBoxDepartment;

  @FXML
  private Label labelErrorName;

  @FXML
  private Label labelErrorEmail;

  @FXML
  private Label labelErrorBirthDate;

  @FXML
  private Label labelErrorSalary;

  @FXML
  private Button btSave;

  @FXML
  private Button btCancel;

  @FXML
  private ObservableList<Department> obsList;

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

  private Seller getFormData() {
    Seller obj = new Seller();

    ValidationException exception = new ValidationException("Validation error");

    obj.setId(txtId.getText());

    if (txtName.getText() == null || txtName.getText().trim().equals("")) {
      exception.addError("name", "Field can't be empty");
    }
    obj.setName(txtName.getText());

    if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
      exception.addError("email", "Field can't be empty");
    }
    obj.setEmail(txtEmail.getText());

    if (dpBirthDate.getValue() == null) {
      exception.addError("birthDate", "Field can't be empty");
    } else {
      Instant inst = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
      obj.setBirthDate(inst);
    }

    if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
      exception.addError("baseSalary", "Field can't be empty");
    }
    try {
      obj.setBaseSalary(Double.parseDouble(txtBaseSalary.getText()));
    } catch (NumberFormatException e) {
      obj.setBaseSalary(null);
    }

    obj.setDepartment(comboBoxDepartment.getValue());
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
    Constraints.setTextFieldMaxLength(txtName, 80);
    Constraints.setTextFieldDouble(txtBaseSalary);
    Constraints.setTextFieldMaxLength(txtEmail, 100);
    Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
  }

  public void updateFormData() {
    if (entity == null) {
      throw new IllegalStateException("Entity was null");
    }

    txtId.setText(entity.getId());
    txtName.setText(entity.getName());
    txtEmail.setText(entity.getEmail());
    txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
    dpBirthDate.setValue(Utils.instantToLocalDate(entity.getBirthDate()));
    if (entity.getDepartment() == null) {
      comboBoxDepartment.getSelectionModel().selectFirst();
    } else {
      comboBoxDepartment.setValue(entity.getDepartment());
    }
  }

  public void loadAssociatedObjects() {
    List<Department> list = depService.findAll();
    obsList = FXCollections.observableArrayList(list);
    comboBoxDepartment.setItems(obsList);
  }

  public void subscribeDataChangeListener(DataChangeListener listener) {
    dataChangeListeners.add(listener);
  }

  private void setErrorsMessages(Map<String, String> error) {
    Set<String> fields = error.keySet();

    labelErrorName.setText((fields.contains("name") ? error.get("name") : ""));
    labelErrorEmail.setText((fields.contains("email") ? error.get("email") : ""));
    labelErrorSalary.setText((fields.contains("baseSalary") ? error.get("baseSalary") : ""));
    labelErrorBirthDate.setText((fields.contains("birthDate") ? error.get("birthDate") : ""));

  }

  private void initializeComboBoxDepartment() {
    Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
      @Override
      protected void updateItem(Department item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? "" : item.getName());
      }
    };
    comboBoxDepartment.setCellFactory(factory);
    comboBoxDepartment.setButtonCell(factory.call(null));
  }
}
