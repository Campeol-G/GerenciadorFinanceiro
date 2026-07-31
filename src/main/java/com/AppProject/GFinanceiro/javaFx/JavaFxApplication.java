package com.AppProject.GFinanceiro.javaFx;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.AppProject.GFinanceiro.GFinanceiroApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

  private ConfigurableApplicationContext context;

  @Override
  public void init() throws Exception {
    context = new SpringApplicationBuilder(GFinanceiroApplication.class).run();
  }

  @Override
  public void start(Stage stage) {
    try {
      System.out.println("try");
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/MainView.fxml"));

      loader.setControllerFactory(context::getBean);
      ScrollPane scrollPane = loader.load();
      scrollPane.setFitToHeight(true);
      scrollPane.setFitToWidth(true);
      Scene scene = new Scene(scrollPane);
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
