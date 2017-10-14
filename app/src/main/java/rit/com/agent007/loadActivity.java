package rit.com.agent007;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

//    get editText, text view, button, imagebutton
//    check
    protected EditText getEditTextById(int id){
        return (EditText)findViewById(id);
    }
    protected TextView getTextViewById(int id) { return (TextView)findViewById(id); }
    protected Button getButtonById(int id) {return (Button)findViewById(id);}
    protected ImageButton getImgButtonById(int id) {return (ImageButton)findViewById(id);}

// Load button click script
    protected void loadButtonClickScript(Button btn) {
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {

            }
        });
    }

    protected void loadButtonClearScript(ImageButton btn, int edtId){
        final int id = edtId;

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getEditTextById(id).setText("");
            }
        });
    }
    protected void loadEditTextScript(EditText edt, int btnId) {
        final int id = btnId;

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 0)
                    getImgButtonById(id).setVisibility(ImageButton.GONE);
                else
                    getImgButtonById(id).setVisibility(ImageButton.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


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

        getButtonById(R.id.buttonLogIn).setTypeface(getTypeFace());

        setImage();

        loadButtonClearScript(getImgButtonById(R.id.buttonClear1), R.id.editText1);
        loadButtonClearScript(getImgButtonById(R.id.buttonClear2), R.id.editText2);

        loadEditTextScript(editText1, R.id.buttonClear1);
        loadEditTextScript(editText2, R.id.buttonClear2);

    }



    @Override
    protected void onStart(){
        super.onStart();
    }


}
