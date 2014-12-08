package com.greenriver.pcrepair.app;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Review_Frag extends Fragment implements View.OnClickListener{
    private String name;
    private String receipt;
    private String price;
    private String paymentMethod;
    private String phoneNumber;
    private String email;
    private String studentID;
    private String problem;
    private Bitmap signature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.review_layout, container, false);

        MainActivity a = (MainActivity)getActivity();
        a.changeWarrantyButtonOff();
        a.changePolicy1ButtonOff();
        a.changePolicy2ButtonOff();
        a.changeCustomerInfoButtonOff();
        a.changePaymentButtonOff();
        a.changeReviewButtonOn();

        //Grabs the data from main
        String test = a.getStringData();
        //Splits test into an array
        String[] array =  test.split(":://");

        //Assigns the displays with the data
        TextView nameDisp = (TextView) v.findViewById(R.id.nameDisplay);
        nameDisp.setText(array[0]);

        TextView receiptDisp = (TextView) v.findViewById(R.id.receiptDisplay);
        receiptDisp.setText(array[1]);

        TextView priceDisp = (TextView) v.findViewById(R.id.costDisplay);
        priceDisp.setText(array[2]);

        TextView paymentMethod = (TextView) v.findViewById(R.id.paymentDisplay);
        paymentMethod.setText(array[3]);

        TextView phoneNumber = (TextView) v.findViewById(R.id.phoneDisplay);
        phoneNumber.setText(array[4]);

        TextView emailDisp = (TextView) v.findViewById(R.id.emailDisplay);
        emailDisp.setText(array[5]);

        TextView studentID = (TextView) v.findViewById(R.id.sidDisplay);
        studentID.setText(array[6]);

        TextView problem = (TextView) v.findViewById(R.id.issueDisplay);
        problem.setText(array[7]);

        ImageView signature = (ImageView) v.findViewById(R.id.signatureImage);
        Bitmap bm = a.getImageData();
        signature.setImageBitmap(bm);
        signature.setMaxWidth(400);

        Button btnReview = (Button) v.findViewById(R.id.menuButton);
        btnReview.setOnClickListener(this);
        btnReview.setBackgroundColor(Color.rgb(108, 179, 59));

        return v;
    }

    @Override
    public void onClick(View view) {
        MainActivity a = (MainActivity)getActivity();
        boolean success = false;
        try {
            success = a.pushData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (success) {
            Toast.makeText(getActivity(), "Form Submitted Successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "Form Not Submitted!", Toast.LENGTH_LONG).show();
        }
    }

}