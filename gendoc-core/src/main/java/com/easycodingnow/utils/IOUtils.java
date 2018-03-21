package com.easycodingnow.utils;

import java.io.*;
import java.nio.channels.Channel;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class IOUtils {

    private static final int DEFAULT_BUFFER_SIZE = 16384;

    private IOUtils() {
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        copy((InputStream)input, (OutputStream)output, 16384);
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        boolean var4 = false;

        int n;
        while(0 <= (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }

    }

    public static void copy(Reader input, Writer output) throws IOException {
        copy((Reader)input, (Writer)output, 16384);
    }

    public static void copy(Reader input, Writer output, int bufferSize) throws IOException {
        char[] buffer = new char[bufferSize];
        boolean var4 = false;

        int n;
        while(0 <= (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }

        output.flush();
    }

    public static void copy(InputStream input, Writer output) throws IOException {
        copy((InputStream)input, (Writer)output, 16384);
    }

    public static void copy(InputStream input, Writer output, int bufferSize) throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copy((Reader)in, (Writer)output, bufferSize);
    }

    public static void copy(InputStream input, Writer output, String encoding) throws IOException {
        InputStreamReader in = new InputStreamReader(input, encoding);
        copy((Reader)in, (Writer)output);
    }

    public static void copy(InputStream input, Writer output, String encoding, int bufferSize) throws IOException {
        InputStreamReader in = new InputStreamReader(input, encoding);
        copy((Reader)in, (Writer)output, bufferSize);
    }

    public static String toString(InputStream input) throws IOException {
        return toString((InputStream)input, 16384);
    }

    public static String toString(InputStream input, int bufferSize) throws IOException {
        StringWriter sw = new StringWriter();
        copy((InputStream)input, (Writer)sw, bufferSize);
        return sw.toString();
    }

    public static String toString(InputStream input, String encoding) throws IOException {
        return toString((InputStream)input, encoding, 16384);
    }

    public static String toString(InputStream input, String encoding, int bufferSize) throws IOException {
        StringWriter sw = new StringWriter();
        copy((InputStream)input, sw, encoding, bufferSize);
        return sw.toString();
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        return toByteArray((InputStream)input, 16384);
    }

    public static byte[] toByteArray(InputStream input, int bufferSize) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy((InputStream)input, (OutputStream)output, bufferSize);
        return output.toByteArray();
    }

    public static void copy(Reader input, OutputStream output) throws IOException {
        copy((Reader)input, (OutputStream)output, 16384);
    }

    public static void copy(Reader input, OutputStream output, int bufferSize) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output);
        copy((Reader)input, (Writer)out, bufferSize);
        out.flush();
    }

    public static String toString(Reader input) throws IOException {
        return toString((Reader)input, 16384);
    }

    public static String toString(Reader input, int bufferSize) throws IOException {
        StringWriter sw = new StringWriter();
        copy((Reader)input, (Writer)sw, bufferSize);
        return sw.toString();
    }

    public static byte[] toByteArray(Reader input) throws IOException {
        return toByteArray((Reader)input, 16384);
    }

    public static byte[] toByteArray(Reader input, int bufferSize) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy((Reader)input, (OutputStream)output, bufferSize);
        return output.toByteArray();
    }

    public static void copy(String input, OutputStream output) throws IOException {
        copy((String)input, (OutputStream)output, 16384);
    }

    public static void copy(String input, OutputStream output, int bufferSize) throws IOException {
        StringReader in = new StringReader(input);
        OutputStreamWriter out = new OutputStreamWriter(output);
        copy((Reader)in, (Writer)out, bufferSize);
        out.flush();
    }

    public static void copy(String input, Writer output) throws IOException {
        output.write(input);
    }

    /** @deprecated */
    public static void bufferedCopy(InputStream input, OutputStream output) throws IOException {
        BufferedInputStream in = new BufferedInputStream(input);
        BufferedOutputStream out = new BufferedOutputStream(output);
        copy((InputStream)in, (OutputStream)out);
        out.flush();
    }

    public static byte[] toByteArray(String input) throws IOException {
        return toByteArray((String)input, 16384);
    }

    public static byte[] toByteArray(String input, int bufferSize) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy((String)input, (OutputStream)output, bufferSize);
        return output.toByteArray();
    }

    public static void copy(byte[] input, Writer output) throws IOException {
        copy((byte[])input, (Writer)output, 16384);
    }

    public static void copy(byte[] input, Writer output, int bufferSize) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy((InputStream)in, (Writer)output, bufferSize);
    }

    public static void copy(byte[] input, Writer output, String encoding) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy((InputStream)in, output, encoding);
    }

    public static void copy(byte[] input, Writer output, String encoding, int bufferSize) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy((InputStream)in, output, encoding, bufferSize);
    }

    public static String toString(byte[] input) throws IOException {
        return toString((byte[])input, 16384);
    }

    public static String toString(byte[] input, int bufferSize) throws IOException {
        StringWriter sw = new StringWriter();
        copy((byte[])input, (Writer)sw, bufferSize);
        return sw.toString();
    }

    public static String toString(byte[] input, String encoding) throws IOException {
        return toString((byte[])input, encoding, 16384);
    }

    public static String toString(byte[] input, String encoding, int bufferSize) throws IOException {
        StringWriter sw = new StringWriter();
        copy((byte[])input, sw, encoding, bufferSize);
        return sw.toString();
    }

    public static void copy(byte[] input, OutputStream output) throws IOException {
        copy((byte[])input, (OutputStream)output, 16384);
    }

    public static void copy(byte[] input, OutputStream output, int bufferSize) throws IOException {
        output.write(input);
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        InputStream bufferedInput1 = new BufferedInputStream(input1);
        InputStream bufferedInput2 = new BufferedInputStream(input2);

        int ch2;
        for(int ch = bufferedInput1.read(); 0 <= ch; ch = bufferedInput1.read()) {
            ch2 = bufferedInput2.read();
            if(ch != ch2) {
                return false;
            }
        }

        ch2 = bufferedInput2.read();
        if(0 <= ch2) {
            return false;
        } else {
            return true;
        }
    }

    public static void close(InputStream inputStream) {
        if(inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var2) {
                ;
            }

        }
    }

    public static void close(Channel channel) {
        if(channel != null) {
            try {
                channel.close();
            } catch (IOException var2) {
                ;
            }

        }
    }

    public static void close(OutputStream outputStream) {
        if(outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException var2) {
                ;
            }

        }
    }

    public static void close(Reader reader) {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException var2) {
                ;
            }

        }
    }

    public static void close(Writer writer) {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException var2) {
                ;
            }

        }
    }
}
