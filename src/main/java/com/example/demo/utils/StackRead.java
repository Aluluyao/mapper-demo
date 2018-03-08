package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StackRead {

    public static String read(Exception e){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(byteArrayOutputStream));
        return new String(byteArrayOutputStream.toByteArray());
    }
}
