package com.example.mingchao_boot.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Match {

    private static final String BASE64_PATTERN =
            "(?i)[A-Za-z0-9+/]{4}\\([A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4}";
    private static final String BASE64_IMAGE_PATTERN =
            "data:image/([^;]+);base64,[^\"]*";
    private static final String IMAGE_PATTERN =
            "(http|https|blob:http)://[\\w\\d./?:&=#+-.]+\\.(jpg|png|gif|jpeg|)([^\"]*)";
   // private static final String P = "<p[^>]*>[^<]*</p>";
   // private static final String P = "<p[^>]*>.*(?<!</p>).*</p>";
    private static final String P = "<p[^>]*>((?!(?:<img|</p>|&nbsp)).)*</p>";
    private static final String IMG = "<img\\s+[^>]*>";

    public static List<String> match(String base){
        return getStrings(base, BASE64_IMAGE_PATTERN);
    }

    public static List<String> matchUrl(String base){
        return getStrings(base, IMAGE_PATTERN);
    }
    public static List<String> getIMG(String base){
        return getStrings(base, IMG);
    }

    @NotNull
    private static List<String> getStrings(String base, String base64ImagePattern) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(base64ImagePattern);
        Matcher matcher = pattern.matcher(base);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }


    public static String MatchFirstP(String base){
        Pattern pattern = Pattern.compile(P);
        Matcher matcher = pattern.matcher(base);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }
}
