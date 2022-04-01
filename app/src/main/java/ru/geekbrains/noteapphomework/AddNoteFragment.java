package ru.geekbrains.noteapphomework;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.DatePickerListener;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.QuitDialogListener;
import ru.geekbrains.noteapphomework.data.Repo;

public class AddNoteFragment extends Fragment{

    private static final int PENDING_REQUEST_ID = 416;
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;
    private TextView dateTextView;
    private String date;
    public static final String CHANNEL_NOTIFICATION_ID_NEW_NOTE = "CHANNEL_NOTIFICATION_ID_NEW_NOTE";
    public static final int NOTIFICATION_NEW_NOTE_ID = 444;
    public static final String DATE = "DATE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTitle = view.findViewById(R.id.set_title);
        editDescription = view.findViewById(R.id.set_description);
        dateTextView = view.findViewById(R.id.set_date);

        if (savedInstanceState == null) {
            Date currentDate = new Date();
            // Форматирование времени как "день.месяц.год"
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            date = dateFormat.format(currentDate);
            dateTextView.setText(date);
        } else {
            date = savedInstanceState.getString(DATE);
            dateTextView.setText(date);
        }


        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).pressedOkButtonEditNote();
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DatePickerListener) requireActivity()).callDatePicker();
            }
        });
    }

    void saveNote()
    {
        Note editNote = new Note(editTitle.getText().toString(), editDescription.getText().toString(), dateTextView.getText().toString());
        if(!(editTitle.getText().toString().equals("") && editDescription.getText().toString().equals(""))) {
            repo.create(editNote);
            showNotificationNewNote();
        }
    }

    //скрываем элементы тулбара активити и добавляем элементы меню фрагмента заметки

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_note_options_menu, menu);
        MenuItem[] menuItems = {menu.findItem(R.id.add_notes), menu.findItem(R.id.search_notes),
                menu.findItem(R.id.sort_notes)};
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i] != null) {
                menuItems[i].setVisible(false);
            }
        }
    }

    //TODO 23.03 реализовать добавление файла и шеринг заметки

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.share_note:
                Toast.makeText(requireActivity(),"Здесь будет возможность поделиться", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.add_multimedia_note:
                Toast.makeText(requireActivity(),"Здесь будет добавление файла", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNotificationNewNote(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), CHANNEL_NOTIFICATION_ID_NEW_NOTE);

        //реализация запуска приложения из строки уведомления
        Intent notesListActivityIntent = new Intent(requireActivity(), NotesListActivity.class);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
          requireActivity(), PENDING_REQUEST_ID, notesListActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT      
        );

        //

        builder
                .setContentTitle(editTitle.getText().toString())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText(editDescription.getText().toString());

        NotificationManagerCompat.from(requireActivity()).notify(NOTIFICATION_NEW_NOTE_ID, builder.build());
    }

    void setDate(String date){
        this.date = date;
        dateTextView.setText(date);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATE, date);
    }

}