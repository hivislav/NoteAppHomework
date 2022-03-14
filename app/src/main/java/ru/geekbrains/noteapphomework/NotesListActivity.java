package ru.geekbrains.noteapphomework;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.Note;

public class NotesListActivity extends AppCompatActivity implements Controller {

    private FragmentManager manager;
    public static final String EDIT_NOTE_LANDSCAPE = "EDIT_NOTE_LANDSCAPE";
    public static final String LIST_FRAGMENT = "LIST_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        manager = getSupportFragmentManager();
        if(savedInstanceState == null)
                manager
                        .beginTransaction()
                        .replace(R.id.fragment_notes_list_container, new NotesListFragment(), LIST_FRAGMENT)
                        .commit();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        }
    }


    @Override
    public void openEditNoteFragment(Note note) {
            manager
                    .beginTransaction()
                    .replace(R.id.fragment_notes_list_container, EditNoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .commit();}

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
        if(manager.findFragmentByTag(LIST_FRAGMENT) != null)
            ((NotesListFragment)manager.findFragmentByTag(LIST_FRAGMENT)).refresh();

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE && manager.findFragmentByTag(EDIT_NOTE_LANDSCAPE) != null)
        manager
                .beginTransaction()
                .remove(manager.findFragmentByTag(EDIT_NOTE_LANDSCAPE))
                .commit();
    }
}