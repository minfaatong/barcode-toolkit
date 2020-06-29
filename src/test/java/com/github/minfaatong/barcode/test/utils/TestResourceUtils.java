package com.github.minfaatong.barcode.test.utils;

import java.io.*;
import java.net.URL;

public class TestResourceUtils {

    public static final URL readFile(String path) {
        return TestResourceUtils.class.getClassLoader().getResource(path);
    }

    public static String readFromFile(String path) throws IOException {
        URL url = readFile(path);
        File file = new File(url.getPath());
        InputStream is = new FileInputStream(file);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append(System.lineSeparator());
            line = buf.readLine();
        }
        return sb.toString();
    }
}
