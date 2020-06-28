package com.github.minfaatong.barcode.service;

import com.google.zxing.NotFoundException;

import java.awt.image.BufferedImage;

public interface IBarcodeService {
    BufferedImage encodeTextToBarcode(String text) throws Exception;

    BufferedImage encodeTextToBarcode(String text, int width, int height) throws Exception;

    String decodeBarcodeToText(BufferedImage barcodeImage) throws NotFoundException;
}

