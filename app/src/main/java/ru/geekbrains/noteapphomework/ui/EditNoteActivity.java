package ru.geekbrains.noteapphomework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        note = (Note) getIntent().getSerializableExtra(Note.NOTE);

        setContentView(R.layout.fragment_edit_note);

        editTitle = findViewById(R.id.set_title);
        editDescription = findViewById(R.id.set_description);
        editTitle.setText(note.getTitle());
        editDescription.setText(note.getDescription());

        Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              saveNote();
            }
        });
    }

    //возвращение отредактированной заметки в NoteListActivity

    void saveNote()
    {
        int noteId = note.getId();
        Note editNote = note;

        editNote.setTitle(editTitle.getText().toString());
        editNote.setDescription(editDescription.getText().toString());
        editNote.setId(noteId);
        repo.update(editNote);

        Intent result = new Intent();
        result.putExtra(Note.NOTE, editNote);
        setResult(RESULT_OK, result);
        finish();
    }
}