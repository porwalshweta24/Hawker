package app.com.hawker.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.com.hawker.Fragments.BillingFragment;
import app.com.hawker.Fragments.StopHistoryFragment;
import app.com.hawker.Fragments.SubscriptionFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {


	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch(i)
		{
		case 0:
			return new SubscriptionFragment();

		case 1:
			return new StopHistoryFragment();


		case 2:
			return  new BillingFragment();


		}
		return null;

	}

	@Override
	public int getCount() {
		return 3;
	}
}
