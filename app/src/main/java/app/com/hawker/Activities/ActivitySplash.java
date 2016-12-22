package app.com.hawker.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import app.com.hawker.Models.MySharedpreference;
import app.com.hawker.R;
import app.com.hawker.dao.Hawker;


/**
 * Created by admin on 8/6/2016.
 */
public class ActivitySplash extends AppCompatActivity{

    private MySharedpreference mySharedpreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mySharedpreference = new MySharedpreference(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	

               if(mySharedpreference.getHawkerObj()!=null) {
                	
                    String json = mySharedpreference.getHawkerObj();
                    ((ApplicationClass) ActivitySplash.this.getApplicationContext()).
                    setHawker(new Gson().fromJson(json, Hawker.class));
                    Intent mainIntent = new Intent(ActivitySplash.this, ActivityDashBoard.class);
                    startActivity(mainIntent);
                    finish();
                }else
                {
                	Intent browserIntent = new Intent(ActivitySplash.this, ActivityLogIn.class);
                    startActivity(browserIntent);
                    finish();
                }

                
            }
        }, 1000);
    }

}
