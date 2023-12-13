package com.example.clockfx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends ClockShop implements IObserver{

    @FXML
    ComboBox Arrows;
    @FXML
    TextField BrandInp;
    @FXML
    TextField PriceInp;
    @FXML
    TextField SInp;
    @FXML
    TextField MInp;
    @FXML
    TextField HInp;
    @FXML
    Button binUplBtn;
    @FXML
    Button binDwnlBtn;
    @FXML
    Button jsonUplBtn;
    @FXML
    Button jsonDwnlBtn;
    @FXML
    GridPane allClocks;
    @FXML
    Label opInfo;
    ClockShop clockShop = ClockShopFabrica.build();

    public MainController(){
        clockShop.sub(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView binDwnlImage = new ImageView(getClass().getResource("/com/images/binDwnl.png").toExternalForm());
        binDwnlImage.setFitHeight(30);
        binDwnlImage.setFitWidth(30);
        binDwnlBtn.setGraphic(binDwnlImage);

        ImageView binUplImage = new ImageView(getClass().getResource("/com/images/binUpl.png").toExternalForm());
        binUplImage.setFitHeight(30);
        binUplImage.setFitWidth(30);
        binUplBtn.setGraphic(binUplImage);

        ImageView jsonDwnlImage = new ImageView(getClass().getResource("/com/images/jsonDwnl.png").toExternalForm());
        jsonDwnlImage.setFitHeight(30);
        jsonDwnlImage.setFitWidth(30);
        jsonDwnlBtn.setGraphic(jsonDwnlImage);

        ImageView jsonUplImage = new ImageView(getClass().getResource("/com/images/jsonUpl.png").toExternalForm());
        jsonUplImage.setFitHeight(30);
        jsonUplImage.setFitWidth(30);
        jsonUplBtn.setGraphic(jsonUplImage);

        opInfo.setVisible(false);

        Arrows.getItems().addAll("two","three");
    }

    public void AddClock(ActionEvent actionEvent) {

        try {
            Clock c;
            if (Arrows.getSelectionModel().getSelectedItem() == null) {
                c = new Clock2Arrows();
            }
            else {
                if (Arrows.getSelectionModel().getSelectedItem().toString().equals("three")){
                c = new Clock3Arrows();
            }
            else c = new Clock2Arrows();}

            if (BrandInp.getText().isEmpty()) c.setBrand("Unknown");
            else c.setBrand(BrandInp.getText());

            if (PriceInp.getText().isEmpty()) c.setPrice(0);
            else c.setPrice(Integer.parseInt(PriceInp.getText()));

            if (HInp.getText().isEmpty()) c.setTime(Types.HOURS, 0);
            else c.setTime(Types.HOURS, Integer.parseInt(HInp.getText()));
            if (MInp.getText().isEmpty()) c.setTime(Types.MINUTES, 0);
            else c.setTime(Types.MINUTES, Integer.parseInt(MInp.getText()));
            if (c.getType().equals(Types.THREEARROWS)) {
                if (SInp.getText().isEmpty()) c.setTime(Types.SECONDS, 0);
                else c.setTime(Types.SECONDS, Integer.parseInt(SInp.getText()));
            }
            /*if (Arrows.getSelectionModel() != null ) {
                if (Arrows.getSelectionModel().getSelectedItem().equals("two")) {
                    c = new Clock2Arrows();
                    if (HInp.getText() != null && MInp.getText() != null) {
                        c.setTime(Types.HOURS, Integer.parseInt(HInp.getText()));
                        c.setTime(Types.MINUTES, Integer.parseInt((MInp.getText())));
                    }
                    else {
                        c.setTime(Types.HOURS, 0);
                        c.setTime(Types.HOURS, 0);
                    }

                }
                else {
                    c = new Clock3Arrows();
                    if (HInp.getText() != null && MInp.getText() != null && SInp.getText() != null) {
                        c.setTime(Types.HOURS, Integer.parseInt(HInp.getText()));
                        c.setTime(Types.MINUTES, Integer.parseInt((MInp.getText())));
                        c.setTime(Types.SECONDS, Integer.parseInt((SInp.getText())));
                    }
                    else {
                        c.setTime(Types.HOURS, 0);
                        c.setTime(Types.MINUTES, 0);
                        c.setTime(Types.SECONDS, 0);
                    }

                }
            }
            else {
                c = new Clock2Arrows("Unknown", 0, 0,0);
            }

        if (PriceInp.getText() != null && BrandInp.getText() != null) {
            c.setPrice(Integer.parseInt(PriceInp.getText()));
            c.setBrand(BrandInp.getText());
        }
        else {
            c.setPrice(0);
            c.setBrand("Unknown");
        }*/
            c.setId(clockShop.getSize());
            clockShop.addClock(c);
        } catch (TimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void event(ClockShop с) {
        allClocks.getChildren().clear();
        for (Clock c : clockShop){
            try {
                ClockController clockController = new ClockController();
                FXMLLoader fxmlLoader = new FXMLLoader(ClockController.class.getResource("clock-panel.fxml"));
                fxmlLoader.setController(clockController);
                Parent root = fxmlLoader.load();
                clockController.setClock(c);
                allClocks.addColumn(0, root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTime(){
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
                    clockShop.setTime(Types.valueOf(comboBox.getValue()), Integer.parseInt(textField.getText()));
                }catch(TimeException e){
                    dialog.close();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    setTime();
                }
                return null;
            }
            else { return null;}
        });dialog.showAndWait();}

    public void showMostCommon(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("The most common brand is: " + clockShop.getMostCommonBrand() + " — " + clockShop.getMostCommonBrandCount() +"pts.");
        alert.showAndWait();
    }

    public void showMostValuable() throws TimeException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("The most valuable clock description is:\n" + clockShop.getMostExpensiveDescription());
        alert.showAndWait();
    }

    public void sort() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("The alphabetically sorted list of brands:\n" + clockShop.sortBrandNames().toString());
        alert.showAndWait();
    }

    @FXML
    public void LoadBin() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(opInfo.getScene().getWindow());
        if (file != null) {
            try {
                opInfo.setText("File opened from: " + file.getPath());
                opInfo.setVisible(true);
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                ClockShop clocks = (ClockShop) inputStream.readObject();
                for (Clock c : clocks) clockShop.addClock(c);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void DownloadBin() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(opInfo.getScene().getWindow());
        if (file != null){
            try {
                opInfo.setText("File saved (.bin). Path:"+file.getPath());
                opInfo.setVisible(true);
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                outputStream.writeObject(clockShop);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void LoadJson(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(opInfo.getScene().getWindow());
        if (file!=null){
            opInfo.setText("File opened from: " + file.getPath());
            opInfo.setVisible(true);
            try {
                FileReader fileReader = new FileReader(file);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(Clock.class, new ClockAdapter())
                        .create();
                ClockShop clocks = gson.fromJson(fileReader, ClockShop.class);
                for(Clock c : clocks) clockShop.addClock(c);
            } catch (FileNotFoundException e ){
                throw  new RuntimeException(e);
            }
        }
    }

    @FXML
    public void DownloadJson() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(opInfo.getScene().getWindow());
        if (file != null) {
            try {
                opInfo.setText("File saved (.json). Path: "+file.getPath());
                opInfo.setVisible(true);
                FileWriter output = new FileWriter(file);
                Gson gson = new GsonBuilder().setPrettyPrinting()
                        .registerTypeAdapter(Clock.class, new ClockAdapter())
                        .create();
                String string = gson.toJson(clockShop);
                output.write(string);
                output.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}