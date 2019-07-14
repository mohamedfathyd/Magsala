package com.khalej.magsala.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.khalej.magsala.Model.contact_annonce;
import com.khalej.magsala.R;

import java.util.List;


public class RecyclerAdapter_first_annonce extends RecyclerView.Adapter<RecyclerAdapter_first_annonce.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_annonce> contactslist;
     RecyclerView recyclerView;

    public RecyclerAdapter_first_annonce(Context context, List<contact_annonce> contactslist, RecyclerView recyclerView){
        this.contactslist=contactslist;
        this.context=context;
        this.recyclerView=recyclerView;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.annonce_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
try {


    //myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/flat.ttf");
    Glide.with(context).load(contactslist.get(position).getImage()).error(R.drawable.circlelogo).into(holder.image);
}
catch (Exception e){}
         holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog settingsDialog = new Dialog(context);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(R.layout.image_show);
                ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                Glide.with(context).load(contactslist.get(position).getImage()).error(R.drawable.circlelogo).into(img);
                settingsDialog.show();
          /*      Intent intent=new Intent(context, first_second_category.class);
                intent.putExtra("name",contactslist.get(position).getname());
                intent.putExtra("id",contactslist.get(position).getId());
                context.startActivity(intent);*/
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

    public MyViewHolder(View itemView) {
        super(itemView);

        image=(ImageView)itemView.findViewById(R.id.photo);

    }
}}