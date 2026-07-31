package com.AppProject.GFinanceiro.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.javaFx.JavaFxApplication;
import com.AppProject.GFinanceiro.util.Alerts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DepartmentListController implements Initializable {

  @FXML
  private TableView<Department> tableViewDepartment;

  @FXML
  private TableColumn<Department, Integer> tableColumnId;

  @FXML
  private TableColumn<Department, String> tableColumnName;

  @FXML
  private Button btNew;

  @FXML
  public void onButtonNewAction() {
    System.out.println("new");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    InitializeNodes();
  }

  private void InitializeNodes() {
    tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

    Stage stage = (Stage) JavaFxApplication.getMainScene().getWindow();
    tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
  }
}
