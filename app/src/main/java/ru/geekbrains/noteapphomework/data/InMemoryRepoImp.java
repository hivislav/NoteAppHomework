package ru.geekbrains.noteapphomework.data;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImp implements Repo {

    private static InMemoryRepoImp repo;
    private InMemoryRepoImp()
    {
        init();
    }

    private void init()
    {
        create(new Note("Title 1", "Description 1"));
        create(new Note("Title 2", "Description 2"));
        create(new Note("Title 3", "Description 3"));
        create(new Note("Title 4", "Description 4"));
        create(new Note("Title 5", "Description 5"));
        create(new Note("Title 6", "Description 6"));
        create(new Note("Title 7", "Description 7"));
        create(new Note("Title 8", "Description 8"));
        create(new Note("Title 9", "Description 9"));
        create(new Note("Title 10", "Description 10"));
        create(new Note("Title 11", "Description 11"));
        create(new Note("Title 12", "Description 12"));
        create(new Note("Title 13", "Description 13"));
        create(new Note("Title 14", "Description 14"));
        create(new Note("Title 15", "Description 15"));
    }

    public static InMemoryRepoImp getInstance(){
        if (repo == null)
        {
            repo = new InMemoryRepoImp();
        }
        return repo;
    }

    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;


    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
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
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }
}
