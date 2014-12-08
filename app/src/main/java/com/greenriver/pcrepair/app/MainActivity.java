package com.greenriver.pcrepair.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    //Fields
    Button warrantyButton,policy1Button;
    private String firstName = "first";
    private String lastName = "last";
    private String receipt = "12584aav";
    public static double DEFAULT_PRICE = 0.0;
    private String price = "50.00";
    private String paymentMethod = "cash";
    private String phoneNumber = "123456789";
    private String email = "email@mail.com";
    private String studentID = "110111011";
    private String problem = "Problem Description";
    private String issueType = "[none]";
    private Bitmap signature;
    private Button btnPolicy1,btnPolicy2,btnCustomer,btnPayment,btnReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.enableDefaults();

        btnPolicy1 = (Button)findViewById(R.id.btnPolicy1);
        btnPolicy2 = (Button) findViewById(R.id.btnPolicy2);
        btnCustomer = (Button) findViewById(R.id.btnCustomerInfo);
        btnPayment = (Button) findViewById(R.id.btnPayment);
        btnReview = (Button) findViewById(R.id.btnReview);
        initializeButtons();
        DEFAULT_PRICE = getData();
        price = "" + DEFAULT_PRICE;
        FragmentManager FM = getFragmentManager();
        android.app.FragmentTransaction FT = FM.beginTransaction();
        Warranty_Frag warranty = new Warranty_Frag();
        FT.add(R.id.contentView, warranty);
        FT.commit();
    }

    public void onSelectFragment(View view) {
        android.app.FragmentTransaction FT = getFragmentManager().beginTransaction();
        if (view == findViewById(R.id.btnWarranty)) {
            Warranty_Frag warfragment = new Warranty_Frag();
            FT.replace(R.id.contentView, warfragment);
        }
        else if (view == findViewById(R.id.btnPolicy1)){
            Policy1_Frag policy1fragment = new Policy1_Frag();
            FT.replace(R.id.contentView, policy1fragment);
        }
        else if (view == findViewById(R.id.btnPolicy2)) {
           Policy2_Frag policy2fragment = new Policy2_Frag();
            FT.replace(R.id.contentView, policy2fragment);
        }
        else if (view == findViewById(R.id.btnCustomerInfo)){
            CustomerInfo_Frag custfragment = new CustomerInfo_Frag();
            FT.replace(R.id.contentView, custfragment);
        }
        else if (view == findViewById(R.id.btnPayment)) {
           Payment_Frag payfragment = new Payment_Frag();
            FT.replace(R.id.contentView, payfragment);
        }
        else if (view == findViewById(R.id.btnReview)) {
            Review_Frag reviewfragment = new Review_Frag();
            FT.replace(R.id.contentView, reviewfragment);
        }


        FT.addToBackStack(null);
        FT.commit();
    }


    public void changeWarrantyButtonOn()
    {
        Button theButton = (Button)findViewById(R.id.btnWarranty);
        theButton.setBackgroundColor(Color.rgb(108,179,59));

    }
    public void changeWarrantyButtonOff()
    {
        Button theButton = (Button)findViewById(R.id.btnWarranty);
        theButton.setBackgroundColor(Color.rgb(65,61,61));
    }

    public void changePolicy2ButtonOn()
    {
        Button theButton = (Button)findViewById(R.id.btnPolicy2);
        theButton.setBackgroundColor(Color.rgb(108,179,59));

    }
    public void changePolicy2ButtonOff()
    {
        Button theButton = (Button)findViewById(R.id.btnPolicy2);
        theButton.setBackgroundColor(Color.rgb(65,61,61));
    }

    public void changePolicy1ButtonOn()
    {
        Button theButton = (Button)findViewById(R.id.btnPolicy1);
        theButton.setBackgroundColor(Color.rgb(108,179,59));

    }
    public void changePolicy1ButtonOff()
    {
        Button theButton = (Button)findViewById(R.id.btnPolicy1);
        theButton.setBackgroundColor(Color.rgb(65,61,61));
    }

    public void changeCustomerInfoButtonOn()
    {
        Button theButton = (Button)findViewById(R.id.btnCustomerInfo);
        theButton.setBackgroundColor(Color.rgb(108,179,59));

    }
    public void changeCustomerInfoButtonOff()
    {
        Button theButton = (Button)findViewById(R.id.btnCustomerInfo);
        theButton.setBackgroundColor(Color.rgb(65,61,61));
    }

    public void changePaymentButtonOn()
    {
        Button theButton = (Button)findViewById(R.id.btnPayment);
        theButton.setBackgroundColor(Color.rgb(108,179,59));

    }
    public void changePaymentButtonOff()
    {
        Button theButton = (Button)findViewById(R.id.btnPayment);
        theButton.setBackgroundColor(Color.rgb(65,61,61));
    }

    public void changeReviewButtonOn()
    {
        Button theButton = (Button)findViewById(R.id.btnReview);
        theButton.setBackgroundColor(Color.rgb(108,179,59));

    }
    public void changeReviewButtonOff()
    {
        Button theButton = (Button)findViewById(R.id.btnReview);
        theButton.setBackgroundColor(Color.rgb(65,61,61));
    }

    //Data Getter method
    //String return:This method gets all the data fields we have
    //For any of the different fragments and returns a large string
    //That we can seperate into an array. We will use the char sequence
    //..//
    //To symbolize a value to split between (totally random, can change)
    public String getStringData(){
        return (this.firstName + " " + this.lastName + ":://" + this.receipt + ":://" + this.price
                + ":://" + this.paymentMethod + ":://" + this.phoneNumber
                + ":://" + this.email + ":://" + this.studentID + ":://"
                + this.issueType + "\n" + this.problem);
    }

    //Image return
    public Bitmap getImageData(){
        return this.signature;
    }

    //Setting Method
    //manipulates the individual field data like name, ssid, etc.
    public void setImageData(Bitmap bm){
        this.signature = bm;
    }

    public void setPaymentData(String s){
        String[] arr = s.split(":://");
        this.receipt = arr[0];
        this.price = arr[1];
        this.paymentMethod = arr[2];
    }

    public void setCustomerInfoData(String s){
        String[] arr = s.split(":://");
        String[] arr2 = arr[0].split(" ");
        this.firstName = arr2[0];
        this.lastName = arr2[1];
        this.phoneNumber = arr[1];
        this.email = arr[2];
        this.studentID = arr[3];
        this.issueType = arr[4];
        this.problem = arr[5];
    }

    public double getPrice() { return this.DEFAULT_PRICE;}

    public static double getData() {
        String result = "";
        InputStream isr = null;
        double PRICE = 0.0;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://172.30.0.14/config/query.php"); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result=sb.toString();
            //Toast.makeText(, result, Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }
//parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);
            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                s = json.getString("default_price");  }
            PRICE = Double.parseDouble(s);
        } catch (Exception e) {
// TODO: handle exception
            Log.e("log_tag", "Error Parsing Data " + e.toString());
        }
        return PRICE;
    }

    public boolean pushData() throws UnsupportedEncodingException {
            // Get user defined values
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://172.30.0.14/php/insertData.php");
            // Create data variable for sent values to server

            Bitmap bm = signature;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("first_name", this.firstName));
                nameValuePairs.add(new BasicNameValuePair("last_name", this.lastName));
                nameValuePairs.add(new BasicNameValuePair("student_id", this.studentID));
                nameValuePairs.add(new BasicNameValuePair("email", this.email));
                nameValuePairs.add(new BasicNameValuePair("phone_number", this.phoneNumber));

                nameValuePairs.add(new BasicNameValuePair("type_issue", this.issueType));
                nameValuePairs.add(new BasicNameValuePair("comment", this.problem));
                nameValuePairs.add(new BasicNameValuePair("sign_image", encodedImage));
                nameValuePairs.add(new BasicNameValuePair("payment_type", this.paymentMethod));
                nameValuePairs.add(new BasicNameValuePair("amount", this.price));
                nameValuePairs.add(new BasicNameValuePair("receipt", this.receipt));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                //Execute
                HttpResponse response = httpclient.execute(httpPost);





            }
            catch(Exception ex)
            {
                Toast.makeText(this, "Connection was not successful! Please make sure connected to Tech.div!", Toast.LENGTH_LONG).show();
            }
            finally
            {
                try
                {
                    Toast.makeText(this, "Data Sent!", Toast.LENGTH_LONG).show();
                    synchronized (this) {
                        wait(100);
                    }

                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    return true;

                }

                catch(Exception ex) {}

            }


        return false;
    }

    // Disables the Content buttons until user has accessed the fragment corresponding to the button.
    void initializeButtons()
    {

        btnPolicy1.setTextColor(Color.rgb(65,61,61));
        btnPolicy1.setEnabled(false);
        btnPolicy2.setTextColor(Color.rgb(65,61,61));
        btnPolicy2.setEnabled(false);
        btnCustomer.setTextColor(Color.rgb(65,61,61));
        btnCustomer.setEnabled(false);
        btnPayment.setTextColor(Color.rgb(65,61,61));
        btnPayment.setEnabled(false);
        btnReview.setTextColor(Color.rgb(65,61,61));
        btnReview.setEnabled(false);
    }

    void enableButton(Button b)
    {
        b.setTextColor(Color.BLACK);
        b.setEnabled(true);
    }

}
