package ru.geekbrains.noteapphomework.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.noteapphomework.R;

public class InMemoryRepoImp implements Repo {

    private static InMemoryRepoImp repo;
    private List<Note> notes = new ArrayList<>();
    private int counter = 0;
    private SharedPreferences preferences;
    public static final String NOTES_KEY = "NOTES_KEY";
    private Gson gson = new Gson();



    private InMemoryRepoImp()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(PreferencesApplication.getInstance());
        notes = getAll();
    }

    public static InMemoryRepoImp getInstance(){
        if (repo == null)
        {
            repo = new InMemoryRepoImp();
        }
        return repo;
    }

    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
        String data = gson.toJson(notes);
        preferences
                .edit()
                .putString(NOTES_KEY,data)
                .apply();
        return id;
    }

    @Override
    public Note read(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id)
                return notes.get(i);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == note.getId()) {
                notes.set(i, note);
                String data = gson.toJson(notes);
                preferences
                        .edit()
                        .putString(NOTES_KEY,data)
                        .apply();
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.remove(i);
                preferences
                        .edit()
                        .remove(NOTES_KEY)
                        .apply();
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        String data = preferences.getString(NOTES_KEY, "{}");
        try {
            //строку превращаем в список заметок при десериализации
            notes = gson.fromJson(
                    data, new TypeToken<List<Note>>(){}.getType());
        }
        catch (Exception e){
            Log.d("happy", "Exception: " + e.getMessage());
        }
        if (notes == null)
            notes = new ArrayList<>();

        return notes;
    }
}
