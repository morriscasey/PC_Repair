package com.greenriver.pcrepair.app;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;

public class Policy2_Frag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.policy2_layout, container, false);

        //Policy1 is an html page from 172.30.0.14/policy1.html
        WebView web;
        web= (WebView) v.findViewById(R.id.policy2);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://172.30.0.14/policy2.html");

        //defines button and checkbox
        final CheckBox policy2Check = (CheckBox) v.findViewById(R.id.checkedPolicy2);
        final Button btnNext = (Button) v.findViewById(R.id.nextButton);

        final MainActivity a = (MainActivity)getActivity();
        a.changeWarrantyButtonOff();
        a.changePolicy1ButtonOff();
        a.changePolicy2ButtonOn();
        a.changeCustomerInfoButtonOff();
        a.changePaymentButtonOff();
        a.changeReviewButtonOff();

        // predefines button as not enabled.
        btnNext.setEnabled(false);
        btnNext.setBackgroundColor(Color.rgb(108, 179, 59));
        policy2Check.setChecked(false);

        //hides Next button until warranty set to no
        btnNext.setVisibility(View.INVISIBLE);

        // policy1Check is checked to allow the next button to show up
        policy2Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                if (policy2Check.isChecked()) {

                    //Enables and un-hides the Next button
                    btnNext.setEnabled(true);
                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });

        //Inflate the next Policy1
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                android.app.FragmentTransaction FT = getFragmentManager().beginTransaction();
                //Allows customer info button to appear in main activity
                Button custInfo = (Button)a.findViewById(R.id.btnCustomerInfo);
                a.enableButton(custInfo);
                CustomerInfo_Frag customerinfo = new CustomerInfo_Frag();
                FT.replace(R.id.contentView, customerinfo);
                FT.addToBackStack(null);
                FT.commit();
            }
        });

        return v;
    }

}