package com.jackingaming.notesquirrel.sandbox.downloadhtml;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class ViewFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ViewFragment.onCreate(Bundle)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "ViewFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        TextView textViewCommunication = (TextView) view.findViewById(R.id.textView_displayer);
        textViewCommunication.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "ViewFragment.onPause()");
    }

}