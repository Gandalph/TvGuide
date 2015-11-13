package com.gandalf.tvprogramv2;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSONBuilder {
    private static final String mProgramNameTag = "programName";
    private static final String mUrlTag = "url";
    private static final String TAG = "JSONBuilder";

    public static ArrayList<TvProgram> loadJSON() throws IOException, JSONException {
        ArrayList<TvProgram> favorites = new ArrayList<>();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalDirectory = Environment.getExternalStorageDirectory();
            String folderName = "com.gandalf.tvprogramv2.TvGuide";
            String filePath = externalDirectory.getAbsolutePath() + File.separator + folderName + File.separator + "favorites.json";
            Log.d(TAG, filePath);
            File favoritesFile = new File(filePath);

            BufferedReader br = new BufferedReader(new FileReader(favoritesFile));
            String line;
            StringBuffer sb = new StringBuffer();
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            String prefix = "http://tv.aladin.info/tv-program-";
            JSONArray array = new JSONArray(sb.toString());
            for(int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);
                TvProgram tvProgram = new TvProgram(jo.getString(mProgramNameTag), prefix + jo.getString(mUrlTag));
                favorites.add(tvProgram);
            }


            br.close();
        }

        return favorites;
    }
}
