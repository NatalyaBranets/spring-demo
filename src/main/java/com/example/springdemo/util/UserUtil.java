package com.example.springdemo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtil {

    private UserUtil() {}
    public static boolean validateUserAge(Date userBirthDate, int allowedAge) {
        long userAge = calculateAge(userBirthDate);
        return userAge >= allowedAge;
    }

    public static int calculateAge(Date birthDate) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(new Date()));
        return (d2 - d1) / 10000;
    }
}
