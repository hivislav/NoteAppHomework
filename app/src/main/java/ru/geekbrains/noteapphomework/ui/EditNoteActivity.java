package ru.geekbrains.noteapphomework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;
    private Repo repo = InMemoryRepoImp.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        note = (Note) getIntent().getSerializableExtra(Note.NOTE);

        setContentView(R.layout.activity_edit_note);

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
        EditText editTitle = findViewById(R.id.set_title);
        EditText editDescription = findViewById(R.id.set_description);
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