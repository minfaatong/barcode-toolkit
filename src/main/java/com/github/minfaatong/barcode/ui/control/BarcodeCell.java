package com.github.minfaatong.barcode.ui.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.github.minfaatong.barcode.ui.model.BarcodeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class BarcodeCell extends ListCell<BarcodeModel> {
    private static final Logger logger = LoggerFactory.getLogger(BarcodeCell.class);
    @FXML
    private GridPane gridPane;
    @FXML
    private Label lblBarcodeText;
    @FXML
    private Hyperlink lnkCopy;
    private FXMLLoader loader;
    private Gson gson;

    public BarcodeCell() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    protected void updateItem(BarcodeModel item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                URL res = this.getClass().getClassLoader().getResource("fx/barcode_cell.fxml");
                logger.debug("res = {}", res);
                try {
                    FXMLLoader loader = new FXMLLoader(res);
                    loader.setController(this);
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.info("item = {} ({})", item, item.getClass());
            lblBarcodeText.setText(item.getText());
            lnkCopy.setAccessibleText(item.getText());
            lnkCopy.setOnAction(this::useHistoryBarcode);
            setText(null);
            setGraphic(gridPane);
        }
    }

    public void useHistoryBarcode(ActionEvent actionEvent) {
        Object objSrc = actionEvent.getSource();
        logger.info("event source = {}", objSrc);
        if (objSrc instanceof Hyperlink) {
            final Hyperlink mHyperlink = (Hyperlink) objSrc;
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(mHyperlink.getAccessibleText());
            clipboard.setContent(content);
        }
    }
}