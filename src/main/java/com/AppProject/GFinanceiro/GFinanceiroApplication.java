package com.AppProject.GFinanceiro;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.AppProject.GFinanceiro.javaFx.JavaFxApplication;

import javafx.application.Application;

@SpringBootApplication
public class GFinanceiroApplication {

  public static void main(String[] args) {
    Application.launch(JavaFxApplication.class, args);
  }

}
