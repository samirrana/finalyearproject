package com.example.assignmentapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogOutFragment extends Fragment {


    private EditText mEmail;
    private Button moButton;
    private EditText mPassword;


    public static LogOutFragment newInstance() {


        LogOutFragment fragment = new LogOutFragment();
        return fragment;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loginpage, container, false);

        moButton = (Button) v.findViewById(R.id.login);
        moButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

           /*     if (mEmail.getText().length()<1 || mPassword.getText().length()<1 ){
                     Toast toast = Toast.makeText(getActivity(), "Please fill in all the details", Toast.LENGTH_LONG);

                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toastMessage.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {*/
                    Intent intent = new Intent(getActivity(), MainMenuActivity.class);
                    startActivity(intent);

                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                /*}*/




            }
        });

        mEmail = (EditText) v.findViewById(R.id.username);

        mEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {


                if (mEmail.getText().length()<1){
                    mEmail.setError("Please enter a valid student ID");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //empty


            }

        });

        mPassword = (EditText) v.findViewById((R.id.password));

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mPassword.getText().length()<1){
                    mPassword.setError("Please enter a password");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





return v;
    }

}
