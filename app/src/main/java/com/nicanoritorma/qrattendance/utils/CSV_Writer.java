package com.nicanoritorma.qrattendance.utils;
/**
 * Created by Nicanor Itorma
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class CSV_Writer {

    private PrintWriter pw;
    private char separator;
    private char escape_char;
    private String lineEnd;
    private char quote_char;

    public static final char DEFAULT_SEPARATOR = ',';
    public static final char NO_QUOTE_CHARACTER = '\u0000';
    public static final char NO_ESCAPE_CHARACTER = '\u0000';
    public static final String DEFAULT_LINE_END = "\n";
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_ESCAPE_CHARACTER = '"';

    public CSV_Writer(Writer writer) {
        this(writer, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
                DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
    }

    public CSV_Writer(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
        this.pw = new PrintWriter(writer);
        this.separator = separator;
        this.quote_char = quotechar;
        this.escape_char = escapechar;
        this.lineEnd = lineEnd;
    }

    public void writeNext(String[] nextLine) {

        if (nextLine == null)
            return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nextLine.length; i++) {

            if (i != 0) {
                sb.append(separator);
            }

            String nextElement = nextLine[i];
            if (nextElement == null)
                continue;
            if (quote_char != NO_QUOTE_CHARACTER)
                sb.append(quote_char);
            for (int j = 0; j < nextElement.length(); j++) {
                char nextChar = nextElement.charAt(j);
                if (escape_char != NO_ESCAPE_CHARACTER && nextChar == quote_char) {
                    sb.append(escape_char).append(nextChar);
                } else if (escape_char != NO_ESCAPE_CHARACTER && nextChar == escape_char) {
                    sb.append(escape_char).append(nextChar);
                } else {
                    sb.append(nextChar);
                }
            }
            if (quote_char != NO_QUOTE_CHARACTER)
                sb.append(quote_char);
        }

        sb.append(lineEnd);
        pw.write(sb.toString());

    }

    public void close() throws IOException {
        pw.flush();
        pw.close();
    }
}