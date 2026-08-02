package com.AppProject.GFinanceiro.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.javaFx.JavaFxApplication;
import com.AppProject.GFinanceiro.util.Alerts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
    loadView("/views/SellerList.fxml", (SellerListController controller) -> {
      controller.updateTableView();
    });
  }

  @FXML
  public void onMenuItemDepartmentAction() {
    loadView("/views/DepartmentList.fxml", (DepartmentListController controller) -> {
      controller.updateTableView();
    });
  }

  @FXML
  public void onMenuItemAboutAction() {
    loadView("/views/About.fxml", x -> {
    });
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
  }

  private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
      loader.setControllerFactory(JavaFxApplication.getContext()::getBean);
      VBox vbox = loader.load();

      Scene mainScene = JavaFxApplication.getMainScene();
      VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

      Node mainMenu = mainVbox.getChildren().get(0);
      mainVbox.getChildren().clear();
      mainVbox.getChildren().add(mainMenu);
      mainVbox.getChildren().addAll(vbox.getChildren());

      T controller = loader.getController();
      initializingAction.accept(controller);
    } catch (IOException e) {
      e.printStackTrace();
      Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
    }
  }

}
