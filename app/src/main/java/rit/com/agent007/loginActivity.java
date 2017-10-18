package rit.com.agent007;

import android.content.Intent;
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
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {



//    Remove title bar, add full screen support
    private void runFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//    Hide action bar
    private void hideBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

//    get ImageView, then add resource, return ImageView object
    private ImageView setImage(){
        ImageView imgView = (ImageView)findViewById(R.id.imgView);
        imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));

        return imgView;
    }

//   make typeface, return typeface

    private Typeface getTypeFace() {
        return Typeface.createFromAsset(getAssets(), "good_times.ttf");
    }

//    get editText, text view, button, imagebutton
//    check
    private EditText getEditTextById(int id){
        return (EditText)findViewById(id);
    }
    private TextView getTextViewById(int id) { return (TextView)findViewById(id); }
    private Button getButtonById(int id) {return (Button)findViewById(id);}
    private ImageButton getImgButtonById(int id) {return (ImageButton)findViewById(id);}

// Load button click script
    private void loadButtonClickScript(Button btn) {

        final EditText editTextFirstName = getEditTextById(R.id.editText1);
        final EditText editTextLastName = getEditTextById(R.id.editText2);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();

                if(firstName.isEmpty() || firstName == null || lastName.isEmpty() || lastName == null)
                    showToastMessage();
                else{
                    Intent intent = new Intent(loginActivity.this, mainActivity.class);
                    intent.putExtra("NAME_SURNAME", firstName + " " +
                            lastName.charAt(0) + ".");
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }

    private void showToastMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT);
        toast.show();

    }

    private void loadButtonClearScript(ImageButton btn, int edtId){
        final int id = edtId;

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getEditTextById(id).setText("");
            }
        });
    }
    private void loadEditTextScript(EditText edt, int btnId) {
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
        setContentView(R.layout.activity_login);
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

        loadButtonClickScript(getButtonById(R.id.buttonLogIn));
    }



    @Override
    protected void onPause(){
        super.onPause();

        finish();
    }


}
