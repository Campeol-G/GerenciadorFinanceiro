package com.AppProject.GFinanceiro.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Utils {

  public static Stage currentStage(ActionEvent event) {
    return (Stage) ((Node) event.getSource()).getScene().getWindow();
  }

  public static <T> void formatTableColumnInstant(TableColumn<T, Instant> tableColumn) {
    tableColumn.setCellFactory(column -> {
      TableCell<T, Instant> cell = new TableCell<T, Instant>() {
        private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.systemDefault());

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

  private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

  public static LocalDate instantToLocalDate(Instant instant) {
    if (instant == null) {
      return null;
    }
    return instant.atZone(DEFAULT_ZONE).toLocalDate();
  }

  public static Instant localDateToInstant(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.atStartOfDay(DEFAULT_ZONE).toInstant();
  }

  public static void formatDatePicker(DatePicker datePicker, String format) {
    datePicker.setConverter(new StringConverter<LocalDate>() {
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
      {
        datePicker.setPromptText(format.toLowerCase());
      }

      @Override
      public String toString(LocalDate date) {
        if (date != null) {
          return dateFormatter.format(date);
        } else {
          return "";
        }
      }

      @Override
      public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
          return LocalDate.parse(string, dateFormatter);
        } else {
          return null;
        }
      }
    });
  }
}
