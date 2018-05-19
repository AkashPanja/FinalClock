package io.github.akashpanja.finalclock.Model.Live;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Akash on 30-04-2018.
 */

public class Clock extends ViewModel {
    MutableLiveData<String> hour = new MutableLiveData<>();
    MutableLiveData<String> minutes = new MutableLiveData<>();
    MutableLiveData<String> dayOfWeek = new MutableLiveData<>();
    MutableLiveData<String> dayOfMonth = new MutableLiveData<>();
    MutableLiveData<String> month = new MutableLiveData<>();
    MutableLiveData<String> wish = new MutableLiveData<>();

    /* Format */
    SimpleDateFormat FullHour = new SimpleDateFormat("HH");

    SimpleDateFormat Hour = new SimpleDateFormat("hh");
    SimpleDateFormat Minutes = new SimpleDateFormat("mm");
    SimpleDateFormat DayOfMonth = new SimpleDateFormat("dd");
    SimpleDateFormat DayOfWeek = new SimpleDateFormat("EEEE");
    SimpleDateFormat Month = new SimpleDateFormat("MMM");

    Handler handler;

    public Clock() {
        Date date = new Date();
        handler = new Handler();
        int tempFulHour = Integer.parseInt(FullHour.format(date));

        hour.setValue(Hour.format(date));
        minutes.setValue(Minutes.format(date));
        dayOfMonth.setValue(DayOfMonth.format(date));
        dayOfWeek.setValue(DayOfWeek.format(date));
        month.setValue(Month.format(date));

        wish.setValue("");

        handler.post(new Runnable() {
            @Override
            public void run() {

                Date current = new Date();
                int tempFulHour = Integer.parseInt(FullHour.format(current));

                /* Hour */
                if (!hour.getValue().equals(Hour.format(current))) {
                    hour.setValue(String.valueOf(Hour.format(current)));
                }

                /* Minutes */

                if (!minutes.getValue().equals(Minutes.format(current))) {
                    minutes.setValue(String.valueOf(Minutes.format(current)));
                }

                /* Day Of Month */

                if (!dayOfMonth.getValue().equals(DayOfMonth.format(current))) {
                    dayOfMonth.setValue(DayOfMonth.format(current));
                }

                /* Day of Week */

                if (!dayOfWeek.getValue().equals(DayOfWeek.format(current))) {
                    dayOfWeek.setValue(DayOfWeek.format(current));
                }

                /* Month of Year */

                if (!month.getValue().equals(Month.format(current))) {
                    month.setValue(Month.format(current));
                }

                setGreeting(tempFulHour);

                handler.postDelayed(this, 10);
            }
        });
    }

    public void setGreeting(int tempFulHour)
    {
        if (tempFulHour >= 4 && tempFulHour < 12) {
            if (!wish.getValue().equals("Good Morning")) {
                wish.setValue("Good Morning");
            }
        } else if (tempFulHour >= 12 && tempFulHour < 17) {
            if (!wish.getValue().equals("Good Afternoon")) {
                wish.setValue("Good Afternoon");
            }
        } else if (tempFulHour >= 17 && tempFulHour < 20) {
            if (!wish.getValue().equals("Good Evening")) {
                wish.setValue("Good Evening");
            }
        } else if (tempFulHour >= 20 || tempFulHour < 5) {
            if (!wish.getValue().equals("Good Night")) {
                wish.setValue("Good Night");
            }
        }
    }

    public MutableLiveData<String> getHour() {
        return hour;
    }

    public MutableLiveData<String> getMinutes() {
        return minutes;
    }

    public MutableLiveData<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public MutableLiveData<String> getDayOfMonth() {
        return dayOfMonth;
    }

    public MutableLiveData<String> getMonth() {
        return month;
    }

    public MutableLiveData<String> getWish() {
        return wish;
    }
}
