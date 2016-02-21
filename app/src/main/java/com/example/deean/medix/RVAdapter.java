package com.example.deean.medix;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Deean on 21.2.2016..
 */
public class RVAdapter  extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        CardView cv;
        TextView personName;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Lijek> lijeks;

    RVAdapter(List<Lijek> lijeks){
        this.lijeks = lijeks;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view_lijek, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(lijeks.get(i).naziv);
        personViewHolder.personPhoto.setImageResource(lijeks.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return lijeks.size();
    }
}
