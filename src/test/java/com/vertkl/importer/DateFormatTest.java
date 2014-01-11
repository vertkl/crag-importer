package com.vertkl.importer;

import org.joda.time.DateTime;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTest {

    @Test
    public void print_full_date() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSSZZ");
        String s = df.format(new Date());
        System.out.println("With standard date: " + s);
    }


    @Test
    public void print_with_joda_time() {
        DateTime dt = new DateTime();
        System.out.println("With Joda Time: " + dt.toString("yyyy-MM-DD'T'HH:mm:ss.SSSZZ"));
    }
}