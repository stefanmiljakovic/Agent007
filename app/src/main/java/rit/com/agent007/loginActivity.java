package rit.com.agent007;

import android.os.Bundle;
import rit.com.agent007.login.scriptLoginActivity;

public class loginActivity extends scriptLoginActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getButtonById(R.id.login_Button_LogIn).setTypeface(getTypeFace());

        getEditTextById(R.id.login_EditText_FirstName).setTypeface(getTypeFace());
        getEditTextById(R.id.login_EditText_LastName).setTypeface(getTypeFace());

        getTextViewById(R.id.login_TextView_onTop).setTypeface(getTypeFace());
        getTextViewById(R.id.login_TextView_FirstName).setTypeface(getTypeFace());
        getTextViewById(R.id.login_TextView_LastName).setTypeface(getTypeFace());
    }

    @Override
    protected void onPause(){
        super.onPause();

        finish();
    }


}
