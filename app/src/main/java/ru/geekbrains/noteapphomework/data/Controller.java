package ru.geekbrains.noteapphomework.data;

public interface Controller{
    void openEditNoteFragment(Note note);
    void openEditNoteFragmentLandscape(Note note);
    void pressedOkButtonEditNote();
    void pressedOkButtonEditNoteLandscape();
}

