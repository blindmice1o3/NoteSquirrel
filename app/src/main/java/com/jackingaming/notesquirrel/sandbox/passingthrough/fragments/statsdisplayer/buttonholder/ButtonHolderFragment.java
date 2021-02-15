package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.statsdisplayer.buttonholder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;

public class ButtonHolderFragment extends Fragment {

    private TextView textView;

    public ButtonHolderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button_holder, container, false);
        textView = view.findViewById(R.id.textview_buttonholderfragment);
        return view;
    }

    public void setTextForTextView(String textForTextView) {
        textView.setText(textForTextView);
    }
}
