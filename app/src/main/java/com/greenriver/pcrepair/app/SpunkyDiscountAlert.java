package com.greenriver.pcrepair.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SpunkyDiscountAlert extends DialogFragment implements View.OnClickListener {
    static SpunkyDiscountAlert newInstance() {
        return new SpunkyDiscountAlert();
    }
        private Button confirm;
        private Button cancel;
        public EditText discount;
        private EditText passwordEntry;
        private Double discount_amount;
        private String password = "1234";
        private OnDataPass dataPasser;

        //interface
        interface OnDataPass {
            public void onDataPass(Double d);
        }

        private OnDataPass dataListener;
        public void setListener(OnDataPass data){
            dataListener = data;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.spunkydiscountalert,null);
            password = getPin();
            getDialog().setTitle("Spunky's Dicount:");


            //setup button listener for confirmation
            confirm = (Button) v.findViewById(R.id.confirm);
            confirm.setOnClickListener(this);
            confirm.setEnabled(false);

            //Set up listener for cancel
            cancel = (Button) v.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);

            //Set up the edit text discount
            discount = (EditText) v.findViewById(R.id.discount);

            //Password prompt
            passwordEntry = (EditText) v.findViewById(R.id.password);

            passwordEntry.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if( passwordEntry.getText().toString().equals(password))
                        confirm.setEnabled(true);
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });


            return v;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:

                    MainActivity main = (MainActivity)getActivity();//Bring in the default price value
                    // Tests discount if value is blank then assigns discount to 0.00
                    try
                    {
                        discount_amount = Double.parseDouble(discount.getText().toString());
                    }
                    catch (Throwable ignore)
                    {
                        discount_amount = 0.0;
                    }




                    // Checks to make sure amount discounting is either 0 or no more than default price
                    if (discount_amount >= 0 && discount_amount <= main.DEFAULT_PRICE)
                    {

                        // The magic that passes info back to a fragment without using mainactivity
                        Intent i = new Intent();
                        Bundle extras = new Bundle();
                        extras.putDouble("disc", discount_amount);
                        i.putExtras(extras);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,i);

                        dismiss();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "You have entered an incorrect amount!", Toast.LENGTH_LONG).show();
                    }
                    break;
                
                case R.id.cancel:
                    dismiss();
                    break;
            }
        }
    public static String getPin() {
        String result = "";
        InputStream isr = null;
        String PASSWORD = "1234";
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

        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }
        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                PASSWORD = json.getString("pin");  }

        } catch (Exception e) {
        // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data " + e.toString());
        }
        return PASSWORD;
    }
}
