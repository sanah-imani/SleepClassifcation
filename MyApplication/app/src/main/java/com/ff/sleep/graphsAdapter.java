package com.ff.sleep;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.core.annotations.Line;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ff.sleep.Results.thresholds;
import static com.ff.sleep.Results.thresholdsV;

public class graphsAdapter extends RecyclerView.Adapter<graphsAdapter.ViewHolder> {
    private List<String> chartTypes;
    private Context c1;
    private SleepRecording mSR;
    private List<String> mXValues;
    private HashMap<String, Float> minMaxA, minMaxV;

    public graphsAdapter(Context c, List<String> ct, SleepRecording sr, List<String> xValues){
        c1 = c;
        chartTypes = ct;
        mSR = sr;
        mXValues = xValues;

    }
    @NonNull
    @Override
    public graphsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c1);
        View view = inflater.inflate(R.layout.sleep_row,parent,false);
        return new graphsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull graphsAdapter.ViewHolder holder, int position) {

        holder.graphName.setText(chartTypes.get(position));
        if(chartTypes.get(position).equals("Average Accelerometer Data")){
            displayCharts(mXValues, mSR.getmAccA(),mSR.getmAccV(),holder.l1,chartTypes.get(position), Color.RED, Color.BLUE, thresholds[0], thresholds[1],thresholdsV[0], thresholdsV[1]);
            setMinMax(mSR.getmAccA(),mSR.getmAccV());
            setThresholdLines(Float.parseFloat(mSR.getmAccAverage()),thresholds[0], thresholds[1], Float.parseFloat(mSR.getmAccAV()), thresholdsV[0], thresholdsV[1],holder.l1);
        }
        else if(chartTypes.get(position).equals("Average Motion Detection Data")){
            displayCharts(mXValues, mSR.getmMotionA(),mSR.getmMotionV(),holder.l1,chartTypes.get(position), Color.RED, Color.BLUE, thresholds[2], thresholds[3], thresholdsV[2], thresholdsV[3]);
            setMinMax(mSR.getmMotionA(),mSR.getmMotionV());
            setThresholdLines(Float.parseFloat(mSR.getmMotionAverage()),thresholds[2], thresholds[3], Float.parseFloat(mSR.getmMotionAV()), thresholdsV[2], thresholdsV[3],holder.l1);

        }

        else if(chartTypes.get(position).equals("Average Gyroscope Data")){
            displayCharts(mXValues, mSR.getmGyroA(),mSR.getmGyroV(),holder.l1,chartTypes.get(position), Color.RED, Color.BLUE, thresholds[4], thresholds[5], thresholdsV[4], thresholdsV[5]);
            setMinMax(mSR.getmGyroA(),mSR.getmGyroV());
            setThresholdLines(Float.parseFloat(mSR.getmGyroAverage()),thresholds[4], thresholds[5], Float.parseFloat(mSR.getmMotionAV()), thresholdsV[4], thresholdsV[5],holder.l1);

        }
        else if(chartTypes.get(position).equals("Average Light Level Data")){
            displayCharts(mXValues, mSR.getmLightA(),mSR.getmLightV(),holder.l1,chartTypes.get(position), Color.RED, Color.BLUE, thresholds[6], thresholds[7],thresholdsV[6], thresholdsV[7]);
            setMinMax(mSR.getmLightA(),mSR.getmLightV());
            setThresholdLines(Float.parseFloat(mSR.getmLightaverage()),thresholds[6], thresholds[7], Float.parseFloat(mSR.getmLightAV()), thresholdsV[6], thresholdsV[7],holder.l1);

        }
        else if(chartTypes.get(position).equals("Average Sound Level Data")){
            displayCharts(mXValues, mSR.getmSoundA(),mSR.getmSoundA(),holder.l1,chartTypes.get(position), Color.RED, Color.BLUE, thresholds[8], thresholds[9],thresholdsV[8], thresholdsV[9]);
            setMinMax(mSR.getmSoundA(),mSR.getmSoundA());
            setThresholdLines(Float.parseFloat(mSR.getmSoundAverage()),thresholds[8], thresholds[9], Float.parseFloat(mSR.getmSoundAV()), thresholdsV[8], thresholdsV[9],holder.l1);

        }
        else{

        }




    }

    @Override
    public int getItemCount() {
        return chartTypes.size();
    }

    public void displayCharts(List<String> data, List data2, List data3, LineChart chart, String label, int color1, int color2, float lower, float upper, float lower2, float upper2){
        String[] xValues = new String[data.size()];
        float[] yValues = new float[data2.size()];
        float[] yValues2 = new float[data3.size()];
        for(int i = 0; i < data.size(); i++){
            yValues2[i] = Float.parseFloat(data3.get(i).toString());
            yValues[i] = Float.parseFloat(data2.get(i).toString());
            xValues[i] = data.get(i);
        }




        chart = new myTrends(c1).setUpLineChart(chart, color1, color2, label, xValues, yValues, yValues2, lower,upper, lower2, upper2);

    }

    public void updateList(List<String> ct){

        chartTypes = ct;
        notifyDataSetChanged();

    }

    public void setMinMax(List data2, List data3){

        minMaxA = new HashMap<>();
        minMaxV = new HashMap<>();

        minMaxA.put("Min", 100000000f);
        minMaxA.put("Max", -100000000f);
        minMaxV.put("Min", 100000000f);
        minMaxV.put("Min", -100000000f);

        for(int i = 0; i < data2.size(); i++){
            if(Float.parseFloat((String) data2.get(i)) > minMaxA.get("Max")){
                minMaxA.put("Max", Float.parseFloat((String) data2.get(i)));

            }
            if(Float.parseFloat((String) data2.get(i)) < minMaxA.get("Min")){
                minMaxA.put("Min", Float.parseFloat((String) data2.get(i)));

            }
        }

        for(int i = 0; i < data3.size(); i++){
            if(Float.parseFloat((String) data3.get(i)) > minMaxV.get("Max")){
                minMaxV.put("Max", Float.parseFloat((String) data2.get(i)));

            }
            if(Float.parseFloat((String) data3.get(i)) < minMaxV.get("Min")){
                minMaxV.put("Min", Float.parseFloat((String) data2.get(i)));

            }
        }

    }
    public void setThresholdLines(float avg, float lower, float upper, float avg2, float lower2, float upper2, LineChart lc){

        LimitLine ll = new LimitLine(upper, "Maximum");
        lc.getAxisLeft().addLimitLine(ll);
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setLineColor(Color.RED);
        LimitLine ll1 = new LimitLine(avg, "Average");
        lc.getAxisLeft().addLimitLine(ll);
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setLineColor(Color.RED);
        LimitLine ll2 = new LimitLine(lower, "Minimum");
        lc.getAxisLeft().addLimitLine(ll);
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setLineColor(Color.RED);
        LimitLine ll3 = new LimitLine(upper2, "Maximum");
        lc.getAxisRight().addLimitLine(ll);
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setLineColor(Color.BLUE);
        LimitLine ll4 = new LimitLine(avg2, "Average");
        lc.getAxisRight().addLimitLine(ll);
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setLineColor(Color.BLUE);
        LimitLine ll5 = new LimitLine(lower2, "Minimum");
        lc.getAxisRight().addLimitLine(ll);
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setLineColor(Color.BLUE);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnChartValueSelectedListener {
        TextView graphName;
        LineChart l1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            graphName = itemView.findViewById(R.id.graphName);
            l1 = itemView.findViewById(R.id.chart1);
            l1.setOnChartValueSelectedListener(ViewHolder.this);
            l1.isHighlightPerTapEnabled();

        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {

            if(minMaxA != null && minMaxV != null){
                if(e.getY() == minMaxA.get("Max") || e.getY() == minMaxV.get("Max")){
                    Toast.makeText(c1, e.getY() + " on" + e.getX() + "\n" + "This is the maximum value", Toast.LENGTH_LONG).show();
                }
                else if(e.getY() == minMaxA.get("Min") || e.getX() == minMaxV.get("Min")){

                    Toast.makeText(c1, e.getY() + " on" + e.getX() + "\n" + "This is the minimum value", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(c1, e.getY() + " on" + e.getX(), Toast.LENGTH_LONG).show();
                }

            }





        }

        @Override
        public void onNothingSelected() {

        }



    }
}
