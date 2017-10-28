package rit.com.agent007.load_screen;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import rit.com.agent007.R;
import rit.com.agent007.SharedAppToolbox;
import rit.com.agent007.loginActivity;

/**
 * Created by Stefan on 28/10/17.
 */

public class animatorLoadScreen extends SharedAppToolbox {

    private static int GLOBAL_ANIM_TIME = 2000;
    private static int PULSE_ANIM_TIME = 500;


    // Animate alpha background from white to black
    private void animateBackground(){
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.BLACK);


        final LinearLayout linLayout = getLinearLayoutById(R.id.loadScreen_LinearLayout_main);

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

    private void animateImgMove()
    {
        final ImageView imgView = getImgViewById(R.id.loadScreen_ImageView_main);

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

    // Pulse animation for imageView
    private void animateImgPulse(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(PULSE_ANIM_TIME);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        // Remove -1 and make it odd so it doesn't remain 0 (invisible)
        alphaAnimation.setRepeatCount(GLOBAL_ANIM_TIME/PULSE_ANIM_TIME - 1);

        final ImageView imgView = (ImageView)findViewById(R.id.loadScreen_ImageView_main);

        imgView.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                callNextActivity(animatorLoadScreen.this, loginActivity.class, new String[]{
                        Integer.toString(R.layout.activity_login)
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    // animate image move, fillAfter true to prevent image blink, move by 110DP
    // (50DP is half image height and 60DP is padding on 2nd activity)


    @Override
    protected void onStart() {
        super.onStart();

        animateBackground();
    }
}
