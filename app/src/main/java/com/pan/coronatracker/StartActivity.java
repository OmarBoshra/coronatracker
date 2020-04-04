package com.pan.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser theUser =  FirebaseAuth.getInstance().getCurrentUser();





        if(theUser!= null){

            Intent intent = new Intent(StartActivity.this, Infections.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_start);

        getSupportActionBar().hide();

        register =findViewById(R.id.Register);
        


    }


    public void register(View view) {

        startActivity(new Intent(StartActivity.this,RegisterActivity.class));


    }

    public void LogIn(View view) {
        startActivity(new Intent(StartActivity.this,LoginActivity.class));

    }
}
