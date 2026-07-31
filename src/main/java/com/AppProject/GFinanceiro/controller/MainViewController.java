package com.AppProject.GFinanceiro.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.AppProject.GFinanceiro.entity.Department;
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
    System.out.println("onMenuItemSellerAction");
  }

  @FXML
  public void onMenuItemDepartmentAction() {
    loadView2("/views/DepartmentList.fxml");
  }

  @FXML
  public void onMenuItemAboutAction() {
    loadView("/views/About.fxml");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
  }

  private synchronized void loadView(String absoluteName) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
      VBox vbox = loader.load();

      Scene mainScene = JavaFxApplication.getMainScene();
      VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

      Node mainMenu = mainVbox.getChildren().get(0);
      mainVbox.getChildren().clear();
      mainVbox.getChildren().add(mainMenu);
      mainVbox.getChildren().addAll(vbox.getChildren());
    } catch (IOException e) {
      e.printStackTrace();
      Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
    }
  }

  private synchronized void loadView2(String absoluteName) {
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

      DepartmentListController controller = loader.getController();
      controller.updateTableView();
    } catch (IOException e) {
      e.printStackTrace();
      Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
    }
  }

}
