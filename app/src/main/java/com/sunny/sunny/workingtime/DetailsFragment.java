package com.sunny.sunny.workingtime;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = "DetailsFragment";

    private EditText editPerHour;
    private EditText editDiscountMonthly;
    private EditText editIncreaseMonthly;
    private EditText editName;
    private SharedPreferences sp;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        sp = getActivity().getSharedPreferences(getString(R.string.sp_job), Context.MODE_PRIVATE);

        editPerHour = root.findViewById(R.id.editPerHour);
        editDiscountMonthly = root.findViewById(R.id.editDiscountMonthly);
        editIncreaseMonthly = root.findViewById(R.id.editIncreaseMonthly);
        editName = root.findViewById(R.id.editName);

        try{
            editName.setText(sp.getString(getString(R.string.sp_job_name), ""));
            editPerHour.setText(sp.getFloat(getString(R.string.sp_per_hour), 0) + "");
            editDiscountMonthly.setText(sp.getFloat(getString(R.string.sp_discount), 0) + "");
            editIncreaseMonthly.setText(sp.getFloat(getString(R.string.sp_increase), 0) + "");
        }catch (NullPointerException ex){
            Log.d(TAG, ex.getMessage());
        }

        root.findViewById(R.id.buttonSave).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (editPerHour.getText().toString().isEmpty() || editName.getText().toString().equals("") ||
                editDiscountMonthly.getText().toString().isEmpty() || editIncreaseMonthly.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "נא להזין מידע בכל השדות", Toast.LENGTH_SHORT).show();
        } else {

            String name = editName.getText().toString();
            float perHour = Float.parseFloat(editPerHour.getText().toString());
            float discountMonthly = Float.parseFloat(editDiscountMonthly.getText().toString());
            float increaseMonthly = Float.parseFloat(editIncreaseMonthly.getText().toString());

            sp.edit()
                    .putString(getString(R.string.sp_job_name), name)
                    .putFloat(getString(R.string.sp_per_hour), perHour)
                    .putFloat(getString(R.string.sp_discount), discountMonthly)
                    .putFloat(getString(R.string.sp_increase), increaseMonthly)
                    .apply();

            Toast.makeText(getContext(), "הפרטים נשמרו!", Toast.LENGTH_SHORT).show();
        }
    }

}
