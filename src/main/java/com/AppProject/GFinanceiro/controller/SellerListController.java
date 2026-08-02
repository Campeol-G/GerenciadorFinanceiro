package com.AppProject.GFinanceiro.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Seller;
import com.AppProject.GFinanceiro.exception.DbException;
import com.AppProject.GFinanceiro.javaFx.JavaFxApplication;
import com.AppProject.GFinanceiro.listeners.DataChangeListener;
import com.AppProject.GFinanceiro.service.SellerService;
import com.AppProject.GFinanceiro.util.Alerts;
import com.AppProject.GFinanceiro.util.Utils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class SellerListController implements Initializable, DataChangeListener {

  private final SellerService service;

  public SellerListController(SellerService service) {
    this.service = service;
  }

  @FXML
  private TableView<Seller> tableViewSeller;

  @FXML
  private TableColumn<Seller, String> tableColumnId;

  @FXML
  private TableColumn<Seller, String> tableColumnName;

  @FXML
  private TableColumn<Seller, String> tableColumnEmail;

  @FXML
  private TableColumn<Seller, Instant> tableColumnBirthDate;

  @FXML
  private TableColumn<Seller, Double> tableColumnBaseSalary;

  @FXML
  private TableColumn<Seller, Seller> tableColumnEDIT;

  @FXML
  private TableColumn<Seller, Seller> tableColumnREMOVE;

  @FXML
  private Button btNew;

  @FXML
  private ObservableList<Seller> obslist;

  @FXML
  public void onButtonNewAction(ActionEvent event) {
    Stage parentStage = Utils.currentStage(event);
    Seller obj = new Seller();

    createDialogForm(obj, parentStage, "/views/SellerForm.fxml");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    InitializeNodes();
  }

  private void InitializeNodes() {
    tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
    tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    Utils.formatTableColumnInstant(tableColumnBirthDate);
    Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);

    Stage stage = (Stage) JavaFxApplication.getMainScene().getWindow();
    tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
  }

  public void updateTableView() {
    List<Seller> list = service.findAll();

    obslist = FXCollections.observableArrayList(list);
    tableViewSeller.setItems(obslist);
    initEditButtons();
    initRemoveButtons();
  }

  private void createDialogForm(Seller obj, Stage parentStage, String absoluteName) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
      loader.setControllerFactory(JavaFxApplication.getContext()::getBean);
      Pane pane = loader.load();

      SellerFormController controller = loader.getController();
      controller.setEntity(obj);
      controller.loadAssociatedObjects();
      controller.subscribeDataChangeListener(this);
      controller.updateFormData();

      Stage dialogStage = new Stage();
      dialogStage.setTitle("Enter Seller data: ");
      dialogStage.setScene(new Scene(pane));
      dialogStage.setResizable(false);
      dialogStage.initOwner(parentStage);
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.showAndWait();
    } catch (IOException e) {
      Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(),
          AlertType.ERROR);
      e.printStackTrace();
    }
  }

  @Override
  public void onDataChanged() {
    updateTableView();
  }

  private void initEditButtons() {
    tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
      private final Button button = new Button("edit");

      @Override
      protected void updateItem(Seller obj, boolean empty) {
        super.updateItem(obj, empty);
        if (obj == null) {
          setGraphic(null);
          return;
        }
        setGraphic(button);
        button.setOnAction(
            event -> createDialogForm(obj, Utils.currentStage(event),
                "/views/SellerForm.fxml"));
      }
    });
  }

  private void initRemoveButtons() {
    tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
      private final Button button = new Button("remove");

      @Override
      protected void updateItem(Seller obj, boolean empty) {
        super.updateItem(obj, empty);
        if (obj == null) {
          setGraphic(null);
          return;
        }
        setGraphic(button);
        button.setOnAction(event -> removeEntity(obj));
      }
    });
  }

  private void removeEntity(Seller obj) {
    Optional<ButtonType> result = Alerts.showConfirmation("showConfirmation", "Are you sure to delete?");

    if (result.get() == ButtonType.OK) {
      try {
        service.delete(obj);
        updateTableView();
      } catch (DbException e) {
        Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
      }
    }
  }
}
