package com.AppProject.GFinanceiro.controller;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
import com.AppProject.GFinanceiro.javaFx.JavaFxApplication;
import com.AppProject.GFinanceiro.listeners.DataChangeListener;
import com.AppProject.GFinanceiro.service.DepartmentService;
import com.AppProject.GFinanceiro.util.Alerts;
import com.AppProject.GFinanceiro.util.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class DepartmentListController implements Initializable, DataChangeListener {

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
  public void onButtonNewAction(ActionEvent event) {
    Stage parentStage = Utils.currentStage(event);
    Department obj = new Department();

    createDialogForm(obj, parentStage, "/views/DepartmentForm.fxml");
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

  private void createDialogForm(Department obj, Stage parentStage, String absoluteName) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
      loader.setControllerFactory(JavaFxApplication.getContext()::getBean);
      Pane pane = loader.load();

      DepartmentFormController controller = loader.getController();
      controller.setEntity(obj);
      controller.subscribeDataChangeListener(this);
      controller.updateFormData();

      Stage dialogStage = new Stage();
      dialogStage.setTitle("Enter Department data: ");
      dialogStage.setScene(new Scene(pane));
      dialogStage.setResizable(false);
      dialogStage.initOwner(parentStage);
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.showAndWait();
    } catch (IOException e) {
      Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
    }
  }

  @Override
  public void onDataChanged() {
    updateTableView();
  }

}
