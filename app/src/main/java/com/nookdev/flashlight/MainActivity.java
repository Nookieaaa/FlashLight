package com.nookdev.flashlight;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Screen screen;
    private Light light;
    private Sound sound;

    @Bind(R.id.soundcontrol) ToggleButton soundControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        screen = new Screen(this);
        light = new Light(this);
        sound = Sound.getInstance();
        sound.playSound(Sound.SOUND_OINK);
        soundControl.setChecked(sound.getSoundState());
    }

    private void hideActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.layout)
    public void handleClick(RelativeLayout relativeLayout){
        light.switchLight();
        boolean isOn = light.isOn();
        sound.handleSwitch(isOn);

        Drawable image = getResources().getDrawable(screen.getBackground(isOn));
        relativeLayout.setBackgroundDrawable(image);
    }

    @OnClick(R.id.soundcontrol)
    public void handleSoundControl(ToggleButton control){
        sound.saveSoundState(control.isChecked());
    }


    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        light.cleanUp();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        light.cleanUp();
        sound.cleanUp();
        screen.cleanUp();
    }
}
