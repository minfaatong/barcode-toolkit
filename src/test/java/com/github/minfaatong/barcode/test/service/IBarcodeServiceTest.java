package com.github.minfaatong.barcode.test.service;

import com.github.minfaatong.barcode.service.IBarcodeService;
import com.github.minfaatong.barcode.service.impl.BarcodeServiceImpl;
import com.github.minfaatong.barcode.test.utils.TestResourceUtils;
import com.google.zxing.WriterException;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.github.minfaatong.barcode.test.utils.TestResourceUtils.readFromFile;

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
        final String longText = readFromFile("longText.txt");
        logger.info("long text = {}", longText);
        final BufferedImage actual;
        try {
            actual = barcodeService.encodeTextToBarcode(longText);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.warn("Error while encoding text to barcode", e);
            assertTrue(e instanceof WriterException);
            assertEquals("Data too big", e.getLocalizedMessage());
        }
    }
}
