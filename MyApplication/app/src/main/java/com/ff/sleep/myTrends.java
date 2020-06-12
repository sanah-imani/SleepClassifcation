package com.ff.sleep;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class myTrends {

    Context myCon;

    public myTrends(Context c1){

        myCon = c1;

    }

    public Pie setUpPie(String[] labels, double[] percentages){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i = 0; i <labels.length; i++){
            if (percentages[i] != 0){
                dataEntries.add(new ValueDataEntry(labels[i], percentages[i]));
            }
        }
        if (dataEntries.size() > 0) {
            pie.data(dataEntries);

        }
        return pie;
    }

    public BarChart setBarChart(String[] labels, float[] values, String title, BarChart barChart, String xlabel){

        ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
        ArrayList<String> mlabels = new ArrayList<>();

        for (int i = 0; i<labels.length; i++){
            barEntryArrayList.add(new BarEntry(i, values[i]));
            mlabels.add(labels[i]);

        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, title);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText(xlabel);
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(mlabels.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();


        return barChart;

    }

    public LineChart setUpLineChart(LineChart lineChart1, int color, int color2, String label, String[] xValues, float[] values, float[] values2, float lower, float upper, float lower2, float upper2){


        lineChart1.setDragEnabled(true);
        lineChart1.setScaleEnabled(false);
        lineChart1.setPinchZoom(true);

        Legend l = lineChart1.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        ArrayList<Entry> yValue = new ArrayList<>();
        ArrayList<Entry> yValue2 = new ArrayList<>();
        for (int i = 0; i<values.length; i++){
            yValue.add(new Entry(i,values[i]));
            yValue2.add(new Entry(i, values2[i]));
        }
        LineDataSet set1 = new LineDataSet(yValue, "Average Value");
        for (int j = 0; j < yValue.size(); j++){
            if(values[j] < lower || values[j] > upper){
                set1.getCircleColor(Color.RED);
            }
        }

        LineDataSet set2 = new LineDataSet(yValue2, "Variability");
        for (int j = 0; j < yValue2.size(); j++){
            if(values[j] < lower2 || values[j] > upper2){
                set2.getCircleColor(Color.RED);
            }
        }

        set1.setFillAlpha(110);
        set1.setColor(color);
        set1.setLineWidth(3f);
        set1.setValueTextSize(15f);
        set2.setFillAlpha(110);
        set2.setColor(color2);
        set2.setLineWidth(3f);
        set2.setValueTextSize(15f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        lineChart1.setData(data);

        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xValues));
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        return lineChart1;

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;
        public MyXAxisValueFormatter(String[] mValues){
            this.mValues = mValues;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
}
