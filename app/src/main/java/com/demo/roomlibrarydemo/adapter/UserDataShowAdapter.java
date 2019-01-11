package com.demo.roomlibrarydemo.adapter;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.roomlibrarydemo.R;
import com.demo.roomlibrarydemo.model.User;
import com.demo.roomlibrarydemo.utils.Utilities;
import com.demo.roomlibrarydemo.viewmodel.UserViewModel;

import java.util.List;




public class UserDataShowAdapter extends RecyclerView.Adapter<UserDataShowAdapter.UserDataViewHolder> {
    private Context context;
    private List<User> lstuserdata;
    private UserViewModel userViewModel;

    public UserDataShowAdapter(Context context, UserViewModel userViewModel) {
        this.context = context;
        this.userViewModel = userViewModel;
    }

    @Override
    public UserDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_user_data, parent, false);
        return new UserDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserDataViewHolder holder, int position) {
        if (lstuserdata != null) {
            final User user = lstuserdata.get(position);
            holder.textViewFirstName.setText(user.getFirstName());
            holder.textViewPhoneNo.setText(user.getNumber());
            int age = user.getAge();
            holder.textViewAge.setText(String.valueOf(age));
            holder.textViewPinCode.setText(user.getAddress());
            holder.linearLayoutUserData.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    userViewModel.deleteSingle(user.getId());
                    return false;
                }
            });
            holder.linearLayoutUserData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_user_data, null);
                    alertDialog.setView(dialogView);
                    alertDialog.setCancelable(false);
                    final EditText editTextName = dialogView.findViewById(R.id.dialogEtName);
                    final EditText editTextPhone = dialogView.findViewById(R.id.dialogEtPhone);
                    Button buttonSave = dialogView.findViewById(R.id.dialog_button_save);
                    final String name = editTextName.getText().toString();
                    final String phone = editTextName.getText().toString();
                    buttonSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(name)) {
                                editTextName.setError(context.getResources().getString(R.string.please_enter_you_name));
                                return;
                            }
                            if (TextUtils.isEmpty(phone)) {
                                editTextPhone.setError(context.getResources().getString(R.string.please_enter_your_phone_number));
                                return;
                            }

                            int userid = user.getId();
                            User user = new User();
                            user.setNumber(phone);
                            user.setFirstName(name);
                            user.setId(userid);
                            userViewModel.updateSingleUser(user);

                            alertDialog.dismiss();

                        }
                    });

                    alertDialog.show();
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            Utilities.debug("Adapter", "no data");

        }
    }

    public void setUsers(List<User> users) {
        lstuserdata = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (lstuserdata != null) {
            return lstuserdata.size();
        } else {

            return 0;
        }
    }

    static class UserDataViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFirstName, textViewPhoneNo, textViewAge, textViewPinCode;
        LinearLayout linearLayoutUserData;

        UserDataViewHolder(View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.recycleTvUserName);
            textViewPhoneNo = itemView.findViewById(R.id.recycleTvPhoneNo);
            textViewAge = itemView.findViewById(R.id.recycleTvAge);
            textViewPinCode = itemView.findViewById(R.id.recycleTvPincode);
            linearLayoutUserData = itemView.findViewById(R.id.llUserData);
        }
    }
}
