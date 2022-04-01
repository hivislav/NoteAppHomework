package ru.geekbrains.noteapphomework;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.DatePickerListener;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.QuitDialogListener;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class NotesListActivity extends AppCompatActivity implements Controller,
        QuitDialogListener, DatePickerListener {

    private FragmentManager manager;
    public static final String EDIT_NOTE = "EDIT_NOTE";
    public static final String LIST_FRAGMENT = "LIST_FRAGMENT";
    public static final String LIST_NAVIGATION_ABOUT = "LIST_NAVIGATION_ABOUT";
    public static final String ADD_NOTE = "ADD_NOTE";
    public static final String CHANNEL_NOTIFICATION_ID_NEW_NOTE = "CHANNEL_NOTIFICATION_ID_NEW_NOTE";
    private int orientation = ORIENTATION_PORTRAIT;
    private EditNoteFragment editNoteFragment;
    private AddNoteFragment addNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        initToolbarAndDrawer();

        orientation = getResources().getConfiguration().orientation;

        manager = getSupportFragmentManager();

        editNoteFragment = (EditNoteFragment) manager.findFragmentByTag(EDIT_NOTE);
        addNoteFragment = (AddNoteFragment) manager.findFragmentByTag(ADD_NOTE);

        if (savedInstanceState == null) {
            manager
                    .beginTransaction()
                    .replace(R.id.fragment_notes_list_container, new NotesListFragment(), LIST_FRAGMENT)
                    .commit();
        }

        if (editNoteFragment != null) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().remove(editNoteFragment).commit();
            manager.executePendingTransactions();
            manager.beginTransaction().replace(
                    orientation == ORIENTATION_PORTRAIT ? R.id.fragment_notes_list_container : R.id.right_landscape_container,
                    editNoteFragment, EDIT_NOTE)
                    .addToBackStack(null)
                    .commit();
        }

        if (addNoteFragment != null) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().remove(addNoteFragment).commit();
            manager.executePendingTransactions();
            manager.beginTransaction().replace(
                    orientation == ORIENTATION_PORTRAIT ? R.id.fragment_notes_list_container : R.id.right_landscape_container,
                    addNoteFragment, ADD_NOTE)
                    .addToBackStack(null)
                    .commit();
        }

        if (orientation == ORIENTATION_PORTRAIT &&
                manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels();
        }

    }

    @Override
    public void openEditNoteFragment(Note note) {
        manager
                .beginTransaction()
                .replace(
                        orientation == ORIENTATION_PORTRAIT ? R.id.fragment_notes_list_container : R.id.right_landscape_container,
                        EditNoteFragment.getInstance(note), EDIT_NOTE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void pressedOkButtonEditNote() {
        manager.popBackStack();
        if (manager.findFragmentByTag(LIST_FRAGMENT) != null)
            ((NotesListFragment) manager.findFragmentByTag(LIST_FRAGMENT)).refresh();

        if (orientation ==
                ORIENTATION_LANDSCAPE && manager.findFragmentByTag(EDIT_NOTE) != null)
            manager
                    .beginTransaction()
                    .remove(manager.findFragmentByTag(EDIT_NOTE))
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

        switch (id) {
            case R.id.sort_notes:
                Toast.makeText(this, "Здесь будет сортировка по алфавиту", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.search_notes:
                Toast.makeText(this, "Здесь будет поиск заметки", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.add_notes:
                manager
                        .beginTransaction()
                        .replace(
                                orientation == ORIENTATION_PORTRAIT ? R.id.fragment_notes_list_container : R.id.right_landscape_container,
                                new AddNoteFragment(), ADD_NOTE)
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
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
                        if (manager.findFragmentByTag(LIST_NAVIGATION_ABOUT) == null)
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

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() == 0) // чтобы форма закрытия всплывала только из основного фрагмента
            new QuitDialogFragment().show(getSupportFragmentManager(), QuitDialogFragment.QUIT_DIALOG_FRAGMENT_TAG);
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void quitDialogYes() {
        finish();
    }


    //Создание NotificationChannel для реализации уведомлений
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannels() {
        String channelName = getString(R.string.channel_notification_name);
        String channelDescription = getString(R.string.channel_notification_description);
        int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(CHANNEL_NOTIFICATION_ID_NEW_NOTE, channelName, channelImportance);
        channel.setDescription(channelDescription);

        NotificationManagerCompat.from(this).createNotificationChannel(channel);
    }

    @Override
    public void callDatePicker() {
        new DatePickerFragment().show(getSupportFragmentManager(), DatePickerFragment.DATE_PICKER);
    }

    @Override
    public void sendDatePicker(String date) {
        if (manager.findFragmentByTag(ADD_NOTE) != null) {
            AddNoteFragment addNoteFragment = (AddNoteFragment) manager.findFragmentByTag(ADD_NOTE);
            addNoteFragment.setDate(date);
        }
        if (manager.findFragmentByTag(EDIT_NOTE) != null) {
            EditNoteFragment editNoteFragment = (EditNoteFragment) manager.findFragmentByTag(EDIT_NOTE);
            editNoteFragment.setDate(date);
        }
    }
}