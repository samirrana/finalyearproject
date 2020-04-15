package com.example.assignmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubjectListFragment extends Fragment {


    private EditText mEmail;
    private Button moButton;


    public static SubjectListFragment newInstance() {


        SubjectListFragment fragment = new SubjectListFragment();
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

                if (mEmail.getText().length()<1 ){
                    mEmail.setError("error");
                } else {
                    Intent intent = new Intent(getActivity(), AssignmentListActivity.class);
                    startActivity(intent);

                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                }




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
                    mEmail.setError("error");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //empty


            }

        });





return v;
    }

}
