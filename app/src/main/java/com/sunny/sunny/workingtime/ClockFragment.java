package com.sunny.sunny.workingtime;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */

public class ClockFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ClockFragment";

    private Button buttonStart;
    private Button buttonFinish;
    private Button buttonCancel;
    private TextView textShowTime;
    private TextView textDate;

    private int countSeconds;
    private int countMinutes;
    private int countHours;

    private String stringSeconds;
    private String stringMinutes;
    private String stringHours;
    private Timer timer;

    protected String startingTime = "";

    private ArrayList<JobTimes> jobTimesArr;

    private UpdateListListener updateListListener;

    public ClockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_clock, container, false);

        textShowTime = root.findViewById(R.id.textShowTime);
        textDate = root.findViewById(R.id.textDate);
        buttonStart = root.findViewById(R.id.buttonStart);
        buttonFinish = root.findViewById(R.id.buttonFinish);
        buttonCancel = root.findViewById(R.id.buttonCancel);
        jobTimesArr = new ArrayList<>();

        buttonStart.setOnClickListener(this);
        buttonFinish.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return root;
    }

    interface UpdateListListener{
        void updateList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateListListener = (UpdateListListener) getActivity();
        }catch (ClassCastException ex){
            Log.d("", ex.getMessage());
        }
    }

    @Override
    public void onClick(View view) {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month  = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar;
        Date date;

        countSeconds = 0;
        countMinutes = 0;
        countHours = 0;
        stringSeconds = "00";
        stringMinutes = "00";
        stringHours = "00";

        switch (view.getId()){
            case R.id.buttonStart:

                buttonStart.setVisibility(View.INVISIBLE);
                buttonFinish.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);

                calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
                date = calendar.getTime();
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

                startingTime = dateFormat.format(date);

                textDate.setText(" תאריך התחלה: " + day + "/" + month + "/" + year + "\n שעת התחלה: " +  startingTime);


                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {


                    @Override
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countSeconds++;
                                textShowTime.setText(stringHours + ":" + stringMinutes + ":" + stringSeconds);


                                if (countSeconds == 60){
                                    countMinutes++;
                                    countSeconds = 0;
                                }

                                if (countMinutes == 60){
                                    countHours++;
                                    countMinutes = 0;
                                }

                                if (countHours == 24) {
                                    timer.cancel();
                                    Toast.makeText(getContext(), "Sorry you can't work a full day!!",
                                            Toast.LENGTH_SHORT).show();
                                }

                                if (countSeconds < 10){
                                    stringSeconds = "0" + countSeconds;
                                }else {
                                    stringSeconds = countSeconds + "";
                                }if (countMinutes < 10){
                                    stringMinutes = "0" + countMinutes;
                                }else {
                                    stringMinutes = countMinutes + "";
                                }if (countHours < 10){
                                    stringHours = "0" + countHours;
                                }else {
                                    stringHours = countHours + "";
                                }
                            }
                        });

                    }
                }, 0, 1000);
                break;

            case R.id.buttonFinish:

                calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
                date = calendar.getTime();
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

                String endingTime = dateFormat.format(date);

                textDate.setText(" תאריך סיום: " + day + "/" + month + "/" + year + "\n שעת סיום: " +  endingTime);

                float totalHours = calcTotalHours(startingTime, endingTime);

                JobTimes jobTimes = new JobTimes(year, month, day, startingTime, endingTime, totalHours);

                jobTimesArr.add(jobTimes);
                insertIntoDB(jobTimes);

                buttonStart.setVisibility(View.VISIBLE);
                buttonFinish.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
                timer.cancel();

                updateListListener.updateList();

                break;

            case R.id.buttonCancel:
                textDate.setText("");

                buttonStart.setVisibility(View.VISIBLE);
                buttonFinish.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
                timer.cancel();

                break;
        }
    }

    protected float calcTotalHours(String startingTime, String endingTime){
        float totalHours = 0;

        float starting = Float.parseFloat(startingTime.substring(0, 2));
        starting += (Float.parseFloat(startingTime.substring(3, 5))) / 60;
        float ending = Float.parseFloat(endingTime.substring(0, 2));
        ending += (Float.parseFloat(endingTime.substring(3, 5))) / 60;

        totalHours = ending - starting;
        return totalHours;
    }


    public void insertIntoDB(JobTimes jobTimes){
        ContentValues values = new ContentValues();

        values.put(JobDBHelper.COL_YEAR, jobTimes.getYear());
        values.put(JobDBHelper.COL_MONTH, jobTimes.getMonth());
        values.put(JobDBHelper.COL_DAY, jobTimes.getDay());
        values.put(JobDBHelper.COL_STARTING_TIME, jobTimes.getStartingTime());
        values.put(JobDBHelper.COL_ENDING_TIME, jobTimes.getEndingTime());
        values.put(JobDBHelper.COL_TOTAL_HOURS, jobTimes.getTotalHours());

        getActivity().getContentResolver().insert(JobProvider.CONTENT_URI_HOURS, values);
    }

//    public void getAllHours(){
//        jobTimesArr.clear();
//        Cursor cursor = getActivity().getContentResolver().query(JobProvider.CONTENT_URI_HOURS, null, null, null, null);
//
//        while (cursor.moveToNext()){
//
//            int id = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_ID2));
//            int year = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_YEAR));
//            int month = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_MONTH));
//            int day = cursor.getInt(cursor.getColumnIndex(JobDBHelper.COL_DAY));
//            String starting = cursor.getString(cursor.getColumnIndex(JobDBHelper.COL_STARTING_TIME));
//            String ending = cursor.getString(cursor.getColumnIndex(JobDBHelper.COL_ENDING_TIME));
//            float total = cursor.getFloat(cursor.getColumnIndex(JobDBHelper.COL_TOTAL_HOURS));
//
//            jobTimesArr.add(new JobTimes(id, year, month, day, starting, ending, total));
//        }
//
//        adapter.setJobTimesArr(jobTimesArr);
//
//    }

}
