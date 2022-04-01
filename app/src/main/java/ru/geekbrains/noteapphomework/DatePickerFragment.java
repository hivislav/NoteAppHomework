package ru.geekbrains.noteapphomework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.noteapphomework.data.DatePickerListener;


public class DatePickerFragment extends DialogFragment {

    public static final String DATE_PICKER = "DATE_PICKER";
    DatePickerListener datePickerListener;
    private TextView buttonOk;
    private TextView buttonCancel;
    private DatePicker datePicker;
    private String date;
    private String day;
    private String month;
    private int year;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View datePickerFragment = inflater.inflate(R.layout.date_picker, null);
        return datePickerFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        datePicker = view.findViewById(R.id.datePicker);
        buttonOk = view.findViewById(R.id.date_picker_button_ok);
        buttonCancel = view.findViewById(R.id.date_picker_button_cancel);

        datePickerListener = (DatePickerListener)requireActivity();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = (datePicker.getDayOfMonth() > 9 ? datePicker.getDayOfMonth() : "0" + datePicker.getDayOfMonth()).toString();
                month = (datePicker.getMonth() > 8 ? datePicker.getMonth() + 1 : "0" + (datePicker.getMonth() + 1)).toString();
                year = datePicker.getYear();
                date = (day + "." + month + "." + year);
                datePickerListener.sendDatePicker(date);
                dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
