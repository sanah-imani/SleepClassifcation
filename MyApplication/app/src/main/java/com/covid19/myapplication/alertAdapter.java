package com.covid19.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class alertAdapter extends RecyclerView.Adapter<alertAdapter.ViewHolder>{

    List<String> notifications, timeStamps;
    Context c1;

    public alertAdapter(Context context, List<String> not, List<String> ts){
        notifications = not;
        timeStamps = ts;
        c1 = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(c1);
        View view = inflater.inflate(R.layout.alert_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.alert.setText(notifications.get(position));
        holder.time.setText(timeStamps.get(position));

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView alert;
        TextView time;

        public ViewHolder(View itemView){
            super(itemView);
            alert = itemView.findViewById(R.id.notification);
            time = itemView.findViewById(R.id.time);


        }
    }


}
