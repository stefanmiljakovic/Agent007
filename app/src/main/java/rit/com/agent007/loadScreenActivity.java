package rit.com.agent007;


import rit.com.agent007.load_screen.animatorLoadScreen;

public class loadScreenActivity extends animatorLoadScreen {


    @Override
    protected void onPause()
    {
        super.onPause();

        finish();
    }
}
