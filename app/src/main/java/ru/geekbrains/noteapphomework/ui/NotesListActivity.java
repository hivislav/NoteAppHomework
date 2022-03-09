package ru.geekbrains.noteapphomework.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.geekbrains.noteapphomework.EditNoteFragment;
import ru.geekbrains.noteapphomework.NotesListFragment;
import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;
import ru.geekbrains.noteapphomework.recycler.NotesAdapter;


public class NotesListActivity extends AppCompatActivity implements Controller {

    private FragmentManager manager;
    public static final String EDIT_NOTE_LANDSCAPE = "EDIT_NOTE_LANDSCAPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);


        manager = getSupportFragmentManager();

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            manager
                    .beginTransaction()
                    .replace(R.id.left_landscape_container, new NotesListFragment())
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_notes_list_container, new NotesListFragment())
                    .commit();
        }
    }


    @Override
    public void openEditNoteFragment(Note note) {
        manager
                .beginTransaction()
                .replace(R.id.fragment_notes_list_container, EditNoteFragment.getInstance(note))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openEditNoteFragmentLandscape(Note note) {
        manager
                .beginTransaction()
                .replace(R.id.right_landscape_container, EditNoteFragment.getInstance(note), EDIT_NOTE_LANDSCAPE)
                .commit();
    }

    @Override
    public void pressedOkButtonEditNote() {
        manager.popBackStack();
    }

    @Override
    public void pressedOkButtonEditNoteLandscape() {
        manager
                .beginTransaction()
                .remove(manager.findFragmentByTag(EDIT_NOTE_LANDSCAPE))
                .replace(R.id.left_landscape_container, new NotesListFragment())
                .commit();
    }
}