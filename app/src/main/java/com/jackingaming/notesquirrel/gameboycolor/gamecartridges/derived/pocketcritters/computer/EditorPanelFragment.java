package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.computer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class EditorPanelFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "EditorPanelFragment.onCreate(Bundle)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "EditorPanelFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.fragment_editor_panel, container, false);

        // Do stuff.

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "EditorPanelFragment.onPause()");

        // Save data if necessary.
    }

}