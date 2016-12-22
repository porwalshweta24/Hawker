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
import android.widget.LinearLayout;
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
import java.util.List;

import app.com.hawker.R;
import app.com.hawker.BuildConfig;
import app.com.hawker.Utils.Utils;
import app.com.hawker.dao.Customer;
import app.com.hawker.dao.Hawker;

/**
 * Created by shweta on 11/5/2016.
 */

public class ActivityAddCustomer extends AppCompatActivity {
    Button button_save_cust;
    EditText tv_h_seq, cust_code, name, mobile_num, t_due,  City,  comments,
            initials, old_h_no, new_h_no, add1, add2,  locality, profile3,street;
    Customer customer;
    LinearLayout linearCustCode;
    private Activity activity;
    String employmentStr="",profile1Str="",profile2Str="",stateStr="";
    int employmentInt=0,profile1Int=0,profile2Int=0,stateInt=0;
    TextView tv_title;
    Spinner profile1, profile2,employment,spinnerState;
    ImageView iv_list_report;
    ImageView iv_logout;
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_edit_customer);
            customer = (Customer)  getIntent().getSerializableExtra("CUSTOMER");

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
        tv_h_seq = (EditText) findViewById(R.id.tv_h_seq);

        cust_code = (EditText) findViewById(R.id.cust_code);
        cust_code.setEnabled(false);

        name = (EditText) findViewById(R.id.name);
        mobile_num = (EditText) findViewById(R.id.mobile_num);
        t_due = (EditText) findViewById(R.id.t_due);
        street= (EditText) findViewById(R.id.street);

        initials = (EditText) findViewById(R.id.initials);
        old_h_no = (EditText) findViewById(R.id.old_h_no);
        new_h_no = (EditText) findViewById(R.id.new_h_no);
        add1 = (EditText) findViewById(R.id.add1);
        add2 = (EditText) findViewById(R.id.add2);
        profile1 = (Spinner) findViewById(R.id.spinnerprofile1);
        locality = (EditText) findViewById(R.id.locality);
        profile2 = (Spinner) findViewById(R.id.spinnerprofile2);
        profile3 = (EditText) findViewById(R.id.profile3);

        City = (EditText) findViewById(R.id.City);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        comments = (EditText) findViewById(R.id.comments);
        employment = (Spinner) findViewById(R.id.spinneremployment);
        t_due.setText("0.0");
        linearCustCode=(LinearLayout)findViewById(R.id.linearCustCode);
        linearCustCode.setVisibility(View.GONE);
        try{
            ArrayList<Customer>  customerArrayList = new ArrayList<Customer>(
                    ((ApplicationClass) this.getApplicationContext()).getCustomerArrayList());
            double h_seq=Double.parseDouble(customerArrayList.get(customerArrayList.size()-1).getHouseSeq());
            tv_h_seq.setText(""+String.valueOf((h_seq+1)).split("\\.")[0]);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void setData() {
        try {
            tv_title.setText("Add New Customers");
            iv_logout.setVisibility(View.GONE);
            iv_list_report.setVisibility(View.GONE);
            iv_back.setVisibility(View.VISIBLE);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    void onClick() {

        ArrayList<String> employeeArr=((ApplicationClass) getApplicationContext()).getEmploymentValuesArr();
        employeeArr.add(0,"Select employee");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                employeeArr);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        employment.setAdapter(dataAdapter);
        ArrayList<String> profileArrd=((ApplicationClass) getApplicationContext()).getProfileValuesArr();
        ArrayList<String> profileArr=new ArrayList<>();
        profileArr.add(0,"Select profile 1 type");
        profileArr.addAll(profileArrd);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterProfile1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                profileArr);
        // Drop down layout style - list view with radio button
        dataAdapterProfile1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        profile1.setAdapter(dataAdapterProfile1);
        ArrayList<String> profileArr2=new ArrayList<>();
        profileArr2.add(0,"Select profile 2 type");
        profileArr2.addAll(profileArrd);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterProfile2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                profileArr2);
        // Drop down layout style - list view with radio button
        dataAdapterProfile2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        profile2.setAdapter(dataAdapterProfile2);

        // Initialize and set Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.state_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);
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
                callAllFragments();

            }
        });
        profile1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                profile1Str  = profile1.getSelectedItem().toString();
                employmentInt=arg2;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        profile2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                profile2Str  = profile2.getSelectedItem().toString();
                profile2Int=arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        employment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                employmentInt=arg2;
                employmentStr  = employment.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                stateStr  = spinnerState.getSelectedItem().toString();
                stateInt=arg2;

            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
