package com.greenriver.pcrepair.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

public class Warranty_Frag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.warranty_layout, container, false);

        final CheckBox yesCheck = (CheckBox) v.findViewById(R.id.yesBox);
        final CheckBox noCheck = (CheckBox) v.findViewById(R.id.noBox);
        final Button btnNext = (Button) v.findViewById(R.id.nextButton);

        final MainActivity a = (MainActivity)getActivity();
        a.changeWarrantyButtonOn();
        a.changePolicy1ButtonOff();
        a.changePolicy2ButtonOff();
        a.changeCustomerInfoButtonOff();
        a.changePaymentButtonOff();
        a.changeReviewButtonOff();

        // predefines button as not enabled.
        btnNext.setEnabled(false);

        //hides Next button until warranty set to no
        btnNext.setVisibility(View.INVISIBLE);

        yesCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesCheck.isChecked() && noCheck.isChecked()) {

                    //Deactive noCheck
                    noCheck.setChecked(false);
                }
                //disables and hides the Next button
                btnNext.setEnabled(false);
                btnNext.setVisibility(View.INVISIBLE);

                // Pop-up Alert stating under warranty
                FragmentManager manager = getFragmentManager();
                MyAlertDialogFragment alertDialog = new MyAlertDialogFragment();

                alertDialog.show(manager, "alertDialog");

            }
        });

        noCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){

                if (noCheck.isChecked() && yesCheck.isChecked()) {

                    //Deactivate yesCheck
                    yesCheck.setChecked(false);
                }
                //Enables and un-hides the Next button
                btnNext.setEnabled(true);
                btnNext.setVisibility(View.VISIBLE);
                }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                android.app.FragmentTransaction FT = getFragmentManager().beginTransaction();
                //Allows policy1 button to appear in main activity
                Button policy = (Button)a.findViewById(R.id.btnPolicy1);
                a.enableButton(policy);
                Policy1_Frag policy1 = new Policy1_Frag();
                FT.replace(R.id.contentView, policy1);
                FT.addToBackStack(null);
                FT.commit();
            }
        });

        return v;
    }



}



