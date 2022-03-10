package ru.geekbrains.noteapphomework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;

public class EditNoteFragment extends Fragment {

    private Note note;
    private int noteId;
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;
    public static final String NOTE = "NOTE";

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
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        editTitle = view.findViewById(R.id.set_title);
        editDescription = view.findViewById(R.id.set_description);

        init(note);

        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).pressedOkButtonEditNote();
            }
        });
    }

    //возвращение отредактированной заметки в NotesListFragment (через перезапись в репо)

     void saveNote()
    {
        Note editNote = new Note(editTitle.getText().toString(), editDescription.getText().toString());
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
        noteId = note.getId();
    }
}

