package com.github.minfaatong.barcode.test.cli;

import com.github.minfaatong.barcode.cli.BarcodeCli;
import com.github.minfaatong.barcode.service.IBarcodeService;
import junit.framework.TestCase;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import static com.github.minfaatong.barcode.utils.ResourceFileUtils.toResourceUrl;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class BarcodeCliTest extends TestCase {
//    @InjectMocks
    private BarcodeCli cli;
//    @Mock
    private IBarcodeService barcodeService;

    @Override
    protected void setUp() throws Exception {
        this.barcodeService = mock(IBarcodeService.class);
        this.cli = new BarcodeCli(barcodeService);
    }

    public void skip_test_cli_encode_Ok() throws Exception {
        final BufferedImage bImg = ImageIO.read(toResourceUrl("test1.jpg"));
        when(barcodeService.encodeTextToBarcode(anyString())).thenReturn(bImg);

        try {
            cli.runCommands(new String[]{"--encode", "--text", "test", "--output", toResourceUrl("out.jpg").getPath()});
//            BarcodeCli.main(new String[]{"--text", "test", "--output", TestResourceUtils.readFile("out.jpg").getPath()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mockito.verify(barcodeService, times(1)).encodeTextToBarcode(anyString());
    }

    public void skip_test_cli_decode_Ok() throws Exception {
        final BufferedImage bImg = ImageIO.read(toResourceUrl("test1.jpg"));
        String text = "test";
        when(barcodeService.decodeBarcodeToText(any(BufferedImage.class))).thenReturn(text);

        try {
            cli.runCommands(new String[]{"--decode", "--input", toResourceUrl("out.jpg").getPath()});
//            BarcodeCli.main(new String[]{"--text", "test", "--output", TestResourceUtils.readFile("out.jpg").getPath()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mockito.verify(barcodeService, times(1)).decodeBarcodeToText(any(BufferedImage.class));
    }

    public void test_cli_encode_illegalArgs() throws Exception {
        try {
            cli.runCommands(new String[]{"--happy"});
        } catch (Exception e) {
            e.printStackTrace();

            assertTrue(e instanceof UnrecognizedOptionException);
            assertEquals("Unrecognized option: --happy", e.getLocalizedMessage());
        }
        Mockito.verify(barcodeService, never()).encodeTextToBarcode(anyString());
    }
}
