package com.sunny.sunny.workingtime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Sunny on 16/11/2017.
 */

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HourHolder> {

    private ArrayList<JobTimes> jobTimesArr;
    private Context context;
    private SharedPreferences sp;
    private MyHoursFragment myHoursFragment;

    public HoursAdapter(ArrayList<JobTimes> jobTimesArr, Context context) {
        this.jobTimesArr = jobTimesArr;
        this.context = context;
    }

    public HoursAdapter(ArrayList<JobTimes> jobTimesArr, Context context, MyHoursFragment myHoursFragment) {
        this.jobTimesArr = jobTimesArr;
        this.context = context;
        this.myHoursFragment = myHoursFragment;
    }

    public void setJobTimesArr(ArrayList<JobTimes> jobTimesArr) {
        this.jobTimesArr = jobTimesArr;
        notifyDataSetChanged();
    }

    @Override
    public HourHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.hour_style, parent, false);

        sp = context.getSharedPreferences(context.getString(R.string.sp_job), Context.MODE_PRIVATE);

        return new HourHolder(view);
    }

    @Override
    public void onBindViewHolder(HourHolder holder, int position) {
        holder.bind(jobTimesArr.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (jobTimesArr != null){
            return jobTimesArr.size();
        }
        return 0;
    }

    public class HourHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, DialogInterface.OnClickListener {

        private TextView textDate;
        private TextView textStart;
        private TextView textEnd;
        private TextView textHours;
        private TextView textSalary;
        private JobTimes jobTimes;
        private int position;

        public HourHolder(View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDate);
            textStart = itemView.findViewById(R.id.textStart);
            textEnd = itemView.findViewById(R.id.textEnd);
            textHours = itemView.findViewById(R.id.textHours);
            textSalary = itemView.findViewById(R.id.textSalary);

            itemView.setOnLongClickListener(this);
        }

        public void bind(JobTimes jobTimes, int position) {
            this.position = position;
            this.jobTimes = jobTimes;

            textDate.setText(jobTimes.getDay() + "." + jobTimes.getMonth() + "." + jobTimes.getYear() + "");
            textStart.setText(jobTimes.getStartingTime());
            textEnd.setText(jobTimes.getEndingTime());
            textHours.setText(String.format("%.2f", jobTimes.getTotalHours()));
            textSalary.setText(String.format("%.2f", (jobTimes.getTotalHours() *
                    sp.getFloat(context.getString(R.string.sp_per_hour), 0))));
        }

        @Override
        public boolean onLongClick(View view) {

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("מחיקה")
                    .setMessage("האם אתה בטוח שאתה רוצה למחוק את יום העבודה הזה?")
                    .setPositiveButton("כן", this)
                    .setNegativeButton("לא", this)
                    .setNegativeButton("ביטול", this)
                    .create();
            dialog.show();

            return true;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which){

                case DialogInterface.BUTTON_POSITIVE:
                    context.getContentResolver().delete(JobProvider.CONTENT_URI_HOURS, JobDBHelper.COL_ID2 + "=" + jobTimes.getId(), null);
                    jobTimesArr.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, jobTimesArr.size());
                    myHoursFragment.getTotal();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    break;

                case DialogInterface.BUTTON_NEUTRAL:

                    break;
            }
        }
    }
}
