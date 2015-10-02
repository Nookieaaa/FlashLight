package com.nookdev.flashlight;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class Light {
    private Camera camera;
    private Camera.Parameters parameters;
    private MainActivity mainActivity;
    private boolean isFlashEnabled;
    private boolean isOn;
    private Preferences preferences;

    public Light(MainActivity activity) {
        mainActivity = activity;
        isFlashEnabled = mainActivity.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        isOn = false;
        preferences = Preferences.getInstance();

        if (!initCamera()){
            showErrorDialog();
        }
    }

    public void switchLight(){
        isOn = !isOn;
        light(isOn);

    }

    private void showErrorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(mainActivity.getString(R.string.error));
        builder.setMessage(mainActivity.getString(R.string.error_camera_not_suported));
        builder.setCancelable(false);
        builder.setPositiveButton(mainActivity.getString(R.string.Ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void light(boolean state){

        if (!isFlashEnabled){
            showErrorDialog();
            return;
        }


        if (state) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
        }
    }
    private boolean initCamera() {
        if (!isFlashEnabled){
            camera = null;
            parameters = null;
            return false;
        }
        try {
            if (camera == null) {
                camera = Camera.open();
                parameters = camera.getParameters();
            }
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }
        return true;
    }

    public void cleanUp(){
        if (camera!=null)
            camera.release();
        camera = null;
        parameters = null;
    }

    public boolean isOn(){
        return isOn;
    }

    public boolean isFlashEnabled(){
        return isFlashEnabled;
    }

    public void setLightParam(boolean param){

    }

}
