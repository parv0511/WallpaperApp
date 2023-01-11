package com.compileC.aw;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class Nobita extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {


    ProgressBar progressBar;
    BottomNavigationView bn;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nobita );

        progressBar= findViewById( R.id.progressbar );
        bn = findViewById( R.id.bottom_nav );
        bn.setOnNavigationItemSelectedListener( this );
        recyclerView = findViewById( R.id.recycle_view );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ));
        firebaseDatabase = FirebaseDatabase.getInstance();
        dr = firebaseDatabase.getReference().child( "nob" );


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<nob> op = new FirebaseRecyclerOptions.Builder<nob>()
                .setQuery( dr,nob.class )
                .build();

        FirebaseRecyclerAdapter<nob,MyviewHolder> adapter = new FirebaseRecyclerAdapter<nob, MyviewHolder>(op) {
            @Override
            protected void onBindViewHolder(@NonNull MyviewHolder holder,final int position, @NonNull nob model) {
                System.out.println("Start_1");
                System.out.println(model.getUrl());
                System.out.println("Start_2");
                holder.setdetails( getApplication(), model.getName(), model.getUrl() );
                holder.v.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( Nobita.this, Onclick.class );
                        intent.putExtra( "nobkey", getRef(holder.getAbsoluteAdapterPosition()).getKey() );
                        startActivity( intent );
                    }
                } );
                holder.v.setOnLongClickListener( new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                } );
                progressBar.setVisibility(View.GONE);
            }


                @NonNull
                @Override
                public MyviewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
                    View v = LayoutInflater.from( parent.getContext() )
                            .inflate( R.layout.activity_imgview, parent, false );
                    return new MyviewHolder( v );

                }
            };
        recyclerView.setAdapter( adapter );
        adapter.startListening();



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent i = new Intent(Nobita
                        .this,MainActivity.class);
                startActivity(i);
                break;
            case R.id.nav_share:
                Intent share = new Intent( Intent.ACTION_SEND );
                share.setType( "text/plain" );
                String shareBody ="https://play.google.com/store/apps/details?id=com.compileC.aw";
                String shareSubject = "ANIME X CARTOON";
                share.putExtra( Intent.EXTRA_TEXT, shareBody );
                share.putExtra( Intent.EXTRA_SUBJECT, shareSubject );

                startActivity( Intent.createChooser( share, "Share" ) );
                break;
            case R.id.nav_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.compileC.aw")));
                break;


        }
        return true;

    }


}
