package com.pan.coronatracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVh> implements Filterable {
    private List<UserModel> userModelList;
    private List<UserModel> getUserModelListFiltered;
    private Context context;
    private SelectedUser selectedUser;

    public UsersAdapter(List<UserModel> userModelList,SelectedUser selectedUser) {
        this.userModelList = userModelList;
        this.getUserModelListFiltered = userModelList;
        this.selectedUser = selectedUser;
    }

    @NonNull
    @Override
    public UsersAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UsersAdapterVh holder, int position) {



        UserModel userModel = userModelList.get(position);

        String username = userModel.getUsername();
        String healthstatus = userModel.getProbability();
        String country = userModel.getCountry();
        String address = userModel.getCity_address();

        if(healthstatus.equals("Perfectly healthy ✅"))
            healthstatus="✅";
else
            healthstatus=healthstatus.substring(healthstatus.indexOf("s")+1,healthstatus.length());

        holder.tvUsername.setText(username);
        holder.probability.setText(healthstatus);
        holder.country.setText(country);
        holder.address.setText(address);

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length() == 0){
                    filterResults.count = getUserModelListFiltered.size();
                    filterResults.values = getUserModelListFiltered;

                }else{
                    String searchChr = charSequence.toString().toLowerCase();

                    List<UserModel> resultData = new ArrayList<>();

                    for(UserModel userModel: getUserModelListFiltered){
                        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);

                    Boolean isCountry= pref.getBoolean("iscountry",false);

                        if(isCountry?userModel.getCountry().toLowerCase().contains(searchChr):userModel.getCity_address().toLowerCase().contains(searchChr)){
                            resultData.add(userModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                userModelList = (List<UserModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }


    public interface SelectedUser{

        void selectedUser(UserModel userModel);

    }

    public class UsersAdapterVh extends RecyclerView.ViewHolder {

        TextView probability;
        TextView tvUsername;
        TextView country;
        TextView address;
        public UsersAdapterVh(@NonNull View itemView) {
            super(itemView);
            probability = itemView.findViewById(R.id.userprobability);
            tvUsername = itemView.findViewById(R.id.username);
            country = itemView.findViewById(R.id.usercountry);
            address = itemView.findViewById(R.id.useraddress);

            //todo to remove if unnecessary
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedUser.selectedUser(userModelList.get(getAdapterPosition()));
                }
            });


        }
    }
}
