package com.gandalf.tvprogramv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchProgramFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "SearchProgramFragment";

    private ProgramsAdapter mProgramsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_programs, container, false);

        ListView listOfPrograms = (ListView) v.findViewById(R.id.list_of_programs_listView);
        listOfPrograms.setOnItemClickListener(this);
        ArrayList<TvProgram> programs = new ArrayList<>(TvGuideLab.instance().getTvPrograms());
        mProgramsAdapter = new ProgramsAdapter(programs);
        listOfPrograms.setAdapter(mProgramsAdapter);

        EditText mTvSearchToolbar = (EditText) getActivity().findViewById(R.id.tv_search_toolbar);
        mTvSearchToolbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProgramsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout linearLayoutParent = (LinearLayout) view;
        TextView tvName = (TextView) linearLayoutParent.getChildAt(0);
        Intent i = new Intent(getActivity(), TimetableActivity.class);
        i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_URL, TvGuideLab.instance().getTvPrograms().get(position).getUrl());
        i.putExtra(TimetableFragment.EXTRA_TV_PROGRAM_NAME, TvGuideLab.instance().getTvPrograms().get(position).getName());
        startActivity(i);
    }


    private class ProgramsAdapter extends ArrayAdapter<TvProgram> {
        private ArrayList<TvProgram> mTvProgramsOriginal;
        private ArrayList<TvProgram> mTvProgramsFiltered;
        private ProgramsFilter mProgramsFilter;

        public ProgramsAdapter(ArrayList<TvProgram> tvPrograms) {
            super(getActivity(), 0, tvPrograms);
            mTvProgramsOriginal = new ArrayList<>(tvPrograms);
            mTvProgramsFiltered = new ArrayList<>(tvPrograms);
        }

        @Override
        public int getCount() {
            return mTvProgramsFiltered.size();
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

        @Override
        public Filter getFilter() {
            if(mProgramsFilter == null) {
                mProgramsFilter = new ProgramsFilter();
            }

            return mProgramsFilter;
        }

        private class ProgramsFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null || constraint.length() == 0) {
                    filterResults.values = mTvProgramsOriginal;
                    filterResults.count = mTvProgramsOriginal.size();
                }
                else {
                    ArrayList<TvProgram> newArray = new ArrayList<>();
                    for(TvProgram t : mTvProgramsOriginal) {
                        if(t.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            newArray.add(t);
                        }
                    }

                    filterResults.values = newArray;
                    filterResults.count = newArray.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mTvProgramsFiltered = (ArrayList<TvProgram>) results.values;
                notifyDataSetChanged();
                clear();
                for(int i = 0; i < mTvProgramsFiltered.size(); i++) {
                    add(mTvProgramsFiltered.get(i));
                }
                notifyDataSetInvalidated();
            }
        }

    }
}
