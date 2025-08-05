package com.psatraining.loginandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;
    private List<UserModel> userList;

    // Constructor for UserAdapter
    public UserAdapter(Context context, List<UserModel> userList){
        this.userList = userList;
        this.context = context;
    }

    // New Class for ViewHolder
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tv_userID, tv_userName, tv_userEmail, tv_birthdate, tv_description;

        public UserViewHolder(View view){
            super(view);
            tv_userID = view.findViewById(R.id.tv_userID);
            tv_userName = view.findViewById(R.id.tv_userName);
            tv_userEmail = view.findViewById(R.id.tv_userEmail);
            tv_birthdate = view.findViewById(R.id.tv_birthdate);
            tv_description = view.findViewById(R.id.tv_description);
        }
    }

    // This will create a new view if there is a new user
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false);
        return new UserViewHolder(view);
    }

    // This will populate the created view with the values coming from the database
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);

        holder.tv_userID.setText("ID: " + user.getUserID());
        holder.tv_userName.setText(user.getUserName());
        holder.tv_userEmail.setText(user.getUserEmail());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            if (context instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) context;
                UserDetailDialogFragment dialog = UserDetailDialogFragment.newInstance(user);
                dialog.show(activity.getSupportFragmentManager(), "UserDetailDialog");
            }
        });

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateUserList(List<UserModel> newUserList) {
        this.userList = newUserList;
        notifyDataSetChanged();
    }
}
