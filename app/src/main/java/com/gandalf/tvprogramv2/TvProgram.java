package com.gandalf.tvprogramv2;

public class TvProgram {
    private String mName;
    private String mUrl;

    public TvProgram(String name, String url) {
        mName = name;
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }
}
