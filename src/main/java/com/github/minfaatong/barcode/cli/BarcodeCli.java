package com.github.minfaatong.barcode.cli;

import com.github.minfaatong.barcode.service.IBarcodeService;
import com.github.minfaatong.barcode.service.impl.BarcodeServiceImpl;
import com.github.minfaatong.barcode.ui.BarcodeGui;
import com.github.minfaatong.barcode.utils.ResourceFileUtils;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class BarcodeCli {
    private static final Logger logger = LoggerFactory.getLogger(BarcodeCli.class);
    private IBarcodeService barcodeService;

    @Inject
    public BarcodeCli( IBarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    public static void main(String[] args) throws Exception {
        BarcodeCli cli = new BarcodeCli(new BarcodeServiceImpl());
        cli.runCommands(args);
    }

    public void runCommands(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("e", "encode", false, "encode mode (Cli ONLY)");
        options.addOption("d", "decode", false, "decode mode (Cli ONLY)");
        options.addOption("i", "input", true, "input file name (barcode image)");
        options.addOption("t", "text", true, "text to convert");
        options.addOption("o", "output", true, "output file name (barcode image)");
        options.addOption("g", "gui", false, "show gui");
        String header = "Barcode toolkit\n\n";
        String footer = "";
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption('g') || cmd.hasOption("gui")) {
                BarcodeGui.main(args);
                return;
            } else if ((cmd.hasOption('e') || cmd.hasOption("encode"))
                    &&(cmd.hasOption('t') || cmd.hasOption("text"))
                    && (cmd.hasOption('o') || cmd.hasOption("output"))) {
                String text = new String(cmd.getOptionValue('t').getBytes(), StandardCharsets.ISO_8859_1.toString());
                String output = cmd.getOptionValue('o');
                BufferedImage bImg = barcodeService.encodeTextToBarcode(text, 512, 512);
                ImageIO.write(bImg, "jpg", new File(output));
                return;
            } else if ((cmd.hasOption('d') || cmd.hasOption("decode"))
                    &&(cmd.hasOption('i') || cmd.hasOption("input"))) {
                final String input = cmd.getOptionValue('i');
                BufferedImage bImg = ImageIO.read(new File(input));
                final String text = barcodeService.decodeBarcodeToText(bImg);
                logger.info("text = {}", text);
                return;
            }
            formatter.printHelp("barcode-toolkit", header, options, footer, true);
        } catch (ParseException e) {
            logger.error("Error execute command", e);
            formatter.printHelp("barcode-toolkit", header, options, footer, true);
        }
    }
}
