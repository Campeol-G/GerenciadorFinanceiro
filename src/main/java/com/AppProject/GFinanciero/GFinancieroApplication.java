package com.AppProject.GFinanciero;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.AppProject.GFinanciero.javaFx.JavaFxApplication;

import javafx.application.Application;

@SpringBootApplication
public class GFinancieroApplication {

  public static void main(String[] args) {
    Application.launch(JavaFxApplication.class, args);
  }

}
