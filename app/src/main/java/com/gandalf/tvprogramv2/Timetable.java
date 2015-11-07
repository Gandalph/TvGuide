package com.gandalf.tvprogramv2;

public class Timetable {
    private String mTime;
    private String mTvShow;

    public Timetable(String time, String tvShow) {
        mTime = time;
        mTvShow = tvShow;
    }

    public String getTime() {
        return mTime;
    }

    public String getTvShow() {
        return mTvShow;
    }
}
