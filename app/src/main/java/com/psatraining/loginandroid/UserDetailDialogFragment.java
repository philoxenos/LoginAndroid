package com.psatraining.loginandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class UserDetailDialogFragment extends DialogFragment {

    public static final String ARG_USER_ID = "userID";
    public static final String ARG_USER_NAME = "userName";
    public static final String ARG_USER_EMAIL = "userEmail";
    public static final String ARG_USER_BIRTHDATE = "userBirthdate";
    public static final String ARG_USER_DESCRIPTION = "userDescription";

    public static UserDetailDialogFragment newInstance(UserModel user) {
        UserDetailDialogFragment fragment = new UserDetailDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, user.getUserID());
        args.putString(ARG_USER_NAME, user.getUserName());
        args.putString(ARG_USER_EMAIL, user.getUserEmail());
        args.putString(ARG_USER_BIRTHDATE, user.getBirthDate());
        args.putString(ARG_USER_DESCRIPTION, user.getDescription());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use the correct layout file name (should be user_profile_view, not user_list_details)
        View view = inflater.inflate(R.layout.user_list_details, container, false);

        TextView tvUserId = view.findViewById(R.id.tv_userID);
        TextView tvUserName = view.findViewById(R.id.tv_userName);
        TextView tvUserEmail = view.findViewById(R.id.tv_userEmail);
        TextView tvBirthdate = view.findViewById(R.id.tv_birthdate);
        TextView tvDescription = view.findViewById(R.id.tv_description);

        Bundle args = getArguments();
        if (args != null) {
            tvUserId.setText("ID: " + args.getInt(ARG_USER_ID));
            tvUserName.setText(args.getString(ARG_USER_NAME, ""));
            tvUserEmail.setText(args.getString(ARG_USER_EMAIL, ""));

            String birthdate = args.getString(ARG_USER_BIRTHDATE, "");
            if (birthdate != null && !birthdate.isEmpty()) {
                tvBirthdate.setText(birthdate);
            } else {
                tvBirthdate.setText("Not set");
            }

            String description = args.getString(ARG_USER_DESCRIPTION, "");
            if (description != null && !description.isEmpty()) {
                tvDescription.setText(description);
            } else {
                tvDescription.setText("No description available");
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}