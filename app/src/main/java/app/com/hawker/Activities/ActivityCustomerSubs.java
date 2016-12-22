package app.com.hawker.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import app.com.hawker.Fragments.BillingFragment;
import app.com.hawker.Fragments.StopHistoryFragment;
import app.com.hawker.Fragments.SubscriptionFragment;
import app.com.hawker.R;
import app.com.hawker.adapter.ViewPagerAdapter;

public class ActivityCustomerSubs  extends AppCompatActivity  implements View.OnClickListener{

	private String[] tabs;
	FragmentTabHost tabHost;
	ViewPagerAdapter pagerAdapter;
	ViewPager viewPager;
	private TabWidget tabWidget;
	private HorizontalScrollView horizontalScrollView;


	TextView tv_title;
	ImageView iv_list_report;
	ImageView iv_logout;
	ImageView image_view_back;
	String cutomerId="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_aubscriptions);
			init();
			initHeader();
			onCall();
			initializeHorizontalTabs();
			initializeTabs();
			setupTabHost();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	void init()
	{
		viewPager = (ViewPager) findViewById(R.id.pager);
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);
		
		pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
	}
	
	void initHeader()
	{

		tv_title= (TextView) findViewById(R.id.tv_title);
		iv_list_report= (ImageView) findViewById(R.id.iv_list_report);
		iv_logout= (ImageView) findViewById(R.id.iv_logout);
		image_view_back= (ImageView) findViewById(R.id.iv_back);

		tv_title.setText("CUSTOMER DETAILS");
		iv_logout.setVisibility(View.GONE);
		iv_list_report.setVisibility(View.GONE);
		image_view_back.setVisibility(View.VISIBLE);
		image_view_back.setOnClickListener(this);
	}
	@SuppressWarnings("deprecation")
	void onCall()
	{
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	
			@Override
			public void onPageSelected(int position) {
				invalidateOptionsMenu();
				tabHost.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				invalidateOptionsMenu();
			}
		});

		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				viewPager.setCurrentItem(tabHost.getCurrentTab());
				scrollToCurrentTab();
			}
		});

	}
	private void initializeHorizontalTabs() {
		LinearLayout ll = (LinearLayout) tabWidget.getParent();
		horizontalScrollView = new HorizontalScrollView(this);
		horizontalScrollView.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT));
		ll.addView(horizontalScrollView, 0);
		ll.removeView(tabWidget);
		horizontalScrollView.addView(tabWidget);
		horizontalScrollView.setHorizontalScrollBarEnabled(false);
	}

	private void scrollToCurrentTab() {
		final int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		final int leftX = tabWidget.getChildAt(tabHost.getCurrentTab()).getLeft();
		int newX = 0;

		newX = leftX + (tabWidget.getChildAt(tabHost.getCurrentTab()).getWidth() / 2) - (screenWidth / 2);
		if (newX < 0) {
			newX = 0;
		}
		horizontalScrollView.scrollTo(newX, 0);
	}
	private void initializeTabs() {
		tabs = new String[] { "Subscription", "Stop History", "Billing" };
	}

	private void setupTabHost() {
		Bundle args = new Bundle();
       //args.getString("CUSTOMER_ID"
        tabHost.addTab(tabHost.newTabSpec("Subscription").
				setIndicator("Subscription"),
				SubscriptionFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("Stop History").
				setIndicator("Stop History"),
				StopHistoryFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("Billing").
				setIndicator("Billing"),
				BillingFragment.class, null);
		
	}
	@Override
	public void onClick(View v) {
		if(v==image_view_back){
			finish();
		}

	}

	
}
