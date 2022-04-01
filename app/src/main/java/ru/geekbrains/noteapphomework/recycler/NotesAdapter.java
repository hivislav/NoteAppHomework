package ru.geekbrains.noteapphomework.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.noteapphomework.NotesListFragment;
import ru.geekbrains.noteapphomework.R;
import ru.geekbrains.noteapphomework.data.Note;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private NotesListFragment notesListFragment;
    private int noteHolderPosition;

    public NotesAdapter(Fragment fragment) {
        this.notesListFragment = (NotesListFragment) fragment;
    }



    // Создает новый холдер
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view, listener, notesListFragment);
    }

    //Холдер вышел за область видимости, его нужно наполнить новыми данными
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    // Сколько элементов показывать
    @Override
    public int getItemCount()
    {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    //Интерфейс, который будут имплементить те, кому нужны данные о щелчках
    public interface OnNoteClickListener{
        void onNoteClick(Note note);
        void onNoteLongClick(Note note);
    }

    private OnNoteClickListener listener;

    public void setOnNoteClickListener(OnNoteClickListener listener){
        this.listener = listener;
    }

}
