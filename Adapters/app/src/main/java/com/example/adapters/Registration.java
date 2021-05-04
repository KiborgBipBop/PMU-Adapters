package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends Fragment implements View.OnClickListener{
    EditText emailAddressField;
    EditText passwordField;
    EditText confirmPasswordField;
    Button bRegister;

    DialogMessage dialogMessage = new DialogMessage();

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        emailAddressField = (EditText)view.findViewById(R.id.email);
        passwordField = (EditText)view.findViewById(R.id.password);
        confirmPasswordField = (EditText)view.findViewById(R.id.confirm_password);
        bRegister =(Button) view.findViewById(R.id.register);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return view;
    }

    public void register(){
        String patternPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}";
        String patternMail = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
        String mail= String.valueOf(emailAddressField.getText());
        String pass= String.valueOf(passwordField.getText());
        String secPass= String.valueOf(confirmPasswordField.getText());
        if (mail.matches(patternMail) && pass.matches(patternPass)) {
            if(pass.equals(secPass)) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Login  frag = new Login();
                fragmentTransaction.replace(R.id.main_layout, frag);
                fragmentTransaction.commit();

                sharedPreferences.edit().putString("email", emailAddressField.getText().toString()).apply();
                sharedPreferences.edit().putString("password", passwordField.getText().toString()).apply();

                OnClickListener listener = (OnClickListener)getActivity();
                listener.OnRegisterClick();
            }
            else {
                dialogMessage.title = "Passwords don't match.";
                dialogMessage.show(getFragmentManager(), "custom");
            }
        }
        else {
            dialogMessage.title="Invalid password or email address.";
            dialogMessage.show(getFragmentManager(),"u");
        }
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnClickListener{
        void OnRegisterClick();
    }
}