package com.test;

import com.example.mingchao_boot.util.Base64Match;
import com.example.mingchao_boot.util.Base64ToImage;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class TestBase64 {


    public static void main(String[] args) {

        String base = "src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhE" +
                "UgAAACgAAAAbCAYAAAAOEM1uAAAAO0lEQVRYhe3OMQEAMAzDsK78OWcQdv" +
                "hYDguBTpJMsf0deDFIGaQMUgYpg5RByiBlkDJIGaQMUgap+uAFfZ0EMlRYVeAAA" +
                "AAASUVORK5CYII=\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANS" +
                "UhEUgAAAB0AAAAmCAYAAAA4LpBhAAABM0lEQVRYhe2WMU7DMBSG/1QdskYZrPQUICquQFs" +
                "1wFEqVlJByAVQl0JzCWiX9AqodMrWSN7ijXr00MidCkqHNCYlAuFP8mI/+3t6erJtbLJMo" +
                "mYadQu1tDRCCHjeEO32OZbvy9wapRSuewXPG0IIcTwp5xyEEADA3b0PSunnvO8HYIyBEALO" +
                "eW6Podq9nHMMBjeI41g5ScdxMBo9AuPxk9xkWeWxShLZ7fVlt9eXqyQpjG08T0KEk1A563" +
                "3WH2swxmDbNizLKow1Tk7PJAA4rRZm05fSktfpDMFDUDq+07mA593CNE00d5MsTeFeXiuJ9" +
                "w8rm1yue1maKgm/S/NwSDFRNEcUzeuVVi5vXejy/ij/o7yU0q9rcMdi8VY1j4MoP23H4O99V" +
                "7RUS7VUS7VUS3+fdAvqLuxDvDha1QAAAABJRU5ErkJggg==\"></p>";
        System.out.println( Base64Match.match(base));
    }

}
