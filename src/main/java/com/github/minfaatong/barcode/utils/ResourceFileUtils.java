package com.github.minfaatong.barcode.utils;

import java.io.*;
import java.net.URL;

public class ResourceFileUtils {

    public static final URL toResourceUrl(String path) {
        return ResourceFileUtils.class.getClassLoader().getResource(path);
    }

    public static String readFromFile(String path) throws IOException {
        File file = new File(path);
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
