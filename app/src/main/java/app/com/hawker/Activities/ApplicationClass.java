package app.com.hawker.Activities;

import android.app.Application;

import java.util.ArrayList;

import app.com.hawker.dao.Billing;
import app.com.hawker.dao.BillingLine;
import app.com.hawker.dao.Customer;
import app.com.hawker.dao.Hawker;
import app.com.hawker.dao.LineInfo;
import app.com.hawker.dao.StopHistory;
import app.com.hawker.dao.Subscription;

/**
 * Created by admin on 8/13/2016.
 */
public class ApplicationClass extends Application {
    Hawker hawker;
    
    ArrayList<StopHistory> stopHistoryArrayList;
    ArrayList<LineInfo> lineInfoArrayList;
    ArrayList<Customer> customerArrayList;
    ArrayList<Billing> billingsArrayList;
    ArrayList<BillingLine> billingLinesArrayList;
    ArrayList<Subscription> subscriptionArrayList;
	ArrayList<String> paymentValuesArr,employmentValuesArr,profileValuesArr,durationValuesArr;
	boolean isBackActivity=false;
	boolean isNewCustomer=false;
	public ArrayList<String> getPaymentValuesArr() {
		return paymentValuesArr;
	}

	public boolean isNewCustomer() {
		return isNewCustomer;
	}

	public void setNewCustomer(boolean newCustomer) {
		isNewCustomer = newCustomer;
	}

	public boolean isBackActivity() {
		return isBackActivity;
	}

	public void setBackActivity(boolean backActivity) {
		isBackActivity = backActivity;
	}

	public void setPaymentValuesArr(ArrayList<String> paymentValuesArr) {
		this.paymentValuesArr = paymentValuesArr;
	}

	public ArrayList<String> getEmploymentValuesArr() {
		return employmentValuesArr;
	}

	public void setEmploymentValuesArr(ArrayList<String> employmentValuesArr) {
		this.employmentValuesArr = employmentValuesArr;
	}

	public ArrayList<String> getProfileValuesArr() {
		return profileValuesArr;
	}

	public void setProfileValuesArr(ArrayList<String> profileValuesArr) {
		this.profileValuesArr = profileValuesArr;
	}

	public ArrayList<String> getDurationValuesArr() {
		return durationValuesArr;
	}

	public void setDurationValuesArr(ArrayList<String> durationValuesArr) {
		this.durationValuesArr = durationValuesArr;
	}

	public ArrayList<Subscription> getSubscriptionArrayList() {
		return subscriptionArrayList;
	}

	public void setSubscriptionArrayList(
			ArrayList<Subscription> subscriptionArrayList) {
		this.subscriptionArrayList = subscriptionArrayList;
	}

	public ArrayList<BillingLine> getBillingLinesArrayList() {
		return billingLinesArrayList;
	}

	public void setBillingLinesArrayList(
			ArrayList<BillingLine> billingLinesArrayList) {
		this.billingLinesArrayList = billingLinesArrayList;
	}

	public ArrayList<Billing> getBillingsArrayList() {
		return billingsArrayList;
	}

	public void setBillingsArrayList(ArrayList<Billing> billingsArrayList) {
		this.billingsArrayList = billingsArrayList;
	}

	public ArrayList<StopHistory> getStopHistoryArrayList() {
		return stopHistoryArrayList;
	}

	public void setStopHistoryArrayList(ArrayList<StopHistory> stopHistoryArrayList) {
		this.stopHistoryArrayList = stopHistoryArrayList;
	}

	public Hawker getHawker() {
        return hawker;
    }

    public void setHawker(Hawker hawker) {
        this.hawker = hawker;
    }

    public ArrayList<LineInfo> getLineInfoArrayList() {
        return lineInfoArrayList;
    }

    public void setLineInfoArrayList(ArrayList<LineInfo> lineInfoArrayList) {
        this.lineInfoArrayList = lineInfoArrayList;
    }

	public ArrayList<Customer> getCustomerArrayList() {
		return customerArrayList;
	}

	public void setCustomerArrayList(ArrayList<Customer> customerArrayList) {
		this.customerArrayList = customerArrayList;
	}


}
