package com.cio.gidservice.gidservice.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicIDSeparator {
    private String url;
    private String pattern;

    public PublicIDSeparator(String url, String pattern) {
        this.url = url;
        this.pattern = pattern;
    }

    public String  separate() {
        ArrayList<String> array = new ArrayList<>(Arrays.asList(url.split("/")));
        return array.stream().filter(s -> {
            Pattern pattern = Pattern.compile(this.pattern);
            Matcher matcher = pattern.matcher(s);
            return matcher.find();
        }).findFirst().get();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
