package com.gandalf.tvprogramv2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TimetableFragment extends Fragment {
    public static final String EXTRA_TV_PROGRAM_URL = "com.gandalf.tvprogram.timetablefragment.extra_tv_program_url";
    public static final String EXTRA_TV_PROGRAM_NAME = "com.gandalf.tvprogram.timetablefragment.extra_tv_program_name";
    private static final String TAG = "TimetableFragment";

    private ArrayList<Timetable> mTvSatnica;
    private TimetableAdapter mTimetableAdapter;
    private Handler mDataChangedHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tvUrl = getArguments().getString(EXTRA_TV_PROGRAM_URL);
        String tvName = getArguments().getString(EXTRA_TV_PROGRAM_NAME);

        (new DownloadTvGuide()).execute(tvUrl);
        mTvSatnica = new ArrayList<>();

        mDataChangedHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mTimetableAdapter.notifyDataSetChanged();
                return true;
            }
        });

        getActivity().setTitle(tvName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timetable, container, false);

        ListView timetableListView = (ListView) v.findViewById(R.id.timetable_listView);
        mTimetableAdapter = new TimetableAdapter(mTvSatnica);
        if(mTvSatnica != null)
            timetableListView.setAdapter(mTimetableAdapter);

        return v;
    }



    public static Fragment newInstance(String url, String name) {
        Bundle args = new Bundle();
        args.putString(TimetableFragment.EXTRA_TV_PROGRAM_URL, url);
        args.putString(TimetableFragment.EXTRA_TV_PROGRAM_NAME, name);
        TimetableFragment fragment = new TimetableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    class TimetableAdapter extends ArrayAdapter<Timetable> {

        public TimetableAdapter(ArrayList<Timetable> objects) {
            super(getActivity(), 0, objects);
        }

        @Override
        public int getCount() {
            return mTvSatnica.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.timetable_list_item, null);
            }

            Timetable timetable = getItem(position);

            TextView timeTimetable = (TextView) convertView.findViewById(R.id.time_timetable);
            timeTimetable.setText(timetable.getTime());

            TextView tvShowTimetable = (TextView) convertView.findViewById(R.id.tvshow_timetable);
            tvShowTimetable.setText(timetable.getTvShow());

            return convertView;
        }
    }

    class DownloadTvGuide extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            setProgramTime(params[0]);
            mDataChangedHandler.sendEmptyMessage(0);
            return 1;
        }

        private void setProgramTime(String tvURL) {
            Log.d(TAG, tvURL);
            try {
                Document doc = Jsoup.connect(tvURL).get();
                Element table = doc.getElementsByClass("table-striped").first();
                Elements td = table.getElementsByTag("td");
                Iterator<Element> i = td.iterator();
                Element v;
                Element e;
                for(;;) {
                    if(i.hasNext()) {
                        v = i.next();
                    }
                    else
                        break;

                    if(i.hasNext()) {
                        e = i.next();
                        mTvSatnica.add(new Timetable(v.text(), e.text()));
                    }
                    else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTvSatnica.clear();
            }
        }
    }
}
