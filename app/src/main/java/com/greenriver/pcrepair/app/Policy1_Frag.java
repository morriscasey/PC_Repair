package com.greenriver.pcrepair.app;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Policy1_Frag extends Fragment {

    //Parameters used for pinch
    final static float STEP = 200;
    TextView policyText;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    //float fontsize = 13;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.policy1_layout, container, false);

        //Policy1 is an html page from 172.30.0.14/policy1.html
        WebView web;
        web= (WebView) v.findViewById(R.id.policy1);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://172.30.0.14/policy1.html");

        //defines button and checkbox
        final CheckBox policy1Check = (CheckBox) v.findViewById(R.id.checkedPolicy1);
        final Button btnNext = (Button) v.findViewById(R.id.nextButton);


        final MainActivity a = (MainActivity)getActivity();
        a.changeWarrantyButtonOff();
        a.changePolicy1ButtonOn();
        a.changePolicy2ButtonOff();
        a.changeCustomerInfoButtonOff();
        a.changePaymentButtonOff();
        a.changeReviewButtonOff();


        // predefines button as not enabled.
        btnNext.setEnabled(false);
        btnNext.setBackgroundColor(Color.rgb(108, 179, 59));

        //hides Next button until policy1 has been selected
        btnNext.setVisibility(View.INVISIBLE);

        // policy1Check is checked to allow the next button to show up
        policy1Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                if (policy1Check.isChecked()) {

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
                //Allows policy2 button to appear in main activity
                Button policy = (Button)a.findViewById(R.id.btnPolicy2);
                a.enableButton(policy);
                Policy2_Frag policy2 = new Policy2_Frag();
                FT.replace(R.id.contentView, policy2);
                FT.addToBackStack(null);
                FT.commit();
            }
        });

        return v;


    }

    private class SwAWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                policyText.setTextSize(mRatio + 13);
            }
        }
        return true;
    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}

