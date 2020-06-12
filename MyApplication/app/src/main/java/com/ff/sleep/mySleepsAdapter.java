package com.ff.sleep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class mySleepsAdapter extends RecyclerView.Adapter<mySleepsAdapter.ViewHolder> {

    String[] timeTitles;
    String[] date;
    Context c1;
    String[] mIDs;

    public mySleepsAdapter(Context context, String[] TT, String[] DT, String[] IDs){
        timeTitles = TT;
        date = DT;
        c1 = context;
        mIDs = IDs;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c1);
        View view = inflater.inflate(R.layout.sleep_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText("Sleep at " + timeTitles[position]);
        holder.dateDesc.setText(date[position]);
        holder.learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c1,Results.class);
                i.putExtra("Sleep ID", mIDs[position]);
                c1.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return timeTitles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView dateDesc;
        Button learn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            dateDesc = itemView.findViewById(R.id.date);
            learn = itemView.findViewById(R.id.button_learn);
        }
    }
}
