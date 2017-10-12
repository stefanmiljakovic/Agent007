package rit.com.agent007;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class loadActivity extends AppCompatActivity {


//    Remove title bar, add full screen support
    protected void runFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//    Hide action bar
    protected void hideBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

//    get ImageView, then add resource, return ImageView object
    protected ImageView setImage(){
        ImageView imgView = (ImageView)findViewById(R.id.imgView);
        imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));

        return imgView;
    }

//   make typeface, return typeface

    protected Typeface getTypeFace() {
        return Typeface.createFromAsset(getAssets(), "good_times.ttf");
    }

//    get editText
//    check
    protected EditText getEditTextById(int id){
        return (EditText)findViewById(id);
    }
    protected TextView getTextViewById(int id) { return (TextView)findViewById(id); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runFullScreen();
        setContentView(R.layout.activity_load);
        hideBar();

        EditText editText1 = getEditTextById(R.id.editText1);
        EditText editText2 = getEditTextById(R.id.editText2);

        TextView textViewTop = getTextViewById(R.id.textViewTop);
        TextView textViewFirstName = getTextViewById(R.id.textViewFirstName);
        TextView textViewLastName = getTextViewById(R.id.textViewLastName);

        editText1.setTypeface(getTypeFace());
        editText2.setTypeface(getTypeFace());

        textViewTop.setTypeface(getTypeFace());
        textViewFirstName.setTypeface(getTypeFace());
        textViewLastName.setTypeface(getTypeFace());

        ImageView imgView = setImage();

    }



    @Override
    protected void onStart(){
        super.onStart();
    }


}
