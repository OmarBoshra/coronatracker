package com.pan.coronatracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Infections extends AppCompatActivity implements UsersAdapter.SelectedUser {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem toprofile = menu .findItem(R.id.profile);
        MenuItem refresh = menu .findItem(R.id.refresh);
        MenuItem filter = menu .findItem(R.id.filter);


        toprofile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent = new Intent(Infections.this, Profile.class);
                startActivity(intent);

                return false;
            }
        });

        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();

                Boolean isCountry= pref.getBoolean(getResources().getString(R.string.iscountry),false);
if(isCountry){
    editor.putBoolean(getResources().getString(R.string.iscountry), false);
    search.setQueryHint("Search By country");
    Toast.makeText(Infections.this, "Filter by country", Toast.LENGTH_SHORT).show();
}else {
    editor.putBoolean(getResources().getString(R.string.iscountry), true);
    search.setQueryHint("Search By address");

    Toast.makeText(Infections.this, "Filter by address", Toast.LENGTH_SHORT).show();

}

                editor.apply();

                return false;
            }
        });


        refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                refreshData();

                return false;
            }
        });

        return true;


    }

    private void refreshData(){


        final ProgressDialog dialogProgress = new ProgressDialog(this);
        dialogProgress.setMessage(getResources().getString(R.string.Loading));
        dialogProgress.setCancelable(false);
        dialogProgress.show();

        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        RecyclerView recyclerView = findViewById(R.id.recycleView);


                        List<UserModel> userModelList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            UserModel user = snapshot.getValue(UserModel.class);

                            userModelList.add(user);
//todo fill recycle view



                        }
                        final UsersAdapter  usersAdapter = new UsersAdapter(userModelList, Infections.this);

                        recyclerView.setLayoutManager(new LinearLayoutManager(Infections.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(Infections.this,DividerItemDecoration.VERTICAL));

                        recyclerView.setAdapter(usersAdapter);

                        dialogProgress.dismiss();

                        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {

                                usersAdapter.getFilter().filter(s);

                                return false;
                            }
                        });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }


                });
    }

    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infection);

        search = findViewById(R.id.searchView);

        refreshData();



    }

    @Override
    public void selectedUser(UserModel userModel) {


        AlertDialog.Builder details = new AlertDialog.Builder(Infections.this);

        LinearLayout linearLayout = new LinearLayout(Infections.this);

        TextView UserName = new TextView(Infections.this);
        TextView Probability = new TextView(Infections.this);
        TextView Temperature = new TextView(Infections.this);
        TextView HighTemperature = new TextView(Infections.this);
        TextView Coughing = new TextView(Infections.this);
        TextView Headache = new TextView(Infections.this);
        TextView shortbreath = new TextView(Infections.this);
        TextView sorethroat = new TextView(Infections.this);
        TextView Infection = new TextView(Infections.this);
        TextView NationalId = new TextView(Infections.this);


        HighTemperature.setVisibility(userModel.getHigh_temp()?View.VISIBLE:View.GONE);
        Temperature.setVisibility(!userModel.getTemperature().isEmpty()?View.VISIBLE:View.GONE);

        Coughing.setVisibility(userModel.getCoughing()?View.VISIBLE:View.GONE);
        Headache.setVisibility(userModel.getHeadache()?View.VISIBLE:View.GONE);
        shortbreath.setVisibility(userModel.getShort_breath()?View.VISIBLE:View.GONE);
        sorethroat.setVisibility(userModel.getSore_throat()?View.VISIBLE:View.GONE);
        Infection.setVisibility(userModel.getInfected()?View.VISIBLE:View.GONE);

        NationalId.setVisibility(!userModel.getNationalID().isEmpty()?View.VISIBLE:View.GONE);


        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        UserName.setText(userModel.getUsername());
        Probability.setText(userModel.getProbability());

        HighTemperature.setText("Has a High Temperature");
        Temperature.setText("Temperature "+ userModel.getTemperature()+"celsius");// add the actual temperature
        Coughing.setText("Has Coughing");
        Headache.setText("Has Headache");
        shortbreath.setText("Has a short breath");
        sorethroat.setText("Has sore throat");
        Infection.setText("Sure of infection");
        NationalId.setText("National ID "+userModel.getNationalID());

        linearLayout.setPadding(8,8,8,8);

        linearLayout.addView(UserName);
        linearLayout.addView(Probability);
        linearLayout.addView(HighTemperature);
        linearLayout.addView(Temperature);
        linearLayout.addView(Coughing);
        linearLayout.addView(Headache);
        linearLayout.addView(shortbreath);
        linearLayout.addView(sorethroat);
        linearLayout.addView(Infection);
        linearLayout.addView(NationalId);


        details.setView(linearLayout);
        details.show();

    }
}
