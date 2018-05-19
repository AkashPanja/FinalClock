package io.github.akashpanja.finalclock.Activity.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import io.github.akashpanja.finalclock.Model.Live.Clock;
import io.github.akashpanja.finalclock.R;

public class MainActivity extends AppCompatActivity {

    TextView HourView, MinutesView, DayOfWeekView, MonthView, DateOfMonthView, WishOfTheDayView;
    ImageView logo;
    TextToSpeech textToSpeech;
    MediaPlayer mediaPlayer = new MediaPlayer();

    private Observer<String> Hour = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {

            textToSpeech.speak("Right Now It's " + s + " O Clock", TextToSpeech.QUEUE_ADD, null);


            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hourtune);
            mediaPlayer.start();

            HourView.setText(s);
        }
    };

    private Observer<String> Minutes = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            MinutesView.setText(s);
            if (s.equals("30")) {
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.release();


                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pikachu);
                mediaPlayer.start();
            }
        }
    };

    private Observer<String> DayOfWeek = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            DayOfWeekView.setText(s);
        }
    };

    private Observer<String> Month = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            MonthView.setText(s);
        }
    };

    private Observer<String> DateOfMonth = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            DateOfMonthView.setText(s);
        }
    };

    private Observer<String> WishOfTheDay = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            WishOfTheDayView.setText(s);
            textToSpeech.speak(s + " Everyone", TextToSpeech.QUEUE_ADD, null);
        }
    };

    Clock clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/product_sans_regular.ttf");

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setPitch((float) 1.5);
                    textToSpeech.setSpeechRate((float) 1.0);
                }
            }
        });

        clock = ViewModelProviders.of(this).get(Clock.class);

        HourView = (TextView) findViewById(R.id.Hour);
        MinutesView = (TextView) findViewById(R.id.Minutes);
        DayOfWeekView = (TextView) findViewById(R.id.DayOfWeek);
        DateOfMonthView = (TextView) findViewById(R.id.DateOfMonth);
        MonthView = (TextView) findViewById(R.id.Month);
        WishOfTheDayView = (TextView) findViewById(R.id.WishOfTheDay);
        logo = (ImageView) findViewById(R.id.logo);

        logo.post(new Runnable() {
            @Override
            public void run() {

                TranslateAnimation anim = new TranslateAnimation(-logo.getMeasuredWidth(), getWindow().getDecorView().getMeasuredWidth(), 0, 0);
                anim.setRepeatCount(TranslateAnimation.INFINITE);
                anim.setRepeatMode(TranslateAnimation.RESTART);
                anim.setDuration(3500);

                Glide.with(getApplicationContext()).load(R.drawable.pikachu).animate(anim).into(logo);

            }
        });


        HourView.setTypeface(typeface);
        MinutesView.setTypeface(typeface);
        DateOfMonthView.setTypeface(typeface);
        DayOfWeekView.setTypeface(typeface);
        MonthView.setTypeface(typeface);
        WishOfTheDayView.setTypeface(typeface);

        /* Setting Observer */

        clock.getHour().observe(this, Hour);
        clock.getMinutes().observe(this, Minutes);
        clock.getDayOfWeek().observe(this, DayOfWeek);
        clock.getMonth().observe(this, Month);
        clock.getDayOfMonth().observe(this, DateOfMonth);
        clock.getWish().observe(this, WishOfTheDay);
    }
}
