package com.pan.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

  private EditText email, password;
    private Button register;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        authentication = FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        password =findViewById(R.id.password);

        register=findViewById(R.id.LogIn);



    }

    public void LogIn(View view) {

        String email_txt = email.getText().toString();
        String password = this.password.getText().toString();

        if(email_txt.isEmpty()||password.isEmpty())
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        else
            authentication.signInWithEmailAndPassword(email_txt,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(LoginActivity.this, Profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }else{

                        Toast.makeText(LoginActivity.this, "Failed authentication", Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }

    public void ForgotPassword(View view) {

        String email_txt = email.getText().toString();

        if (email_txt.isEmpty())
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        else {


            authentication.sendPasswordResetEmail("user@example.com")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "New password sent to your email", Toast.LENGTH_LONG).show();

                            }else{

                                Toast.makeText(LoginActivity.this, "Your email is not registered ", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });


        }
    }
}
