package com.djmedia.maifang51.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.djmedia.maifang51.R;
import com.djmedia.maifang51.tools.Constants;

import java.util.Calendar;

/**
 * Created by rd on 2014/3/30.
 */
public class CreateReminderFragment extends Fragment {
    private static final String TAG = CreateReminderFragment.class.getSimpleName();
    private EditText titleEditText;
    private Button confirmReminderButton;
    private Button pickDateButton;
    private Button pickTimeButton;
    private String infoId = null;

    public CreateReminderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reminder, container, false);
        titleEditText = (EditText) view.findViewById(R.id.id_create_reminder_title);

        pickDateButton = (Button) view.findViewById(R.id.id_pick_date_button);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment(pickDateButton);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        pickTimeButton = (Button) view.findViewById(R.id.id_pick_time_button);
        pickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment(pickTimeButton);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        confirmReminderButton = (Button) view.findViewById(R.id.id_confirm_reminder_button);
        confirmReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoId == null) {
                    Toast.makeText(getActivity(), "提醒创建成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "提醒修改成功", Toast.LENGTH_LONG).show();
                }
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoId = getArguments().getString(Constants.INFO_ID, null);
        if (infoId != null) {
            confirmReminderButton.setText(getString(R.string.update_reminder));
            titleEditText.setText(infoId);
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        private Button button;

        public TimePickerFragment(Button button) {
            this.button = button;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            button.setText(hourOfDay + ":" + minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private Button button;

        public DatePickerFragment(Button button) {
            this.button = button;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            button.setText(year + "-" + month + "-" + day);
        }
    }
}
