package keown.cantrell.artgallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        lottieAnimationView = findViewById(R.id.lottie);

        lottieAnimationView.animate().translationY(2000).setDuration(50000000).setStartDelay(2000000000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(keown.cantrell.artgallery.SplashScreen.this,
                        LoginActivity.class )
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) );
                finish();
            }
        },1100);
    }

}