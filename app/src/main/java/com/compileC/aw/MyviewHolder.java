package com.compileC.aw;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

class MyviewHolder extends RecyclerView.ViewHolder {
    View v;
    ImageView ig;
    TextView tx;
    public MyviewHolder(@NonNull View itemView) {
        super( itemView );
        v= itemView;

        itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClicklistener.onItemclick( v,getAbsoluteAdapterPosition() );
            }
        } );
        itemView.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClicklistener.onItemLongclick( v,getAbsoluteAdapterPosition() );
                return false;
            }
        } );

    }


    public void setdetails(Context context,String name,String url) {
            ig = v.findViewById( R.id.imgview );
          tx = v.findViewById( R.id.imgtxt );
        tx.setText(name);
        //Picasso.get().load(url).into(ig);
        Glide.with(context).load(url).into(ig);

        Animation animation = AnimationUtils.loadAnimation( context,android.R.anim.slide_in_left );
        itemView.startAnimation( animation );
        }

    private MyviewHolder.ClickListener mClicklistener;

    public  interface ClickListener {
        void onItemclick(View view, int position);

        void onItemLongclick(View view, int position);

    }
    public void setOnclickListener(MyviewHolder.ClickListener clickListener){
        mClicklistener=clickListener;
    }
}
