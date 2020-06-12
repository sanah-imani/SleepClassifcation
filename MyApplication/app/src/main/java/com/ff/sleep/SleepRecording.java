package com.ff.sleep;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SleepRecording {
    private String mSleepID, mSleepDate, mSleepTimes, mSleepScore, mSleepDuration, mAccAverage, mMotionAverage, mGyroAverage, mLightaverage, mSoundAverage, mAccAV, mMotionAV, mGryoAV, mLightAV, mSoundAV;
    private List<Float> mAccA, mMotionA, mGyroA, mLightA, mAccV, mMotionV, mGyroV, mLightV;
    private List<Double> mSoundA, mSoundV;
    private List<String> mpackageName, museTime;

    public SleepRecording(String SleepID, String SleepDate, String SleepTimes, String SleepScore, String SleepDuration, String AccAverage, String MotionAverage, String GyroAverage, String LightAverage, String SoundAverage, String AccAV, String MotionAV, String GryoAV, String LightAV, String SoundAV, List<Float> AccA, List<Float> MotionA, List<Float> GyroA, List<Float> LightA, List<Double> SoundA, List<Float> AccV, List<Float> MotionV,List<Float> GyroV, List<Float> LightV, List<Double> SoundV, List<String> packageName, List<String> useTime){
        mSleepID = SleepID;
        mSleepDate = SleepDate;
        mSleepTimes = SleepTimes;
        mSleepScore = SleepScore;
        mSleepDuration = SleepDuration;
        mAccAverage = AccAverage;
        mMotionAverage = MotionAverage;
        mGyroAverage = GyroAverage;
        mLightaverage = LightAverage;
        mSoundAverage = SoundAverage;
        mAccAV =  AccAV;
        mMotionAV = MotionAV;
        mGryoAV = GryoAV;
        mLightAV = LightAV;
        mSoundAV = SoundAV;
        mAccA = AccA;
        mMotionA = MotionA;
        mGyroA = GyroA;
        mLightA = LightA;
        mSoundA = SoundA;
        mAccV = AccV;
        mMotionV = MotionV;
        mGyroV = GyroV;
        mLightV = LightV;
        mSoundV = SoundV;
        mpackageName = packageName;
        museTime = useTime;

    }

    public String getmSleepID() {
        return mSleepID;
    }

    public String getmSleepDate() {
        return mSleepDate;
    }

    public String getmSleepTimes() {
        return mSleepTimes;
    }

    public String getmSleepScore() {
        return mSleepScore;
    }

    public String getmSleepDuration() {
        return mSleepDuration;
    }

    public List<String> getMpackageName() {
        return mpackageName;
    }

    public List<String> getMuseTime() {
        return museTime;
    }

    public String getmAccAverage() {
        return mAccAverage;
    }

    public String getmMotionAverage() {
        return mMotionAverage;
    }

    public String getmGyroAverage() {
        return mGyroAverage;
    }

    public String getmLightaverage() {
        return mLightaverage;
    }

    public String getmSoundAverage() {
        return mSoundAverage;
    }

    public String getmAccAV() {
        return mAccAV;
    }

    public String getmMotionAV() {
        return mMotionAV;
    }

    public String getmGryoAV() {
        return mGryoAV;
    }

    public String getmLightAV() {
        return mLightAV;
    }

    public String getmSoundAV() {
        return mSoundAV;
    }

    public List<Float> getmAccA() {
        return mAccA;
    }

    public List<Float> getmMotionA() {
        return mMotionA;
    }

    public List<Float> getmGyroA() {
        return mGyroA;
    }

    public List<Float> getmLightA() {
        return mLightA;
    }

    public List<Float> getmAccV() {
        return mAccV;
    }

    public List<Float> getmMotionV() {
        return mMotionV;
    }

    public List<Float> getmGyroV() {
        return mGyroV;
    }

    public List<Float> getmLightV() {
        return mLightV;
    }

    public List<Double> getmSoundA() {
        return mSoundA;
    }

    public List<Double> getmSoundV() {
        return mSoundV;
    }

    public ArrayList getAll(){
        ArrayList ar = new ArrayList();
        Object[] objs = {mSleepID, mSleepDate, mSleepTimes, mSleepScore, mSleepDuration, mAccAverage, mMotionAverage, mGyroAverage, mLightaverage, mSoundAverage, mAccAV, mMotionAV, mGryoAV, mLightAV, mSoundAV, mAccA, mMotionA, mGyroA, mLightA, mSoundA, mAccV, mMotionV, mGyroV, mLightV, mSoundV};
        for(Object obj:objs){
            ar.add(obj);
        }
        return ar;
    }
}
