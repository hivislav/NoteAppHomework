package ru.geekbrains.noteapphomework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem[] menuItems = {menu.findItem(R.id.add_notes), menu.findItem(R.id.search_notes),
                menu.findItem(R.id.sort_notes)};
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i] != null) {
                menuItems[i].setVisible(false);
            }
        }
    }
}