package com.example.assignmentapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
    private TextView register;
    private AppDatabase appDatabase;


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
        appDatabase = AppDatabase.geAppdatabase(getActivity());

        register = v.findViewById(R.id.register);


        RoomDAO roomDAO = appDatabase.getRoomDAO();
        UsernamePassword temp = roomDAO.getLoggedInUser();
        if(temp!=null){
            Intent intent = new Intent(getActivity(),MainMenuActivity.class);
            startActivity(intent);

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , Register.class);
                startActivity(intent);

            }
        });

        moButton = (Button) v.findViewById(R.id.login);
        moButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                if (mEmail.getText().length() < 1 || mPassword.getText().length() < 1) {
//                    Toast toast = Toast.makeText(getActivity(), "Please fill in all the details", Toast.LENGTH_LONG);
//
//                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
//                    toastMessage.setTextColor(Color.RED);
//                    toastMessage.setTextSize(25);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                } else {
            /*        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
                    startActivity(intent);

                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();*/
                loginUser(mEmail.getText().toString().trim(),mPassword.getText().toString().trim());


//                }
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


                if (mEmail.getText().length() < 1) {
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

                if (mPassword.getText().length() < 1) {
                    mPassword.setError("Please enter a password");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }

    public void loginUser(String user,String pass){

        RoomDAO roomDAO = appDatabase.getRoomDAO();
        UsernamePassword temp = roomDAO.getUserwithUsername(user);
        //Toast.makeText(this, temp.getPassword(), Toast.LENGTH_SHORT).show();
        if(temp==null){
            Toast.makeText(getActivity(),"Invalid username",Toast.LENGTH_SHORT).show();
        }
        else{
            if(temp.getPassword().equals(pass)){
                temp.setIsloggedIn(1);
                roomDAO.Update(temp);
                AppDatabase.destroyInstance();
                Intent intent = new Intent(getActivity(),MainMenuActivity.class);
                startActivity(intent);

            }
            else{
                Toast.makeText(getActivity(),"Invalid Password",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
