package com.AppProject.GFinanceiro.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.javaFx.JavaFxApplication;
import com.AppProject.GFinanceiro.service.DepartmentService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Controller
public class DepartmentListController implements Initializable {

  private final DepartmentService service;

  public DepartmentListController(DepartmentService service) {
    this.service = service;
  }

  @FXML
  private TableView<Department> tableViewDepartment;

  @FXML
  private TableColumn<Department, String> tableColumnId;

  @FXML
  private TableColumn<Department, String> tableColumnName;

  @FXML
  private Button btNew;

  @FXML
  private ObservableList<Department> obslist;

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

  public void updateTableView() {
    List<Department> list = service.findAll();

    obslist = FXCollections.observableArrayList(list);
    tableViewDepartment.setItems(obslist);
  }
}
