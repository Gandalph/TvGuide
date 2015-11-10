package com.gandalf.tvprogramv2;

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

import java.util.ArrayList;

public class ProgramsTabFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_launcher, container, false);

        ListView mProgramsListView = (ListView) v.findViewById(R.id.programs_listView);
        ArrayList<TvProgram> mPrograms = new ArrayList<>(TvGuideLab.instance().getTvPrograms());
        mProgramsListView.setAdapter(new ProgramsAdapter(mPrograms));
        mProgramsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), TimetableActivity.class);
                i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_NAME, TvGuideLab.instance().getTvPrograms().get(position).getName());
                i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_URL, TvGuideLab.instance().getTvPrograms().get(position).getUrl());
                startActivity(i);
            }
        });

        return v;
    }

    private class ProgramsAdapter extends ArrayAdapter<TvProgram> {
        private ArrayList<TvProgram> mTvProgramsOriginal;

        public ProgramsAdapter(ArrayList<TvProgram> tvPrograms) {
            super(getActivity(), 0, tvPrograms);
            mTvProgramsOriginal = new ArrayList<>(tvPrograms);
        }

        @Override
        public int getCount() {
            return mTvProgramsOriginal.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_of_programs_list_item, null);
            }

            TvProgram tvProgram = getItem(position);

            TextView mProgramName = (TextView) convertView.findViewById(R.id.program_name);
            mProgramName.setText(tvProgram.getName());

            ImageView mLogoImageView = (ImageView) convertView.findViewById(R.id.logo_imageView);
            String nameProgram = tvProgram.getName().toLowerCase().replace(' ', '_');
            int img = getActivity().getResources().getIdentifier(nameProgram, "drawable", getActivity().getPackageName());
            mLogoImageView.setImageResource(img);

            return convertView;
        }

    }
}