boolean isValidate()
{
 /*   if(stateInt==0){
        Utils.showToast(this,"Please select state.");
        return false;}
    if(employmentInt==0){
        Utils.showToast(this,"Please select employment type.");
        return false;}
    if(profile2Int==0){
        Utils.showToast(this,"Please select profile2 type.");
        return false;}
    if(profile1Int==0){
        Utils.showToast(this,"Please select profile1 type.");
        return false;}*/
    return true;
}
    void callAllFragments() {
        try
    {
        if(isValidate()) {
            customer.setHouseSeq(tv_h_seq.getText().toString());
            customer.setCustomerCode(cust_code.getText().toString());
            customer.setName(name.getText().toString());
            customer.setMobileNum(mobile_num.getText().toString());
            customer.setTotalDue(t_due.getText().toString());
            customer.setNewHouseNum(new_h_no.getText().toString());
            customer.setOldHouseNum(old_h_no.getText().toString());
            customer.setAddrLine1(add1.getText().toString());
            customer.setAddrLine2(add2.getText().toString());
            customer.setCity(City.getText().toString());

            customer.setComments(comments.getText().toString());
            customer.setInitials(initials.getText().toString());
            customer.setLocality(locality.getText().toString());
            customer.setProfile1(profile1Int==0?"": profile1Str);
            customer.setProfile2(profile2Int==0?"": profile2Str);
            customer.setState(stateInt==0?"": stateStr);
            customer.setEmployment(employmentInt==0?"": employmentStr);
            customer.setProfile3(profile3.getText().toString());
            customer.setBuildingStreet(street.getText().toString());

            new SendJsonValue().execute();
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class SendJsonValue extends AsyncTask<String, Void, String> {
        ProgressDialog mProgressDialog;

        public void closeDialog() {
            if (mProgressDialog != null)
                mProgressDialog.dismiss();
        }

        public void showDialog(Context mContex) {
            if (Utils.isOnline(mContex)) {
                try {
                    mProgressDialog = new ProgressDialog(mContex);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Utils.showAlertBox(mContex, "No Internet Connection", null);
            }
        }

        @Override

        protected void onPreExecute() {
            showDialog(ActivityAddCustomer.this);
        }

        protected String doInBackground(String... params) {
            String Result = "";

            try {

                Result = AddCustomerFunction();
            } catch (Exception e) {
                closeDialog();
            }
            return Result;
        }

        protected void onPostExecute(String Result) {
            closeDialog();
           /* ApplicationClass globalVariable = (ApplicationClass) getApplicationContext();
            globalVariable.setNewCustomer(true);
            System.out.println(Result);
            finish();*/
            ApplicationClass globalVariable = (ApplicationClass) getApplicationContext();
            globalVariable.setBackActivity(true);
            System.out.println(Result);
            Intent browserIntent = new Intent(ActivityAddCustomer.this, Activity_ListInfo.class);
            startActivity(browserIntent);
            finish();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private String AddCustomerFunction() {

        Gson gson = new Gson();
        AWSCredentials credentials = null;
        credentials = new BasicAWSCredentials(BuildConfig.ACCESS_KEY, BuildConfig.SECRET);
        AWSLambdaClient lambdaClient = new AWSLambdaClient(credentials);

        lambdaClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
        try {
            InvokeRequest invokeRequest = new InvokeRequest();
            invokeRequest.setFunctionName("CreateCustomer");

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", customer.getName());
            map.put("mobileNum", customer.getMobileNum());
            map.put("hawkerCode", "" +String.valueOf(customer.getHawkerCode()).split("\\.")[0]);//
            map.put("lineNum", ""+String.valueOf(customer.getLineNum()).split("\\.")[0] );
            map.put("houseSeq", String.valueOf(customer.getHouseSeq()).split("\\.")[0] );
            map.put("oldHouseNum", customer.getOldHouseNum());
            map.put("newHouseNum", customer.getNewHouseNum());
            map.put("addrLine1", customer.getAddrLine1());
            map.put("addrLine2", customer.getAddrLine2());
            map.put("locality", customer.getLocality());
            map.put("city", customer.getCity());
            map.put("state", customer.getState());
            map.put("profile1", customer.getProfile1());
            map.put("profile2", customer.getProfile2());
            map.put("profile3", customer.getProfile3());
            map.put("initials", customer.getInitials());
            map.put("employment", customer.getEmployment());
            map.put("comments", customer.getComments());
            map.put("buildingStreet", customer.getBuildingStreet());
            map.put("totalDue", String.valueOf(customer.getTotalDue()).split("\\.")[0] );
            map.put("hawkerId", ""+String.valueOf(customer.getHawkerId()).split("\\.")[0] );
            map.put("lineId", ""+String.valueOf(customer.getLineId()).split("\\.")[0] );

            invokeRequest.setPayload(ByteBuffer.wrap(gson.toJson(map).getBytes(StringUtils.UTF8)));
            InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

            if (invokeResult.getLogResult() != null) {
                System.out.println(" log: "
                        + new String(Base64.decode(invokeResult.getLogResult())));
            }

            if (invokeResult.getFunctionError() != null) {
                throw new LambdaFunctionException(invokeResult.getFunctionError(),
                        new String(invokeResult.getPayload().array()));
            }
            if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                return "";
            }
            String value = new String(invokeResult.getPayload().array(), "UTF-8");
            String jsonFormattedString = value.replaceAll("\\\\", "");
            jsonFormattedString = jsonFormattedString.replace("\"[", "[");
            jsonFormattedString = jsonFormattedString.replace("]\"", "]");
            System.out.println("EditCustomer:\t" + jsonFormattedString);
            return jsonFormattedString;
            //			System.out.println("CustomerListForHawkerAndLine:\t"+gson.fromJson(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(invokeResult.getPayload().array()))), ArrayList.class));

        } catch (LambdaFunctionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }
}
