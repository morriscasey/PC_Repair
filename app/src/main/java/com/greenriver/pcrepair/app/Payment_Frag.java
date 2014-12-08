package com.greenriver.pcrepair.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class Payment_Frag extends DialogFragment implements View.OnClickListener {
    private TextView price;
    private boolean signed = false;
    private boolean payment = false;
    private View v;
    private View alert;
    private CheckBox cash;
    private CheckBox check;
    private CheckBox spunkyDiscount;
    private Button apply;
    private Button reset;
    private Button gestureSave;
    private Button gestureClear;
    private EditText receipt;
    private Double discount;
    private Button next;
    private Double updatePrice = 0.00;
    public double DEFAULT_PRICE;
    private GestureOverlayView gv; // For signature box
    private boolean isClicked = false; //Used in signature to determine is gesture was pressed
    private String FILENAME = "signature.png";
    private FileOutputStream fos;
    private String paymentType;




    public static final int SPUNKYDISCOUNTALERT = 1; //class variable
    SpunkyDiscountAlert discountSpunky = new SpunkyDiscountAlert(); //intialize call to spunkydiscountalert


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.payment_layout, container, false);

        /* Initialize the Default Price
        *  String.format("%.2f", String) makes the string display with
        *  two decimal places as opposed to one.
        */

        MainActivity a = (MainActivity)getActivity();
        a.changeWarrantyButtonOff();
        a.changePolicy1ButtonOff();
        a.changePolicy2ButtonOff();
        a.changeCustomerInfoButtonOff();
        a.changePaymentButtonOn();
        a.changeReviewButtonOff();
        DEFAULT_PRICE = a.DEFAULT_PRICE;

        price = (TextView) v.findViewById(R.id.price);
        price.setText(String.format("%.2f", DEFAULT_PRICE));

        apply = (Button) v.findViewById(R.id.applyDiscount);
        apply.setOnClickListener(this);
        apply.setEnabled(false);

        next = (Button) v.findViewById(R.id.nextButton);
        next.setBackgroundColor(Color.rgb(108, 179, 59));
        next.setOnClickListener(this);


        spunkyDiscount = (CheckBox) v.findViewById(R.id.spunky_Discount);
        spunkyDiscount.setOnClickListener(this);

        cash = (CheckBox) v.findViewById(R.id.cashPayment);
        cash.setOnClickListener(this);
        check = (CheckBox) v.findViewById(R.id.checkPayment);
        check.setOnClickListener(this);

        //Initialize reciept
        receipt = (EditText) v.findViewById(R.id.receipt);

        // Initialize gesture for signature
        gv = (GestureOverlayView) v.findViewById(R.id.signature);
        gv.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        gv.setFadeEnabled(true);
        gv.setFadeOffset(5000);
        // Cached Drawing for future export
        gv.setDrawingCacheEnabled(true);


        // Initialize button
        gestureSave = (Button) v.findViewById(R.id.btnSave);
        gestureSave.setOnClickListener(this);
        gestureClear = (Button) v.findViewById(R.id.btnClear);
        gestureClear.setOnClickListener(this);


        return v;
    }

   /* @Override
    public void onResume() {
        super.onResume();
        MainActivity a = (MainActivity)getActivity();
        DEFAULT_PRICE = a.DEFAULT_PRICE;

        price = (TextView) v.findViewById(R.id.price);
        price.setText(String.format("%.2f", DEFAULT_PRICE));
    }*/

    /**
     * onClick Method:
     * Handles all on click functions within the view using a
     * switch statement.
     * <p/>
     * Case 1:
     * Spunky Discount
     * This method alters the price TextView to apply Spunky
     * discount. It takes the information from the alert and applies
     * the value entered, reducing the price.
     * <p/>
     * Case 2:
     * Apply Discount
     * This method takes the information provided from Spunky Dicount
     * and implements it to TextView
     * <p/>
     * Case 3:
     * Reset Amount
     * Clicking the reset button will put all the values to zero and correct TextView to the
     * default $50.00 amount
     * <p/>
     * Case 4 and 5:
     * Cash or Check Payment
     * When the cash payment checkbox is selected, the case will check
     * if the box is selected or if it has not been. If the box is not
     * checked, it will disable the other checkbox, but will enable both
     * if the checkbox is being unchecked. The same is true visa versa.
     *
     * @param v View of the current fragment
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spunky_Discount:
                if (spunkyDiscount.isChecked()) {
                    // Alert prompting user to enter an amount.
                    discountSpunky.setTargetFragment(this, SPUNKYDISCOUNTALERT);
                    discountSpunky.show(getFragmentManager().beginTransaction(), "alertDialog");
                    apply.setEnabled(true);
                }
                else {
                    price.setText(String.format("%.2f", DEFAULT_PRICE));
                    apply.setEnabled(false);
                }
                break;
            case R.id.applyDiscount:
                if (updatePrice != 0){
                    price.setText(String.format("%.2f", updatePrice));
                }
                v.invalidate();
                break;
            case R.id.cashPayment:
                if (check.isEnabled()) {
                    paymentType = "cash";
                    check.setEnabled(false);
                    payment = true;
                } else {
                    check.setEnabled(true);
                    payment = false;
                }
                break;
            case R.id.checkPayment:
                if (cash.isEnabled()) {
                    paymentType = "check";
                    cash.setEnabled(false);
                    payment = true;
                } else {
                    cash.setEnabled(true);
                    payment = false;
                }
                break;
            //For some reason, this only can save the first attempt
            case R.id.btnSave:
                Bitmap bm = Bitmap.createBitmap(gv.getDrawingCache());
                MainActivity a = (MainActivity)getActivity();
                a.setImageData(bm);
                //Allows the user to continue to the next page
                signed = true;
                gestureSave.setEnabled(false);
                gestureClear.setEnabled(false);
                //Empties the Cache
                gv.setDrawingCacheEnabled(false);

                //This section is for saving local copies
                /*try {
                    gv.setDrawingCacheEnabled(false);
                    Toast.makeText(getActivity(), "I'm in the save button", Toast.LENGTH_LONG).show();
                    fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    //compress to specified format (PNG), quality - which is ignored for PNG, and out stream
                    bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    a.setImageData(bm);
                    fos.flush();
                    fos.close();
                    Toast.makeText(getActivity(), "Signature Received.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.v("Gestures", e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        fos.flush();
                        fos.close();
                        Toast.makeText(getActivity(), "Signature Saved.", Toast.LENGTH_LONG).show();
                    } catch (Throwable ignore) {
                        Toast.makeText(getActivity(), "Oops! File didn't save.", Toast.LENGTH_LONG).show();
                    }
                }*/
                //resets the cache
                gv.setDrawingCacheEnabled(true);
                gestureSave.setEnabled(true);
                gestureClear.setEnabled(true);
                Toast.makeText(getActivity(), "Signature saved!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnClear:
                signed = false;
                gv.cancelClearAnimation();
                gv.clear(true);
                gv.invalidate();
                break;
            case R.id.nextButton:
                MainActivity main = (MainActivity)getActivity();
                if (signed == true && payment == true && (receipt.getText() != null)) {
                    main.setPaymentData(receipt.getText().toString() + ":://" + price.getText().toString() + ":://"
                            + paymentType);
                    android.app.FragmentTransaction FT = getFragmentManager().beginTransaction();
                    //Allows review button to appear in main activity
                    Button btnreview = (Button)main.findViewById(R.id.btnReview);
                    main.enableButton(btnreview);
                    Review_Frag review = new Review_Frag();
                    FT.replace(R.id.contentView, review);
                    FT.addToBackStack(null);
                    FT.commit();
                }
                else {
                    Toast.makeText(getActivity(), "You did not fill out the required information!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SPUNKYDISCOUNTALERT:

                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    double discount = bundle.getDouble("disc");
                    updatePrice = DEFAULT_PRICE - discount;
                }

                break;
        }
    }
}