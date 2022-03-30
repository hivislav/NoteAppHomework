package ru.geekbrains.noteapphomework;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import ru.geekbrains.noteapphomework.data.QuitDialogListener;


public class QuitDialogFragment extends DialogFragment {

    public static final String QUIT_DIALOG_FRAGMENT_TAG = "QUIT_DIALOG_FRAGMENT_TAG";
    private MaterialButton quitButtonYes;
    private MaterialButton quitButtonNo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View quitDialogFragmentView = inflater.inflate(R.layout.fragment_quit_dialog, null);
        return quitDialogFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        quitButtonYes = view.findViewById(R.id.quit_button_yes);
        quitButtonNo = view.findViewById(R.id.quit_button_no);


        quitButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireActivity(), "Приложение Note App закрыто", Toast.LENGTH_SHORT).show();
                ((QuitDialogListener) requireActivity()).quitDialogYes();
            }
        });

        quitButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}

