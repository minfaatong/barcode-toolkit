package com.github.minfaatong.barcode.ui.model;

import com.google.gson.JsonObject;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class BarcodeModel implements Serializable {
    private static final long serialVersionUID = -8583245737551582023L;
    private String text;
    private BufferedImage barcodeImage;

    public BarcodeModel() {
    }

    public BarcodeModel(String text, BufferedImage barcodeImage) {
        this.text = text;
        this.barcodeImage = barcodeImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BufferedImage getBarcodeImage() {
        return barcodeImage;
    }

    public void setBarcodeImage(BufferedImage barcodeImage) {
        this.barcodeImage = barcodeImage;
    }

    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);
        jsonObject.addProperty("barcodeImage", barcodeImage.toString());
        return jsonObject.toString();
    }
}