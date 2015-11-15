package com.gandalf.tvprogramv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        ListView favoritesListView = (ListView) v.findViewById(R.id.favorites_listView);
        favoritesListView.setAdapter(new FavoritesAdapter(TvGuideLab.instance().getFavorites()));
        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getActivity(), TimetableActivity.class);
                i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_NAME, TvGuideLab.instance().getFavoritesItem(position).getName());
                i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_URL, TvGuideLab.instance().getFavoritesItem(position).getUrl());
                startActivity(i);
            }
        });
        favoritesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TvGuideLab.instance().removeFavoritesItem(position);
                try {
                    JSONBuilder.saveFavorites();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                ((FavoritesAdapter) parent.getAdapter()).notifyDataSetChanged();
                Toast.makeText(getActivity(), "Program removed from favorites", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return v;
    }

    class FavoritesAdapter extends ArrayAdapter<TvProgram> {
        private ArrayList<TvProgram> mFavorites;

        public FavoritesAdapter(ArrayList<TvProgram> objects) {
            super(getActivity(), 0, objects);
            mFavorites = objects;
        }

        @Override
        public int getCount() {
            return mFavorites.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_of_programs_list_item, null);
            }

            TvProgram tvProgram = getItem(position);

            TextView programName = (TextView) convertView.findViewById(R.id.program_name);
            programName.setText(tvProgram.getName());

            ImageView logo = (ImageView) convertView.findViewById(R.id.logo_imageView);
            String nameProgram = tvProgram.getName().toLowerCase().replace(' ', '_');
            int img = getActivity().getResources().getIdentifier(nameProgram, "drawable", getActivity().getPackageName());
            logo.setImageResource(img);

            return convertView;
        }
    }
}
