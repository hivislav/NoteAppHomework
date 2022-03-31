package ru.geekbrains.noteapphomework.recycler;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.noteapphomework.NotesListFragment;
import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private TextView date;
    private Note note;
    private NotesListFragment notesListFragment;



    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener, NotesListFragment notesListFragment) {
        super(itemView);
        this.notesListFragment = notesListFragment;
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        date = itemView.findViewById(R.id.note_date);
        registerContextMenu(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNoteClick(note);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onNoteLongClick(note);
                return false;
            }
        });

    }

    void bind(Note note)
    {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        date.setText(note.getDate());
    }


//Метод для регистрации контекстного меню на конкретном холдере
    private void registerContextMenu(@NonNull View itemView) {
        if (notesListFragment != null)
            notesListFragment.registerForContextMenu(itemView);
    }
}
