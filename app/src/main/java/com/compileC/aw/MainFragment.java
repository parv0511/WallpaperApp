package com.compileC.aw;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    ImageButton nobita,dora,siz,sun,gia,hot;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate( R.layout.fragment_main, container, false );
        nobita = v.findViewById( R.id.nob );
       nobita.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent( getActivity(),Nobita.class );
               startActivity( i );
           }
       } );
        dora = v.findViewById( R.id.imageView );
        dora.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(),Doreamon.class );
                startActivity( i );
            }
        } );
        siz = v.findViewById( R.id.siz );
        siz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(),Shizuka.class );
                startActivity( i );
            }
        } );
        sun = v.findViewById( R.id.sun );
        sun.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(),Suneo.class );
                startActivity( i );
            }
        } );
        gia = v.findViewById( R.id.gia );
        gia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(),Gian.class );
                startActivity( i );
            }
        } );
        hot = v.findViewById( R.id.hot );
        hot.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(),hot.class );
                startActivity( i );
            }
        } );
        return v;
    }




}
