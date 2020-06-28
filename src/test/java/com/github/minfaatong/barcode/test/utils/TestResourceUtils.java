package com.github.minfaatong.barcode.test.utils;

import java.io.*;
import java.net.URL;

public class TestResourceUtils {
    public static String readFromFile(String res) throws IOException {
        URL url = TestResourceUtils.class.getClassLoader().getResource(res);
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
