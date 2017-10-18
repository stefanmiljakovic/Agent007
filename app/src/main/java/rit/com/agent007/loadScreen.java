package rit.com.agent007;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class loadScreen extends AppCompatActivity {

    private static int GLOBAL_ANIM_TIME = 2000;
    private static int PULSE_ANIM_TIME = 500;

    // Run in full screen
    private void runFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Remove top bar
    private void hideBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    // Set Image to ImgView
    private ImageView setImage(){
        ImageView imgView = (ImageView)findViewById(R.id.imgView);
        imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));

        return imgView;
    }


    // Animate alpha background from white to black
    private void animateBackground(){
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.BLACK);


        final LinearLayout linLayout = (LinearLayout)findViewById(R.id.linLayout);
        valueAnimator.setDuration(GLOBAL_ANIM_TIME);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                linLayout.setBackgroundColor((Integer)animation.getAnimatedValue());
            }
        });

        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                linLayout.setBackgroundColor(Color.BLACK);

                animateImgMove();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    // Pulse animation for imageView
    private void animateImgPulse(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(PULSE_ANIM_TIME);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        // Remove -1 and make it odd so it doesn't remain 0 (invisible)
        alphaAnimation.setRepeatCount(GLOBAL_ANIM_TIME/PULSE_ANIM_TIME - 1);

        ImageView imgView = (ImageView)findViewById(R.id.imgView);

        imgView.startAnimation(alphaAnimation);


        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                callNextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    // animate image move, fillAfter true to prevent image blink, move by 110DP
    // (50DP is half image height and 60DP is padding on 2nd activity)

    private void animateImgMove()
    {
        final ImageView imgView = (ImageView)findViewById(R.id.imgView);

        final float DP = getResources().getDimensionPixelSize(R.dimen.DP);

        float moveBy = 110 * DP - getWindowManager().getDefaultDisplay().getHeight()/2;

        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, moveBy);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);

        imgView.startAnimation(translateAnimation);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animateImgPulse();
                // setY to 60DP to prevent flicker on 2nd activity call
                imgView.setY(60*DP);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void callNextActivity(){

        Intent intent = new Intent(loadScreen.this, loginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runFullScreen();
        setContentView(R.layout.activity_load_screen);
        hideBar();

        setImage();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        animateBackground();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        finish();
    }
}
