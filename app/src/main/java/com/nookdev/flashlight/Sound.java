package com.nookdev.flashlight;


import android.content.Context;
import android.media.MediaPlayer;

public class Sound {
    private static Sound instance = new Sound();
    public static final int SOUND_OINK = R.raw.oink;
    public static final int SOUND_WOW = R.raw.wow;

    private MediaPlayer mp;
    private Context context;
    private boolean isSoundOn;
    private Preferences preferences;

    public static synchronized Sound getInstance(){
        return instance;
    }

 /*   public static boolean isOn(){
        if (instance==null){
            instance = new Sound();
        }
        return instance.isSoundOn
    }*/


    private Sound(){
        context = LighterApp.getAppContext();
        isSoundOn = true;
        preferences = Preferences.getInstance();
    }

    public void playSound(final int sound){
        if(!getSoundState())
            return;

        stopPlaying();
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                mp = MediaPlayer.create(context,sound);
                mp.start();
            }
        });
        t.start();

    }

    public void handleSwitch(boolean isOn){

        if (isOn)
            playSound(SOUND_WOW);
        else
            playSound(SOUND_OINK);
    }

    public void cleanUp(){
        stopPlaying();
        if (mp!=null){
            mp.release();
            mp=null;
        }
    }

    public void saveSoundState(boolean state){
        preferences.setSoundOn(state);
    }

    public boolean getSoundState(){
        return preferences.getSoundState();
    }

    private void stopPlaying(){
        if(mp!=null)
            if (mp.isPlaying())
                mp.stop();
    }

}
