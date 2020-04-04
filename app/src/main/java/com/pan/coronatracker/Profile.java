package com.pan.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class Profile extends AppCompatActivity {

    private  FirebaseAuth authentication;

    private String username;
    private String email;
    private String password;
    private ProgressDialog dialogProgress;

    CheckBox high_temp;
    CheckBox coughing;
    CheckBox headache;
    CheckBox short_breath;
    CheckBox sore_throat;
    CheckBox infected;

    AutoCompleteTextView country;
    EditText temperature;
    EditText city_address;
    EditText nationalID;
    TextView probability;


    int percentage=0;
    int fraction= (16);

    private void checkerscheck(boolean ischecked){

        if(ischecked) {
            percentage =(fraction + percentage);

            probability.setText("Probability of Corona is " + percentage + "%");
            probability.setTextColor(getResources().getColor(R.color.colorAccent));

        }else{
            percentage = (percentage-fraction);
            probability.setText("Probability of Corona is " + percentage + "%");

            if (percentage==0) {
                probability.setText("Perfectly healthy ✅");
                probability.setTextColor(getResources().getColor(R.color.green));

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView title= findViewById(R.id.personaldetails);

       high_temp= findViewById(R.id.hightemp);
       coughing= findViewById(R.id.cough);
       headache= findViewById(R.id.headache);
       short_breath= findViewById(R.id.shortbreath);
       sore_throat= findViewById(R.id.sorethroat);
       infected= findViewById(R.id.iaminfected);

        country = findViewById(R.id.country);

        probability= findViewById(R.id.probabiltiy);
       temperature= findViewById(R.id.temperature);
       city_address= findViewById(R.id.address);
       nationalID= findViewById(R.id.national);


        high_temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fraction= (16);
                if(compoundButton.isPressed())
                checkerscheck(b);
            }
        });

        coughing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fraction= (16);
                if(compoundButton.isPressed())
                checkerscheck(b);

            }
        });


        headache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fraction= (16);
                if(compoundButton.isPressed())
                checkerscheck(b);

            }
        });


        short_breath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fraction= (20);
                if(compoundButton.isPressed())
                checkerscheck(b);

            }
        });


        sore_throat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fraction= (16);
                if(compoundButton.isPressed())
                checkerscheck(b);

            }
        });


        infected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fraction= (16);
                if(compoundButton.isPressed())
                checkerscheck(b);

            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button submit = findViewById(R.id.Register);

        if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("username")){

            title.setText("Step 2 Personal details");
            username=   getIntent().getExtras().getString("username");
            email= getIntent().getExtras().getString("email");
            password= getIntent().getExtras().getString("password");

        }else {

            dialogProgress = new ProgressDialog(this);
            dialogProgress.setMessage("Loading...");
            dialogProgress.setCancelable(false);
            dialogProgress.show();

            authentication = FirebaseAuth.getInstance();
            //todo when comming back retrieve the user data from shared pref
            FirebaseUser firbaseUser = authentication.getCurrentUser();
            String userId = firbaseUser.getUid();

            FirebaseDatabase.getInstance().getReference().child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    dialogProgress.dismiss();

                    UserModel user = dataSnapshot.getValue(UserModel.class);



                    high_temp.setChecked(user.getHigh_temp());
                    coughing.setChecked(user.getCoughing());
                    headache.setChecked(user.getHeadache());
                    short_breath.setChecked(user.getShort_breath());
                    sore_throat.setChecked(user.getSore_throat());
                    infected.setChecked(user.getInfected());

                    country.setText(user.getCountry());
                    temperature.setText(user.getTemperature());
                    city_address.setText(user.getCity_address());
                    nationalID.setText(user.getNationalID());

                    if(high_temp.isChecked())
                        percentage =(fraction=16 + percentage);
                    if(coughing.isChecked())
                        percentage =(fraction=16 + percentage);
                    if(headache.isChecked())
                        percentage =(fraction=16 + percentage);
                    if(short_breath.isChecked())
                        percentage =(fraction=20 + percentage);
                    if(sore_throat.isChecked())
                        percentage =(fraction=16 + percentage);
                    if(infected.isChecked())
                        percentage =(fraction=16 + percentage);

                    if(percentage>0){

                        probability.setText("Probability of Corona is "+percentage+"%");

                        probability.setTextColor(getResources().getColor(R.color.colorAccent));

                    }else {
                        probability.setText("Perfectly healthy ✅");
                        probability.setTextColor(getResources().getColor(R.color.green));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            submit.setText("Update");

        }

// adapter for the countries
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.countries));

        country.setAdapter(adapter);
        country.setThreshold(1);


    }

    public void submit(View view) {


        if (country.getText().toString().isEmpty()|| !Arrays.asList(getResources().getStringArray(R.array.countries)).contains(country.getText().toString())) {
            Toast.makeText(this, "Please add your country", Toast.LENGTH_SHORT).show();

        } else {

            authentication = FirebaseAuth.getInstance();
            final HashMap<String, Object> hashMap = new HashMap<>();



            if (((Button) view).getText().toString().equals("Update")) {

                FirebaseUser firbaseUser = authentication.getCurrentUser();
                String userId = firbaseUser.getUid();

                dialogProgress = new ProgressDialog(this);
                dialogProgress.setMessage("Loading...");
                dialogProgress.setCancelable(false);
                dialogProgress.show();


                hashMap.put("country", country.getText().toString());
                hashMap.put("city_address", city_address.getText().toString());
                hashMap.put("temperature", temperature.getText().toString());
                hashMap.put("nationalID", nationalID.getText().toString());
                hashMap.put("probability", probability.getText().toString());

                hashMap.put("high_temp", high_temp.isChecked());
                hashMap.put("coughing", coughing.isChecked());
                hashMap.put("headache", headache.isChecked());
                hashMap.put("short_breath", short_breath.isChecked());
                hashMap.put("sore_throat", sore_throat.isChecked());
                hashMap.put("infected", infected.isChecked());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            dialogProgress.dismiss();
                            Toast.makeText(Profile.this, "Update successful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            } else {

                authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        FirebaseUser firbaseUser = authentication.getCurrentUser();
                        String userId = firbaseUser.getUid();

                        if (task.isSuccessful()) {


                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            hashMap.put("username", username);
                            hashMap.put("country", country.getText().toString());
                            hashMap.put("city_address", city_address.getText().toString());
                            hashMap.put("temperature", temperature.getText().toString());
                            hashMap.put("nationalID", nationalID.getText().toString());
                            hashMap.put("probability", probability.getText().toString());

                            hashMap.put("high_temp", high_temp.isChecked());
                            hashMap.put("coughing", coughing.isChecked());
                            hashMap.put("headache", headache.isChecked());
                            hashMap.put("short_breath", short_breath.isChecked());
                            hashMap.put("sore_throat", sore_throat.isChecked());
                            hashMap.put("infected", infected.isChecked());


                            dialogProgress = new ProgressDialog(Profile.this);
                            dialogProgress.setMessage("Loading...");
                            dialogProgress.setCancelable(false);
                            dialogProgress.show();


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        dialogProgress.dismiss();
                                        Toast.makeText(Profile.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Profile.this, Infections.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();


                                    }
                                }
                            });

                        } else {

                            Toast.makeText(Profile.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }

        }
    }
}
