package com.github.minfaatong.barcode.cli;

import com.github.minfaatong.barcode.service.IBarcodeService;
import com.github.minfaatong.barcode.service.impl.BarcodeServiceImpl;
import com.github.minfaatong.barcode.ui.BarcodeGui;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class BarcodeCli {
    private static final Logger logger = LoggerFactory.getLogger(BarcodeCli.class);
    private IBarcodeService barcodeService;

    public BarcodeCli() {
        this.barcodeService = new BarcodeServiceImpl();
    }

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("t", "text", true, "text to convert");
        options.addOption("o", "output", true, "converted output file name");
        options.addOption("g", "gui", false, "show gui");
        String header = "Barcode toolkit\n\n";
        String footer = "";
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            BarcodeCli instance = new BarcodeCli();
            if (cmd.hasOption("g")) {
                // new BarcodePane().launch(args);
                BarcodeGui.main(args);
                return;
            }
            if (cmd.hasOption("t") && cmd.hasOption("o")) {
                String text = new String(cmd.getOptionValue('t').getBytes(), StandardCharsets.ISO_8859_1.toString());
                String output = cmd.getOptionValue('o');
                BufferedImage bImg = instance.barcodeService.encodeTextToBarcode(text, 512, 512);
                ImageIO.write(bImg, "jpg", new File(output));
                return;
            }
            formatter.printHelp("barcode-toolkit", header, options, footer, true);
        } catch (ParseException e) {
            logger.error("Error execute command", e);
            formatter.printHelp("barcode-toolkit", header, options, footer, true);
        }
    }
}
