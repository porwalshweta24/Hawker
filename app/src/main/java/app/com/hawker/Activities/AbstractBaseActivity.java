package app.com.hawker.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by admin on 1/29/2016.
 */
public abstract class AbstractBaseActivity extends AppCompatActivity  {
    public ArrayList<String> paymentValuesArr,employmentValuesArr,profileValuesArr,durationValuesArr;


    protected boolean isStopDefaultAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResource());

    }

    protected abstract int getLayoutResource();



    public void replaceFragment(Fragment fragment, boolean addToBackStack, int resourceId) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      /*  if (addToBackStack) {
            onBack(fragment);
            Log.d("AbstractBase","onBack Below");
            if (fragment instanceof ThankYou){

                Log.d("AbstractBase","Thank you");

            }
            else transaction.addToBackStack(null);
        }else{
            leftHeaderBtn.setImageResource(R.drawable.menu);
            headerback.setText(" ");
            if (fragment instanceof BaseFragment)
                leftBtnRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       onHeaderLeftButtonPress();
                    }
                });
        }*/

        transaction.replace(resourceId, fragment);
        transaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (!isStopDefaultAnimation)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        else overridePendingTransition(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isStopDefaultAnimation)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
