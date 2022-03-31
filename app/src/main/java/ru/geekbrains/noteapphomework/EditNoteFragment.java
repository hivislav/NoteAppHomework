package ru.geekbrains.noteapphomework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.DatePickerListener;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;

public class EditNoteFragment extends Fragment {

    private Note note;
    private int noteId;
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;
    private TextView dateTextView;
    public static final String NOTE = "NOTE";
    public static final String DATE = "DATE";
    private String date;

    public static EditNoteFragment getInstance(Note note){
        // создать фрагмент
        EditNoteFragment fragment = new EditNoteFragment();
        // создать аргументы
        Bundle args = new Bundle();
        // добавляем данные (объект) по строковому ключу
        // добавляем в аргументы входные параметры
        args.putSerializable(NOTE, note);
        // присоединить аргументы к фрагменту
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTitle = view.findViewById(R.id.set_title);
        editDescription = view.findViewById(R.id.set_description);
        dateTextView = view.findViewById(R.id.set_date);

        init(note);

        if (savedInstanceState == null) {
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

    //возвращение отредактированной заметки в NotesListFragment (через перезапись в репо)

     void saveNote()
    {
        Note editNote = new Note(editTitle.getText().toString(), editDescription.getText().toString(), dateTextView.getText().toString());
        editNote.setId(noteId);
        repo.update(editNote);
    }

    private void init(Note note){
        // извлечь аргументы
        Bundle args = getArguments();
        // извлечь по ключу из аргументов входные параметры
        note = (Note) args.getSerializable(NOTE);
        editTitle.setText(note.getTitle());
        editDescription.setText(note.getDescription());
        dateTextView.setText(note.getDate());
        date = note.getDate();
        noteId = note.getId();
    }

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

