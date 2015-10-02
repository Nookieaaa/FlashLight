package com.nookdev.flashlight;


import android.content.Context;
import android.content.SharedPreferences;

public class Preferences  {
    private SharedPreferences sharedPreferences;
    private static Preferences instance = new Preferences();
    public static final String PREFERENCES_NAME = "PigLighter";
    public static final String SOUND = "sound";
    public static final String VIBRATE = "vibrate";

    private Preferences(){
        this.sharedPreferences = LighterApp.getAppContext().getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
    }

    public static Preferences getInstance(){
        return instance;
    }

    public void setSoundOn(boolean value){
        SharedPreferences.Editor ed = getEditor();
        ed.putBoolean(SOUND,value);
        ed.commit();
    }

    public boolean getSoundState(){
        if (sharedPreferences!=null)
            return sharedPreferences.getBoolean(SOUND,false);
        return false;
    }

    private SharedPreferences.Editor getEditor(){
        if (sharedPreferences!=null)
            return sharedPreferences.edit();
        return null;

    }

}
