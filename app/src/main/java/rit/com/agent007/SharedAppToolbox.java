package rit.com.agent007;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Stefan on 28/10/17.
 */

public abstract class SharedAppToolbox extends AppCompatActivity {


    protected void runFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Remove top bar
    protected void hideBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    protected EditText getEditTextById(int id){
        return (EditText)findViewById(id);
    }
    protected TextView getTextViewById(int id) { return (TextView)findViewById(id); }
    protected Button getButtonById(int id) {return (Button)findViewById(id);}
    protected ImageButton getImgButtonById(int id) {return (ImageButton)findViewById(id);}
    protected ImageView getImgViewById(int id) {return (ImageView)findViewById(id);}
    protected LinearLayout getLinearLayoutById(int id) { return (LinearLayout)findViewById(id);}

    protected Typeface getTypeFace() {
        return Typeface.createFromAsset(getAssets(), "good_times.ttf");
    }

    private void putExtras(String arg1, String arg2, Intent intent){

        intent.putExtra(arg1, arg2);
    }

    protected String getExtra(String arg){
        String extras;
        try{ extras = getIntent().getExtras().getString(arg); }
        catch (Exception e) { extras = null; }

        if(extras == null)
            extras = Integer.toString(R.layout.activity_load_screen);

        return extras;
    }
    protected void callNextActivity(Context fromActivity, Class toActivity, String [] args) {

        Intent intent = new Intent(fromActivity, toActivity);

        try{

        if(args.length % 2 == 1 && args.length > 1){

        for(int i = 1; i < args.length; i = i + 2){
            putExtras(args[i], args[i+1], intent);

        }}}catch (Exception e){}

        putExtras("R_LAYOUT", args[0],intent);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutID = Integer.parseInt(getExtra("R_LAYOUT"));

        runFullScreen();
        setContentView(layoutID);
        hideBar();
    }
}
