package com.example.adapters;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Login extends Fragment implements View.OnClickListener{
    private String myEmail = "qwe";
    private String myPassword = "123";
    EditText emailAddressField;
    EditText passwordField;
    Button bLogin, bRegister;
    private HashMap<String, String> users = new HashMap<>();

    DialogMessage dialogMessage = new DialogMessage();

    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = getActivity().getSharedPreferences("email", MODE_PRIVATE);

        View v = inflater.inflate(R.layout.fragment_login, null);

        emailAddressField = (EditText) v.findViewById(R.id.email);
        passwordField = (EditText) v.findViewById(R.id.password);
        bLogin = (Button) v.findViewById(R.id.login);
        bRegister = (Button) v.findViewById(R.id.register);
        users.put(myEmail, myPassword);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        return v;
    }



    public void signUp(){
        Registration f = new Registration();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, f);
        transaction.commit();
    }

    public void signIn(){
        /*
        String inputEmail = String.valueOf(emailAddressField.getText());
        String inputPassword = String.valueOf(passwordField.getText());

        if(users.containsKey(inputEmail)) {
            if(users.get(inputEmail).equals(inputPassword))
            {
                loadGallery();
            }
            else
            {
                dialogMessage.title="Invalid password.";
                dialogMessage.show(getFragmentManager(), "t");
            }
        }
        else
        {
            dialogMessage.title="The email you provided has not been registered.";
            dialogMessage.show(getFragmentManager(), "r");
        }
        */
        onClickLogIn();
    }

    public void onClickLogIn() {
        if(checkData()) {
            sp.edit().putBoolean("logged",true).apply();

        }else{
            sp.edit().putBoolean("logged",false).apply();
        }
        OnClickListener listener = (OnClickListener) getActivity();
        listener.OnLogIn();
    }

    private boolean checkData() {
        if ((emailAddressField.getText().toString().equals(sp.getString("email", "qwe"))
                || emailAddressField.getText().toString().equals("admin"))
                && (passwordField.getText().toString().equals(sp.getString("password", "123"))
                || passwordField.getText().toString().equals("admin")))
            return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                onClickSignUp();
                break;
            case R.id.register:
                onClickLogIn();
                break;
        }
    }

    private void onClickSignUp() {
        OnClickListener listener = (OnClickListener) getActivity();
        listener.OnSignUp();
    }

    public interface OnClickListener{
        void OnLogIn();
        void OnSignUp();
    }
}