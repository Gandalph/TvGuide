package com.gandalf.tvprogramv2;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONBuilder {
    private static final String mProgramNameTag = "programName";
    private static final String mUrlTag = "url";
    private static final String TAG = "JSONBuilder";

    private static final File externalDirectory = Environment.getExternalStorageDirectory();
    private static final String folderName = "com.gandalf.tvprogramv2.TvGuide";
    private static final String filePath = externalDirectory.getAbsolutePath()
                                            + File.separator
                                            + folderName
                                            + File.separator
                                            + "favorites.json";

    public static ArrayList<TvProgram> loadJSON() throws IOException, JSONException {
        ArrayList<TvProgram> favorites = new ArrayList<>();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, filePath);
            File favoritesFile = new File(filePath);
            if (favoritesFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(favoritesFile));
                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String prefix = "http://tv.aladin.info/tv-program-";
                JSONArray array = new JSONArray(sb.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jo = array.getJSONObject(i);
                    TvProgram tvProgram = new TvProgram(jo.getString(mProgramNameTag), jo.getString(mUrlTag));
                    favorites.add(tvProgram);
                }
                br.close();
            }
        }

        return favorites;
    }

    public static void saveFavorites() throws IOException, JSONException {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File favoritesFile = new File(filePath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(favoritesFile));

            JSONArray array = new JSONArray();
            ArrayList<TvProgram> favorites = TvGuideLab.instance().getFavorites();
            for(int i = 0; i < favorites.size(); i++) {
                JSONObject object = new JSONObject();
                object.put(mProgramNameTag, favorites.get(i).getName());
                object.put(mUrlTag, favorites.get(i).getUrl());
                array.put(i, object);
            }

            bw.write(array.toString());

            bw.close();
        }
    }
}
