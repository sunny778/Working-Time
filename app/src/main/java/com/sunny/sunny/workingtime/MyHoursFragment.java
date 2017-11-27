package com.sunny.sunny.workingtime;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyHoursFragment extends Fragment {

    private static final String TAG = "MyHoursFragment";

    private RecyclerView hoursList;
    private HoursAdapter adapter;
    private ArrayList<JobTimes> jobTimesArr;
    private SharedPreferences sp;
    private TextView textTotal;

    public MyHoursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_hours, container, false);

        sp = getActivity().getSharedPreferences(getString(R.string.sp_job), Context.MODE_PRIVATE);
        textTotal = root.findViewById(R.id.textTotal);
        jobTimesArr = new ArrayList<>();
        hoursList = root.findViewById(R.id.hoursList);
        hoursList.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            getAllHours();
        }catch (NullPointerException ex){
            Log.d(TAG, ex.getMessage());
        }

        adapter = new HoursAdapter(jobTimesArr, getContext(), this);
        hoursList.setAdapter(adapter);

        getTotal();
        
        return root;
    }

    protected void getTotal(){
        double total = 0;
        JobTimes jobTimes = null;
        for (int i = 0; i <jobTimesArr.size() ; i++) {
            jobTimes = jobTimesArr.get(i);
            total += jobTimes.getTotalHours() * sp.getFloat(getString(R.string.sp_per_hour), 0);
        }
        total += sp.getFloat(getString(R.string.sp_increase), 0);
        total -= sp.getFloat(getString(R.string.sp_discount), 0);

        textTotal.setText(String.format("%.2f", total) + " ש\"ח");
    }

    public void getAllHours(){
        jobTimesArr.clear();
        Cursor cursor = getActivity().getContentResolver().query(JobProvider.CONTENT_URI_HOURS, null, null, null, null);

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_ID2));
            int year = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_YEAR));
            int month = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_MONTH));
            int day = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_DAY));
            String starting = cursor.getString(cursor.getColumnIndex(JobDBHelper.COL_STARTING_TIME));
            String ending = cursor.getString(cursor.getColumnIndex(JobDBHelper.COL_ENDING_TIME));
            float total = cursor.getFloat(cursor.getColumnIndex(JobDBHelper.COL_TOTAL_HOURS));

            jobTimesArr.add(new JobTimes(id, year, month, day, starting, ending, total));
        }

        adapter.setJobTimesArr(jobTimesArr);

    }

}
