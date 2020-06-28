package com.github.minfaatong.barcode.ui.control;

import com.github.minfaatong.barcode.service.IBarcodeService;
import com.github.minfaatong.barcode.service.impl.BarcodeServiceImpl;
import com.github.minfaatong.barcode.ui.model.BarcodeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class BarcodePane extends Application implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(BarcodePane.class);
    private Parent root;
    public TextArea txaText;
    public ImageView imgBarcode;
    public Button btnSave;
    public Button btnLoad;
    public Button btnEncode;
    public Button btnDecode;
    public Button btnClose;
    public ListView lsvHistory;
    private IBarcodeService barcodeSvc;
    private ArrayList<BufferedImage> barcodeBuffers;
    private ObservableList<BarcodeModel> lsObservedHistory;
    private int lastBufferedIndex = -1;
    private Gson gson;
    private static final int PREF_WIDTH = 512;
    private static final int PREF_HEIGHT = 512;

    public BarcodePane() {
        super();
        this.barcodeSvc = new BarcodeServiceImpl();
        this.barcodeBuffers = new ArrayList<>();
        this.gson = new GsonBuilder().create();
        initUIComponents();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    private void initUIComponents() {
        lsObservedHistory = FXCollections.observableArrayList();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL res = this.getClass().getClassLoader().getResource("fx/barcode.fxml");
        logger.debug("res = {}", res);
        FXMLLoader loader = new FXMLLoader(res);
        logger.debug("loader = {}", loader);
        root = FXMLLoader.load(res);
        primaryStage.setTitle("Barcode UI");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void saveBarcode(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Barcode File");
        final ExtensionFilter extFilter = new ExtensionFilter("JPEG files (*.jpeg)", "*.jpg", "jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("out.jpg");
        final File userHome = new File(System.getProperty("user.home"));

        fileChooser.setInitialDirectory(userHome);
        File outputFile = fileChooser.showSaveDialog(null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imgBarcode.snapshot(null, null), null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            logger.error("Error while saving image", e);
            throw new RuntimeException(e);
        }
    }

    public void loadBarcode(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        final ExtensionFilter extFilter = new ExtensionFilter("JPEG files (*.jpeg)", "*.jpg", "jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("out.jpg");
        final File userHome = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(userHome);
        File inputFile = fileChooser.showOpenDialog(null);
        logger.info("input file = {}", inputFile);
        try {
            BufferedImage bImg = ImageIO.read(inputFile);
            barcodeBuffers.add(bImg);
            lastBufferedIndex = barcodeBuffers.indexOf(bImg);
            Image img = SwingFXUtils.toFXImage(bImg, null);
            imgBarcode.setImage(img);
            decodeBarcode(null);
        } catch (IOException e) {
            logger.error("Error while loading image", e);
            throw new RuntimeException(e);
        }
    }

    public void encodeBarcode(ActionEvent actionEvent) {
        BufferedImage bImg = null;
        logger.info("taText = {}", txaText);
        logger.info("text = {}", txaText.getText());
        try {
            String text = new String(txaText.getText().getBytes(), StandardCharsets.ISO_8859_1.toString());
            bImg = barcodeSvc.encodeTextToBarcode(text, PREF_WIDTH, PREF_HEIGHT);
            barcodeBuffers.add(bImg);
            lastBufferedIndex = barcodeBuffers.indexOf(bImg);
            updateHistory(text, bImg);
        } catch (Exception e) {
            logger.error("Error while encoding text to barcode", e);
        }

        if (bImg == null) {
            logger.warn("failure to encode image");
            return;
        }
        Image img = SwingFXUtils.toFXImage(bImg, null);
        imgBarcode.setImage(img);
    }

    public void decodeBarcode(ActionEvent actionEvent) {
        String text = null;
        try {
            final BufferedImage bImg = barcodeBuffers.get(lastBufferedIndex);
            text = barcodeSvc.decodeBarcodeToText(bImg);
            updateHistory(text, bImg);
            logger.info("lsvHistory({}) = {}", CollectionUtils.isEmpty(lsvHistory.getItems()) ? 0 : lsvHistory.getItems().size(), lsvHistory.getItems());
        } catch (Exception e) {
            logger.error("Error while decoding barcode to text", e);
        }
        if (StringUtils.isBlank(text)) {
            return;
        }
        txaText.setText(text);
    }

    private void updateHistory(String text, BufferedImage barcodeImage) {
        BarcodeModel model = new BarcodeModel(text, barcodeImage);
        lsObservedHistory.add(model);
    }

    public void closeApp(ActionEvent actionEvent) {
        exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lsvHistory.setItems(lsObservedHistory);
        lsvHistory.setEditable(false);
        lsvHistory.setCellFactory(studentListView -> new BarcodeCell());
    }
}
