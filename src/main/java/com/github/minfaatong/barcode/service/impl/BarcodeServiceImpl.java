package com.github.minfaatong.barcode.service.impl;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.github.minfaatong.barcode.service.IBarcodeService;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BarcodeServiceImpl implements IBarcodeService {
    private static final int DEFAULT_WITDTH = 256;
    private static final int DEFAULT_HEIGHT = 256;

    @Override
    public BufferedImage encodeTextToBarcode(String text) throws Exception {
        return encodeTextToBarcode(text, DEFAULT_WITDTH, DEFAULT_HEIGHT);
    }

    @Override
    public BufferedImage encodeTextToBarcode(String text, int width, int height) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public String decodeBarcodeToText(BufferedImage barcodeImage) throws NotFoundException {
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        Result qrCodeResult = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(barcodeImage))));
        if (qrCodeResult == null) return null;
        return qrCodeResult.getText();
    }
}
