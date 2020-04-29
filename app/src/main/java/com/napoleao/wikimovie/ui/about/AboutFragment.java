package com.napoleao.wikimovie.ui.about;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napoleao.wikimovie.R;

/**
 * Fragment da tela About (sobre).
 */
public class AboutFragment extends Fragment {


    public static AboutFragment newInstance() {
        return new AboutFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


}
