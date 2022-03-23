package ru.geekbrains.noteapphomework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ru.geekbrains.noteapphomework.data.Controller;
import ru.geekbrains.noteapphomework.data.InMemoryRepoImp;
import ru.geekbrains.noteapphomework.data.Note;
import ru.geekbrains.noteapphomework.data.Repo;

public class AddNoteFragment extends Fragment {

    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTitle = view.findViewById(R.id.set_title);
        editDescription = view.findViewById(R.id.set_description);

        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).pressedOkButtonEditNote();
            }
        });
    }

    void saveNote()
    {
        Note editNote = new Note(editTitle.getText().toString(), editDescription.getText().toString());
        if(!(editTitle.getText().toString().equals("") && editDescription.getText().toString().equals("")))
            repo.create(editNote);
    }

    //скрываем элементы тулбара активити и добавляем элементы меню фрагмента заметки

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_note_options_menu, menu);
        MenuItem[] menuItems = {menu.findItem(R.id.add_notes), menu.findItem(R.id.search_notes),
                menu.findItem(R.id.sort_notes)};
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i] != null) {
                menuItems[i].setVisible(false);
            }
        }
    }

    //TODO 23.03 реализовать добавление файла и шеринг заметки

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.share_note:
                Toast.makeText(requireActivity(),"Здесь будет возможность поделиться", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.add_multimedia_note:
                Toast.makeText(requireActivity(),"Здесь будет добавление файла", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}