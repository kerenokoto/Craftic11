package com.craftic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.craftic.R;
import com.craftic.ui.activity.UserProfileModule;

import java.util.Collections;
import java.util.List;

/**
 * Created by keren on 4/30/15.
 */
public class UserProfileAdapter2 extends RecyclerView.Adapter<UserProfileAdapter2.MyViewHolder> {

    List<UserProfileModule> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public UserProfileAdapter2(Context context, List<UserProfileModule> data){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.user_profile, parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserProfileModule current=data.get(position);
        holder.username.setText(current.getFname()+" "+current.getLname());
        holder.cat.setText(current.getCategorytype());
        holder.btnFollow.setText("Want");
        holder.ivUserProfilePhoto.setImageResource(current.getIconId());
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username,cat;
        ImageView ivUserProfilePhoto;
        Button btnFollow;

        public MyViewHolder(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.username);
            ivUserProfilePhoto= (ImageView) itemView.findViewById(R.id.ivUserProfilePhoto);
            cat= (TextView) itemView.findViewById(R.id.cat);
            btnFollow= (Button) itemView.findViewById(R.id.btnFollow);
            //icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context,"Item clicked at "+ getPosition(),Toast.LENGTH_LONG).show();

        }
    }
}

