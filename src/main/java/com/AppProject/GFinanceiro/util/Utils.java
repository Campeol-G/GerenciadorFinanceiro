package com.AppProject.GFinanceiro.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class Utils {

  public static Stage currentStage(ActionEvent event) {
    return (Stage) ((Node) event.getSource()).getScene().getWindow();
  }

  public static <T> void formatTableColumnInstant(TableColumn<T, Instant> tableColumn) {
    tableColumn.setCellFactory(column -> {
      TableCell<T, Instant> cell = new TableCell<T, Instant>() {
        private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.of("America/Sao_Paulo"));

        @Override
        protected void updateItem(Instant item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || item == null) {
            setText(null);
          } else {
            setText(dtf.format(item));
          }
        }
      };
      return cell;
    });
  }

  public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
    tableColumn.setCellFactory(column -> {
      TableCell<T, Double> cell = new TableCell<T, Double>() {
        @Override
        protected void updateItem(Double item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || item == null) {
            setText(null);
          } else {
            Locale.setDefault(Locale.US);
            setText(String.format("%." + decimalPlaces + "f", item));
          }
        }
      };
      return cell;
    });
  }
}
