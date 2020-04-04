package com.pan.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private EditText username,email, password, confirm_password;
    private Button register;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        username=findViewById(R.id.user_name);
        email=findViewById(R.id.email);
        password =findViewById(R.id.password);
        confirm_password =findViewById(R.id.passwordConfirm);
        register=findViewById(R.id.Register);



    }

    public void registration(View view) {


        if (username.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()|| confirm_password.getText().toString().isEmpty())
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        else if (password.getText().toString().length()<6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();

        }else if (!password.getText().toString().equals(confirm_password.getText().toString())) {

            Toast.makeText(this, "Password not confirmed correctly", Toast.LENGTH_SHORT).show();
        }else {
//todo add healthy dialog
            Intent intent = new Intent(RegisterActivity.this, Profile.class);
            intent.putExtra("username",username.getText().toString());
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("password",password.getText().toString());
            startActivity(intent);
            finish();
        }
    }
}
