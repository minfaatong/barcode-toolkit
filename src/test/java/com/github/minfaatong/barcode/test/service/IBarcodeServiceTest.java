package com.github.minfaatong.barcode.test.service;

import com.github.minfaatong.barcode.service.IBarcodeService;
import com.github.minfaatong.barcode.service.impl.BarcodeServiceImpl;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.github.minfaatong.barcode.utils.ResourceFileUtils.readFromFile;
import static com.github.minfaatong.barcode.utils.ResourceFileUtils.toResourceUrl;

public class IBarcodeServiceTest extends TestCase {
    private static final Logger logger = LoggerFactory.getLogger(IBarcodeServiceTest.class);
    private IBarcodeService barcodeService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        barcodeService = new BarcodeServiceImpl();
    }

    public void test_encodeTextToBarcode_Ok() throws Exception {
        final BufferedImage actual = barcodeService.encodeTextToBarcode("test");
        assertNotNull(actual);
    }

    public void test_encodeTextToBarcode_TextTooLong() throws IOException {
        final String longText = readFromFile(toResourceUrl("longText.txt").getPath());
        BufferedImage actual = null;
        try {
            actual = barcodeService.encodeTextToBarcode(longText);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(e instanceof WriterException);
            assertEquals("Data too big", e.getLocalizedMessage());
        }
        assertEquals(null, actual);
    }

    public void test_decodeBarcodeToText_Ok() throws IOException, NotFoundException {
        final BufferedImage img = ImageIO.read(toResourceUrl("test1.jpg"));

        final String actual = barcodeService.decodeBarcodeToText(img);

        assertEquals("test", actual);
    }

    public void test_decodeBarcodeToText_BadBarcode() throws IOException {
        final BufferedImage img = ImageIO.read(toResourceUrl("test_bad.jpg"));

        String actual = null;
        try {
            actual = barcodeService.decodeBarcodeToText(img);
        } catch (NotFoundException e) {
            logger.warn("Error while decoding barcode to text", e);
            assertTrue(e instanceof NotFoundException);
        }

        assertEquals(null, actual);
    }
}
