package ru.geekbrains.noteapphomework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;
import ru.geekbrains.noteapphomework.recycler.NotesAdapter;


public class NotesListActivity extends AppCompatActivity implements NotesAdapter.OnNoteClickListener {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        list = findViewById(R.id.list);

        adapter = new NotesAdapter();

        //функция для записи щелчков
        adapter.setOnNoteClickListener(this);

        adapter.setNotes(repo.getAll());

        //отрисовка границ между заметками
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    public static final int EDIT_NOTE_REQUEST = 66;

    @Override
    public void onNoteClick(Note note) {
        Log.d("happy", note.getDescription());

        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        editNoteIntent.putExtra(Note.NOTE, note);

        startActivityForResult(editNoteIntent, EDIT_NOTE_REQUEST);
    }

    //функция обработки возвращаемой заметки - добавить ее в репо и обновить
    // данные в адаптере из репо


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            Note note = (Note) data.getSerializableExtra(Note.NOTE);
            repo.update(note);
            adapter.setNotes(repo.getAll());
        }
    }
}