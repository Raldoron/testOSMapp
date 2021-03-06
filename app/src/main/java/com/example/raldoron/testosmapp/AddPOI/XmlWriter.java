package com.example.raldoron.testosmapp.AddPOI;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by Raldoron on 02.12.15.
 */
public class XmlWriter {
    protected PrintWriter out;

    public XmlWriter(PrintWriter out) {
        this.out = out;
    }

    /**
     * Encode the given string in XML1.0 format.
     * Optimized to fast pass strings that don't need encoding (normal case).
     */
    public static String encode(String unencoded) {
        StringBuilder buffer = null;
        for (int i = 0; i < unencoded.length(); ++i) {
            String encS = XmlWriter.encoding.get(unencoded.charAt(i));
            if (encS != null) {
                if (buffer == null)
                    buffer = new StringBuilder(unencoded.substring(0,i));
                buffer.append(encS);
            } else if (buffer != null)
                buffer.append(unencoded.charAt(i));
        }
        return (buffer == null) ? unencoded : buffer.toString();
    }

    /**
     * The output writer to save the values to.
     */
    final private static HashMap<Character, String> encoding = new HashMap<Character, String>();
    static {
        encoding.put('<', "&lt;");
        encoding.put('>', "&gt;");
        encoding.put('"', "&quot;");
        encoding.put('\'', "&apos;");
        encoding.put('&', "&amp;");
        encoding.put('\n', "&#xA;");
        encoding.put('\r', "&#xD;");
        encoding.put('\t', "&#x9;");
    }
}
