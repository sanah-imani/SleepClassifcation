package com.ff.sleep;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriteCSV {
    Context myCon;
    String mFile;
    String mkey;
    ArrayList mdata;

    public WriteCSV(String fileName, String key, Context c1, ArrayList data){
        myCon = c1;
        mFile = fileName;
        mkey = key;
        mdata = data;

    }

    public void setData(List<String> stringData) throws Exception {

        CSVWriter csvWriter = new CSVWriter(new FileWriter(mFile));

        String[] tokens = new String[stringData.size()];


        for(int i = 0; i < stringData.size(); i++){
            tokens[i] = stringData.get(i);


        }
        csvWriter.writeNext(tokens);

        csvWriter.close();



    }

    public void addSleepRecording(SleepRecording sr) throws Exception {
        List<String> stringData = new ArrayList<>();
        ArrayList sleepArrayData = sr.getAll();
        for (int i = 0; i < 15; i++){
            stringData.add(sleepArrayData.get(i).toString());
        }
        for(int i = 15; i < sleepArrayData.size(); i++){

            List l1 = (List) sleepArrayData.get(i);

            StringBuffer sb = new StringBuffer();
            for(int j = 0; j < l1.size(); j++) {
                if(j == -1){
                    sb.append(l1.get(j).toString());

                }
                else{
                    sb.append(l1.get(j).toString()+ " ");
                }

            }
            String str = sb.toString();
            stringData.add(str);

        }
        setData(stringData);
    }

    public void changeProfileData(){



    }

    public void changeUserData(String ID) throws IOException {
        File inputFile = new File(mFile);

// Read existing file
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> csvBody = null;
        try {
            csvBody = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        int row = 0;
        for(String[] tokens: csvBody){
            if(tokens[1].equals(Profile.USER_EMAIL_PHONE)){
                csvBody.get(row)[3] = csvBody.get(row)[3] + " " + ID;
            }
            row++;
        }
// get CSV row column  and replace with by using row and column

        reader.close();

// Write to CSV file which is open
        CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }
}
