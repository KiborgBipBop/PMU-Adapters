package com.example.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Registration.OnClickListener, Login.OnClickListener {

    SharedPreferences sp;
    DialogMessage dialogMessage = new DialogMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("email", MODE_PRIVATE);

        if(savedInstanceState==null) {
            Login lg = new Login();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.main_layout, lg, "Login");
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);
        }
    }


    @Override
    public void OnRegisterClick() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Login frag = new Login();

        ft.replace(R.id.main_layout, frag, "fragment_log");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void OnLogIn() {
        if(sp.getBoolean("logged",true))
        {
            Feed frag = new Feed();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction().replace(R.id.main_layout,frag,"app");
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            dialogMessage.title="Invalid login or password.";
            dialogMessage.show(getSupportFragmentManager(), "r");
        }
    }

    @Override
    public void OnSignUp() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Registration frag = new Registration();

        ft.replace(R.id.main_layout, frag, "fragment_log");
        ft.addToBackStack(null);
        ft.commit();
    }
}