package com.compileC.aw;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class OnclickShi  extends AppCompatActivity {

    TextView ntitle;
    ImageView nimage;
    DatabaseReference re;
    Button msave, mset, mshare;
    Bitmap bitmap;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_onclick );


        msave = findViewById( R.id.save_btn );
        mset = findViewById( R.id.wall_btn );
        mshare = findViewById( R.id.share_btn );


        //save--------------------------
        msave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = ((BitmapDrawable)nimage.getDrawable()).getBitmap();
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE ) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission ={Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions( permission, WRITE_EXTERNAL_STORAGE_CODE);
                    }
                    else
                    {
                        saveImage();
                    }
                }
                else {
                    saveImage();
                }
            }
        });


        //set---------------------------------------
        mset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWall();
            }
        } );

        //share-------------------------------------
        mshare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        } );


        //===============================================
        ntitle = findViewById( R.id.imgtxtclick );
        nimage = findViewById( R.id.imgviewclick );
        re = FirebaseDatabase.getInstance().getReference().child( "shi" );
        String nobkey = getIntent().getStringExtra( "shikey" );

        re.child( nobkey ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Nname = dataSnapshot.child( "name" ).getValue().toString();
                String Nurl = dataSnapshot.child( "url" ).getValue().toString();
                Picasso.get().load( Nurl).into( nimage );
                ntitle.setText( Nname );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    private void saveImage() {
        String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss",
                Locale.getDefault()).format( System.currentTimeMillis() );

        File path = new File( String.valueOf( Environment.getExternalStorageDirectory() ) );
        File dir = new File( path+"/Wallpaper" );
        dir.mkdir();

        String name = timeStamp + ".PNG";
        File file = new File( dir,name );
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream( file );
            bitmap.compress( Bitmap.CompressFormat.PNG,100,outputStream );
            outputStream.flush();
            outputStream.close();
            Toast.makeText( this,"Image Saved"+dir,Toast.LENGTH_LONG ).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText( this,e.getMessage(),Toast.LENGTH_LONG ).show();
        }





    }
    //=====================================================================================================
    private void shareImage() {
        Bitmap bitmap = getBitmapFromView(nimage);

        try {
            File file =new File(this.getExternalCacheDir(),"blank.png");
            FileOutputStream fos = new FileOutputStream( file );
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, fos );
            fos.flush();
            fos.close();
            file.setReadable( true,false );
            Intent intent =new Intent( android.content.Intent.ACTION_SEND );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            intent.putExtra( Intent.EXTRA_STREAM, FileProvider.getUriForFile( com.compileC.aw.OnclickShi.this,BuildConfig.APPLICATION_ID+".provider",file ) );
            intent.setType( "image/*" );
            startActivity( Intent.createChooser( intent,"Share Image" ) );
        }
        catch (Exception e) {
            e.printStackTrace();
        }





    }


    private Bitmap getBitmapFromView(View view) {

        Bitmap bitmapreturn = Bitmap.createBitmap( view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmapreturn );
        Drawable bdraw = view.getBackground();

        if(bdraw!=null)
        {
            bdraw.draw( canvas );
        }
        else {
            canvas.drawColor( Color.WHITE );
        }
        view.draw( canvas );
        return bitmapreturn;

    }

    @SuppressLint("ShowToast")
    private void setWall() {
        bitmap =((BitmapDrawable)nimage.getDrawable()).getBitmap();
        WallpaperManager manager = WallpaperManager.getInstance( getApplicationContext() );

        try {
            manager.setBitmap( bitmap );
            Toast.makeText( this,"Wallpaper Set",Toast.LENGTH_SHORT ).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText( this,"Error, try again",Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
//===========================================================================================


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    saveImage();
                }
                else {
                    Toast.makeText( this,"enable permission",Toast.LENGTH_SHORT ).show();
                }
            }
        }
    }
}




