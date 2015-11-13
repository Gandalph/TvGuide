package com.gandalf.tvprogramv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class FavoritesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        ListView favoritesListView = (ListView) v.findViewById(R.id.favorites_listView);
        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getActivity(), TimetableActivity.class);
                i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_NAME, TvGuideLab.instance().getTvPrograms().get(position).getName());
                i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_URL, TvGuideLab.instance().getTvPrograms().get(position).getUrl());
                startActivity(i);
            }
        });

        return v;
    }
}
