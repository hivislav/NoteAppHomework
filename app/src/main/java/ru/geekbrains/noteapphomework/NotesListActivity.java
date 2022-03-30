package ru.geekbrains.noteapphomework;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.Note;

public class NotesListActivity extends AppCompatActivity implements Controller {

    private FragmentManager manager;
    public static final String EDIT_NOTE_LANDSCAPE = "EDIT_NOTE_LANDSCAPE";
    public static final String LIST_FRAGMENT = "LIST_FRAGMENT";
    public static final String LIST_NAVIGATION_ABOUT = "LIST_NAVIGATION_ABOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        initToolbarAndDrawer();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_list_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    //TODO 23.03 реализовать сортировку и поиск

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.sort_notes:
                Toast.makeText(this,"Здесь будет сортировка по алфавиту", Toast.LENGTH_SHORT).show();
            return true;

            case R.id.search_notes:
                Toast.makeText(this,"Здесь будет поиск заметки", Toast.LENGTH_SHORT).show();
            return true;

            case R.id.add_notes:
                manager
                        .beginTransaction()
                        .replace(R.id.fragment_notes_list_container, new AddNoteFragment())
                        .addToBackStack(null)
                        .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbarAndDrawer (){
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar){
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //объединяем и синхронизируем тулбар и дравер
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_settings:
                        // TODO реализовать меню настроек
                        return true;

                    case R.id.navigation_feedback:
                       // // TODO реализовать меню обратной связи
                        return true;

                    case R.id.navigation_about:
                        if(manager.findFragmentByTag(LIST_NAVIGATION_ABOUT) == null)
                        manager
                                .beginTransaction()
                                .replace(R.id.fragment_notes_list_container, new AboutFragment(), LIST_NAVIGATION_ABOUT)
                                .addToBackStack(null)
                                .commit();
                        drawer.close();
                        return true;
                }
                return false;
            }
        });
    }
}