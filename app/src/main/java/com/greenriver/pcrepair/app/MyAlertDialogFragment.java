package com.greenriver.pcrepair.app;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyAlertDialogFragment extends DialogFragment implements View.OnClickListener{
    static MyAlertDialogFragment newInstance() {
        return new MyAlertDialogFragment();
    }
    Button ok;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.warranty_alert,null);
        // Title of Alert
        getDialog().setTitle("Warning:");

        //setup button listener
        ok = (Button) v.findViewById(R.id.btnOk);
        ok.setOnClickListener(this);
        setCancelable(false);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnOk){
            dismiss();

        }
    }
}
