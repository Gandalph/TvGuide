package com.gandalf.tvprogramv2;

import java.util.ArrayList;
import java.util.Arrays;

public class TvGuideLab {
    private ArrayList<TvProgram> mTvPrograms;
    private ArrayList<TvProgram> mFavorites;
    private static TvGuideLab sTvProgramLab;
    String mFilename = "favorites.json";

    private TvGuideLab() {
        loadTvPrograms();
        loadFavoritesPrograms();
    }

    private void loadFavoritesPrograms() {
        
    }

    private void loadTvPrograms() {
        String prefix = "http://tv.aladin.info/tv-program-";
        mTvPrograms = new ArrayList<>(Arrays.asList(
                new TvProgram("Tv24Kitchen", prefix+"24kitchen"),
                new TvProgram("Animal Planet", prefix+"animal-planet"),
                new TvProgram("Arena Sport 1", prefix+"arena-sport-1"),
                new TvProgram("Arena Sport 2", prefix+"arena-sport-2"),
                new TvProgram("Arena Sport 3", prefix+"arena-sport-3"),
                new TvProgram("Arena Sport 4", prefix+"arena-sport-4"),
                new TvProgram("AXN", prefix+"axn"),
                new TvProgram("B92", prefix+"b92"),
                new TvProgram("B92 Info", prefix+"b92-info"),
                new TvProgram("BBC", prefix+"bbc"),
                new TvProgram("BN Televizija", prefix+"bn-televizija"),
                new TvProgram("Cinemania", prefix+"cinemania"),
                new TvProgram("Cinemax", prefix+"cinemax"),
                new TvProgram("CNN", prefix+"cnn"),
                new TvProgram("Cinestar TV", prefix+"cinestar-tv"),
                new TvProgram("Crime and Investigation", prefix+"crime-and-investigation"),
                new TvProgram("Discovery Channel", prefix+"discovery-channel"),
                new TvProgram("RTS 1", prefix+"rts-1"),
                new TvProgram("Fox", prefix+"fox-tv"),
                new TvProgram("Fox Life", prefix+"fox-life")
        ));
    }

    public ArrayList<TvProgram> getTvPrograms() {
        return mTvPrograms;
    }

    public static TvGuideLab instance() {
        if(sTvProgramLab == null) {
            sTvProgramLab = new TvGuideLab();
        }
        return sTvProgramLab;
    }
}
