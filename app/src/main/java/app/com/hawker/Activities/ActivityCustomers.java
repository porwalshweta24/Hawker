package app.com.hawker.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.hawker.R;
import app.com.hawker.Utils.Utils;
import app.com.hawker.adapter.CustomerListViewAdapter;
import app.com.hawker.dao.Customer;
import app.com.hawker.delegates.IActivityRefresh;


public class ActivityCustomers extends AppCompatActivity implements View.OnClickListener{
    TextView tv_title;
    ImageView iv_list_report;
    ImageView iv_logout;
    ImageView iv_back;
    ListView listView_info;
    ArrayList<Customer> customerArrayList;
    public static String CustomerId;
    FloatingActionButton fabAdd;
    CustomerListViewAdapter adapter;
    String names[] = {"Details","View Customer", "Edit Customer"};
    static int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_list_customer);
            init();
            onCall();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void init() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_list_report = (ImageView) findViewById(R.id.iv_list_report);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        listView_info = (ListView) findViewById(R.id.listView_info);
        fabAdd= (FloatingActionButton) findViewById(R.id.fab_add);
        tv_title.setText("Customers");
        iv_logout.setVisibility(View.GONE);
        iv_list_report.setVisibility(View.GONE);
        iv_back.setVisibility(View.VISIBLE);
        getInfo();
        onClickFab();
    }
    void onClickFab()
    {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Customer country = customerArrayList.get(pos);
                Intent intent = new Intent(ActivityCustomers.this, ActivityAddCustomer.class);
                intent.putExtra("CUSTOMER", country);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationClass globalVariable = (ApplicationClass) getApplicationContext();
        if (globalVariable.isNewCustomer()){
            globalVariable.setNewCustomer(false);
            adapter.notifyDataSetChanged();
        }
    }

    void onCall() {
        iv_back.setOnClickListener(this);
    }

    void getInfo() {
        try {
            customerArrayList = new ArrayList<Customer>(((ApplicationClass) this.getApplicationContext()).getCustomerArrayList());
            adapter = new CustomerListViewAdapter(this,
                    R.layout.table_row_child_customer, customerArrayList);

            LayoutInflater inflater = getLayoutInflater();
            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.table_row_customer, null);
            listView_info.addHeaderView(header, null, false);
            listView_info.setAdapter(adapter);
            listView_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    ShowAlertDialogWithListview(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ShowAlertDialogWithListview(final int position) {
        List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("Details");
        mAnimals.add("View Customer");
        mAnimals.add("Edit Customer");

        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Cutomer List");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
                if (selectedText.equals("View Customer")) {
                    try {
                        pos=position - 1;
                        Customer country = customerArrayList.get(position - 1);
                        CustomerId = country.getCustomerId();
                        Intent browserIntent = new Intent(ActivityCustomers.this, ActivityViewCustomer.class);
                        browserIntent.putExtra("CUSTOMER", country);
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (selectedText.equals("Edit Customer")) {
                    try {
                        pos=position - 1;

                        Customer country = customerArrayList.get(position - 1);
                        CustomerId = country.getCustomerId();
//                            ActivityEditCustomer activityEditCustomer=new ActivityEditCustomer(ActivityCustomers.this);
                        Intent browserIntent = new Intent(ActivityCustomers.this, ActivityEditCustomer.class);
                        browserIntent.putExtra("CUSTOMER", country);
//                        startActivityForResult(browserIntent, 2);
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (selectedText.equals("Details")) {
                    try {
                        pos=position - 1;

                        Customer country = customerArrayList.get(position - 1);
                        CustomerId = country.getCustomerId();
                        Intent browserIntent = new Intent(ActivityCustomers.this, ActivityCustomerSubs.class);
                        browserIntent.putExtra("CUSTOMER_ID", country.getCustomerId());
                        startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    void callDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityCustomers.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.inflate_list, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lv.setAdapter(adapter);
        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.iv_back) {
            finish();
        }

    }

    public void onrefreshingAcivity()
    {
        listView_info.removeAllViews();
        getInfo();
        Utils.showToast(ActivityCustomers.this, "Customer has been updated successfully.");
    }
    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            onrefreshingAcivity();
        }
    }
}
