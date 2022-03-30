package ru.geekbrains.noteapphomework;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;
import ru.geekbrains.noteapphomework.recycler.NotesAdapter;

public class NotesListFragment extends Fragment implements NotesAdapter.OnNoteClickListener {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NotesAdapter adapter;
    private Note note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = view.findViewById(R.id.list);
        adapter = new NotesAdapter(this);

        //функция для записи щелчков
        adapter.setOnNoteClickListener(this);

        adapter.setNotes(repo.getAll());

        //отрисовка границ между заметками
        list.addItemDecoration(new DividerItemDecoration(list.getContext(), LinearLayoutManager.VERTICAL));

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
    }

    @Override
    public void onNoteClick(Note note) {
            Log.d("happy", note.getDescription());
            ((Controller) requireActivity()).openEditNoteFragment(note);
        }

    @Override
    public void onNoteLongClick(Note note) {
        this.note = note;
    }

    public void refresh() {
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }



    //реализация контекстного меню на фрагменте
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.note_context_menu, menu);
    }

    //TODO 30.03
    // 1. реализовать шеринг
    // 2. реализовать диалоговое окно с подтверждением удаления заметки
    // 3. реализовать анимацию при удалении (т.к. мы знаем конкретный id удаляемой заметки)
    // 4. реализовать закрытие окна с редактированием заметки при ее удалении в альбомной ориентации


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.context_menu_change_note:
                ((Controller) requireActivity()).openEditNoteFragment(note);
                return true;
            case R.id.context_menu_delete_note:
                repo.delete(note.getId());
                refresh();
                return true;
            case R.id.context_menu_share_note:
                Toast.makeText(requireActivity(),"Здесь будет возможность поделиться", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}