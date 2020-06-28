package com.github.minfaatong.barcode.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class BarcodeGui extends Application {
    private static final Logger logger = LoggerFactory.getLogger(BarcodeGui.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL res = this.getClass().getClassLoader().getResource("fx/barcode.fxml");
        logger.debug("res = {}", res);
        FXMLLoader loader = new FXMLLoader(res);
        logger.debug("loader = {}", loader);
        Parent root = FXMLLoader.load(res);
        primaryStage.setTitle("Barcode UI");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
