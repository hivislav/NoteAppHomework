package ru.geekbrains.noteapphomework.recycler;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private Note note;


    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_descripton);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNoteClick(note);
            }
        });
    }

    void bind(Note note)
    {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
    }
}
