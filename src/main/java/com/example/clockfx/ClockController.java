package com.example.clockfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ClockController {
    @FXML
    Label BrandValue;
    @FXML
    Label PriceValue;
    @FXML
    Label TypeValue;
    @FXML
    Label TimeValue;
    @FXML
    Label NumLabel;
    ClockShop clockShop = ClockShopFabrica.build();
    Clock ref;
    @FXML
    public void skipTime(){
        Dialog<String> dialog = new Dialog<>();
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        TextField textField = new TextField();
        textField.setPromptText("Enter the value");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Choose hours, minutes or seconds");
        comboBox.getItems().addAll("HOURS", "MINUTES", "SECONDS");
        VBox v= new VBox();
        v.getChildren().addAll(textField,comboBox);
        dialog.getDialogPane().setContent(v);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                try {
                    clockShop.skipTimeAt(Types.valueOf(comboBox.getValue()), Integer.parseInt(textField.getText()), clockShop.getIndex(ref));
                }catch(TimeException e){
                    dialog.close();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    skipTime();
                }
                return null;
            }
            else { return null;}
        });dialog.showAndWait();
    }

    @FXML
    public void setClock(Clock _cl) {
        this.ref = _cl;
        if (_cl.getType() == Types.TWOARROWS){
            TypeValue.setText("2 arrows");
        }
        else { TypeValue.setText("3 arrows"); }
        BrandValue.setText(_cl.getBrand());
        PriceValue.setText(Integer.toString(_cl.getPrice()));
        TimeValue.setText(_cl.getTime());
        NumLabel.setText(Integer.toString(clockShop.getIndex(_cl) + 1));
    }
    @FXML
    public void changeTime(){
        Dialog<String> dialog = new Dialog<>();
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        TextField textField = new TextField();
        textField.setPromptText("Enter the value");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Choose hours, minutes or seconds");
        comboBox.getItems().addAll("HOURS", "MINUTES", "SECONDS");
        VBox v= new VBox();
        v.getChildren().addAll(textField,comboBox);
        dialog.getDialogPane().setContent(v);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                try {
                    clockShop.setTimeAt(Types.valueOf(comboBox.getValue()), Integer.parseInt(textField.getText()), clockShop.getIndex(ref));
                }catch(TimeException e){
                    dialog.close();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    changeTime();
                }
                return null;
            }
            else { return null;}
        });dialog.showAndWait();
    }
    @FXML
    public void RemoveClock() throws TimeException {
        clockShop.remove(ref);
    }
}
