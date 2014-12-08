package com.greenriver.pcrepair.app;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CustomerInfo_Frag extends Fragment {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phone;
    private EditText sid;
    private CheckBox slow;
    private CheckBox malware;
    private CheckBox hardware;
    private CheckBox software;
    private CheckBox other;
    private EditText description;
    private Button btnNext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.customer_layout, container, false);


        //defines button
        btnNext = (Button) v.findViewById(R.id.nextButton);
        btnNext.setBackgroundColor(Color.rgb(108, 179, 59));


        firstName = (EditText) v.findViewById(R.id.firstName);

        lastName = (EditText) v.findViewById(R.id.lastName);

        email = (EditText) v.findViewById(R.id.email);

        phone = (EditText) v.findViewById(R.id.phone);

        sid = (EditText) v.findViewById(R.id.sid);

        slow = (CheckBox) v.findViewById(R.id.slowBox);

        malware = (CheckBox) v.findViewById(R.id.malwareBox);

        hardware = (CheckBox) v.findViewById(R.id.hardwareBox);

        software= (CheckBox) v.findViewById(R.id.softwareBox);

        other= (CheckBox) v.findViewById(R.id.otherBox);

        description = (EditText) v.findViewById(R.id.issue);


        MainActivity a = (MainActivity)getActivity();
        a.changeWarrantyButtonOff();
        a.changePolicy1ButtonOff();
        a.changePolicy2ButtonOff();
        a.changeCustomerInfoButtonOn();
        a.changePaymentButtonOff();
        a.changeReviewButtonOff();

        if( firstName.getText().toString().length() == 0 )
            firstName.setError( "First name is required!" );
        if( lastName.getText().toString().length() == 0 )
            lastName.setError( "Last name is required!" );
        if( email.getText().toString().length() == 0 )
            email.setError( "Email is required!" );
        if( phone.getText().toString().length() == 0 )
            phone.setError( "Phone is required!" );
        if( description.getText().toString().length() == 0)
            description.setError( "Description is required!");


        firstName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if( firstName.getText().toString().length() == 0 )
                    firstName.setError( "First name is required!" );
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });



        lastName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if( lastName.getText().toString().length() == 0 )
                    lastName.setError( "Last name is required!" );
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if( email.getText().toString().length() == 0 )
                    email.setError( "Email is required!" );
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
             public void afterTextChanged(Editable s) {
                 if( phone.getText().toString().length() == 0 )
                     phone.setError( "Phone is required!" );


             }
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }
             public void onTextChanged(CharSequence s, int start, int before, int count) {
             }
        });





        //Inflate the next Policy1
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmailValid(email.getText().toString())) {

                    if ( phone.getText().toString().length() == 10) {

                        if(description.getText().length() > 0) {

                            MainActivity a = (MainActivity) getActivity();
                            String s = "";
                            if (slow.isChecked()) {
                                s += "[Slow] ";
                            }
                            if (malware.isChecked()) {
                                s += "[Malware] ";
                            }
                            if (hardware.isChecked()) {
                                s += "[Hardware] ";
                            }
                            if (software.isChecked()) {
                                s += "[Software] ";
                            }
                            if (other.isChecked()) {
                                s += "[Other] ";
                            }
                            else
                            {
                                s += "[Other] ";
                            }

                            a.setCustomerInfoData(firstName.getText().toString() + " " + lastName.getText().toString()
                                    + ":://" + phone.getText().toString() + ":://" + email.getText().toString()
                                    + ":://" + sid.getText().toString() + ":://" + s + ":://" + description.getText().toString());
                            android.app.FragmentTransaction FT = getFragmentManager().beginTransaction();
                            //Allows payment button to appear in main activity
                            Button paymnt = (Button) a.findViewById(R.id.btnPayment);
                            a.enableButton(paymnt);
                            Payment_Frag payment = new Payment_Frag();
                            FT.replace(R.id.contentView, payment);
                            FT.addToBackStack(null);
                            FT.commit();
                            }
                        else
                            {
                                description.setError("Please give a description of your issue!");
                            }

                    }
                    else
                    {
                        phone.setError( "A 10 digit phone number is required." );
                    }
                }
                else
                {
                    email.setError( "Please check the email format and try again!" );
                }
            }
        });




        return v;
    }

    //checks email address is a valid format
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}