package com.nookdev.flashlight;


import android.content.Context;


public class Screen {
    private Context context;

    public Screen(Context context) {
        this.context = context;
    }

    public void cleanUp(){
        context = null;
    }

    public int getBackground(boolean isOn){
        if (!isOn)
            return R.drawable.pig_off;
        else
            return R.drawable.pig_on;
    }

}
