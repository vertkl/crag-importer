package com.vertkl.importer;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTest {

    @Test
    public void print_full_date() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZ");
        for (int i = 0; i< 100000; ++i){
            String s = df.format(new Date());
            System.out.println(s);
        }

    }
}