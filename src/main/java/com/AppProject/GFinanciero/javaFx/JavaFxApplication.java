package com.AppProject.GFinanciero.javaFx;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.AppProject.GFinanciero.GFinancieroApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

  private ConfigurableApplicationContext context;

  @Override
  public void init() throws Exception {
    context = new SpringApplicationBuilder(GFinancieroApplication.class).run();
  }

  @Override
  public void start(Stage stage) {
    try {
      System.out.println("try");
      FXMLLoader loarder = new FXMLLoader(
          getClass().getResource("/views/MainView.fxml"));

      loarder.setControllerFactory(context::getBean);
      Parent root = loarder.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void stop() throws Exception {
    context.close();
  }

}
