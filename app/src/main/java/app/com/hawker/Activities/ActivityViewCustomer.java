package app.com.hawker.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.Customer;

/**
 * Created by shweta on 11/5/2016.
 */

public class ActivityViewCustomer extends AppCompatActivity {
    Button button_save_cust;
    EditText tv_h_seq, cust_code, name, mobile_num, t_due, City, State, comments,
            initials, old_h_no, new_h_no, add1, add2, locality, profile3, street;
    Customer customer;
    private Activity activity;
    String employmentStr="",profile1Str="",profile2Str="",stateStr="";
    TextView tv_title;
    Spinner profile1, profile2,employment,spinnerState;
    ImageView iv_list_report;
    ImageView iv_logout;
    ImageView iv_back;
    String CustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_edit_customer);

            Intent i = getIntent();
            customer = (Customer) i.getSerializableExtra("CUSTOMER");
            init();
            setData();
            onClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void init() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_list_report = (ImageView) findViewById(R.id.iv_list_report);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        button_save_cust = (Button) findViewById(R.id.button_save_cust);
        button_save_cust.setText("OK");
        tv_h_seq = (EditText) findViewById(R.id.tv_h_seq);
        tv_h_seq.setEnabled(false);
        tv_h_seq.setFocusable(false);
        tv_h_seq.setClickable(false);
        cust_code = (EditText) findViewById(R.id.cust_code);
        cust_code.setEnabled(false);
        cust_code.setFocusable(false);
        cust_code.setClickable(false);
        name = (EditText) findViewById(R.id.name);
        name.setEnabled(false);
        name.setFocusable(false);
        name.setClickable(false);
        mobile_num = (EditText) findViewById(R.id.mobile_num);
        mobile_num.setEnabled(false);
        mobile_num.setFocusable(false);
        mobile_num.setClickable(false);
        t_due = (EditText) findViewById(R.id.t_due);
        t_due.setEnabled(false);
        t_due.setFocusable(false);
        t_due.setClickable(false);
        street = (EditText) findViewById(R.id.street);
        street.setEnabled(false);
        street.setFocusable(false);
        street.setClickable(false);
        initials = (EditText) findViewById(R.id.initials);
        initials.setEnabled(false);
        initials.setFocusable(false);
        initials.setClickable(false);
        old_h_no = (EditText) findViewById(R.id.old_h_no);
        old_h_no.setEnabled(false);
        old_h_no.setFocusable(false);
        old_h_no.setClickable(false);
        new_h_no = (EditText) findViewById(R.id.new_h_no);
        new_h_no.setEnabled(false);
        new_h_no.setFocusable(false);
        new_h_no.setClickable(false);
        add1 = (EditText) findViewById(R.id.add1);
        add1.setEnabled(false);
        add1.setFocusable(false);
        add1.setClickable(false);
        add2 = (EditText) findViewById(R.id.add2);
        add2.setEnabled(false);
        add2.setFocusable(false);
        add2.setClickable(false);
        profile1 = (Spinner) findViewById(R.id.spinnerprofile1);
        profile1.setEnabled(false);
        locality = (EditText) findViewById(R.id.locality);
        locality.setEnabled(false);
        locality.setFocusable(false);
        locality.setClickable(false);
        profile2 = (Spinner) findViewById(R.id.spinnerprofile2);
        profile2.setEnabled(false);
        profile3 = (EditText) findViewById(R.id.profile3);
        profile3.setEnabled(false);
        City = (EditText) findViewById(R.id.City);
        City.setEnabled(false);
        City.setFocusable(false);
        City.setClickable(false);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        spinnerState.setEnabled(false);
        spinnerState.setFocusable(false);
        spinnerState.setClickable(false);
        comments = (EditText) findViewById(R.id.comments);
        comments.setEnabled(false);
        comments.setFocusable(false);
        comments.setClickable(false);
        employment = (Spinner) findViewById(R.id.spinneremployment);
        employment.setEnabled(false);


    }

    void setData() {
        try {
            tv_title.setText("View customers");
            iv_logout.setVisibility(View.GONE);
            iv_list_report.setVisibility(View.GONE);
            iv_back.setVisibility(View.VISIBLE);

            tv_h_seq.setText("" + customer.getHouseSeq().split("\\.")[0]);
            cust_code.setText("" + customer.getCustomerCode().split("\\.")[0]);
            name.setText("" + customer.getName());
            mobile_num.setText("" + customer.getMobileNum());
            street.setText("" + customer.getBuildingStreet());
            initials.setText("" + customer.getInitials());
            old_h_no.setText("" + customer.getOldHouseNum());
            new_h_no.setText("" + customer.getNewHouseNum());
            add1.setText("" + customer.getAddrLine1());
            add2.setText("" + customer.getAddrLine2());
            locality.setText("" + customer.getLocality());

            profile3.setText("" + customer.getProfile3());

            City.setText("" + customer.getCity());
            comments.setText("" + customer.getComments());

            t_due.setText("" + customer.getTotalDue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void onClick() {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                ((ApplicationClass) getApplicationContext()).getEmploymentValuesArr());
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        employment.setAdapter(dataAdapter);
        employment.setSelection(dataAdapter.getPosition(customer.getEmployment()));
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterProfile1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                ((ApplicationClass) getApplicationContext()).getProfileValuesArr());
        // Drop down layout style - list view with radio button
        dataAdapterProfile1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        profile1.setAdapter(dataAdapterProfile1);
        profile1.setSelection(dataAdapterProfile1.getPosition(customer.getProfile1()));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterProfile2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                ((ApplicationClass) getApplicationContext()).getProfileValuesArr());
        // Drop down layout style - list view with radio button
        dataAdapterProfile2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        profile2.setAdapter(dataAdapterProfile2);
        profile2.setSelection(dataAdapterProfile2.getPosition(customer.getProfile2()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.state_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);
        spinnerState.setSelection(adapter.getPosition(customer.getState()));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        button_save_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


    }


}
