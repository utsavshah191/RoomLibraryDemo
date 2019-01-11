package com.demo.roomlibrarydemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demo.roomlibrarydemo.adapter.UserDataShowAdapter;
import com.demo.roomlibrarydemo.model.User;
import com.demo.roomlibrarydemo.viewmodel.UserViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvShowUserData;
    private EditText editTextName, editTextPhoneNumber, editTextAge, editTextPincode;
    private Button buttonSave, buttonDeleteAll;
    private UserViewModel userViewModel;
    private UserDataShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        adapter = new UserDataShowAdapter(this, userViewModel);
        rvShowUserData.setAdapter(adapter);
        rvShowUserData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rvShowUserData.setLayoutManager(new LinearLayoutManager(this));
        buttonDeleteAll.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                adapter.setUsers(users);
            }
        });

    }

    /*
    * Initalize all controls
    * */
    private void init() {
        editTextName = findViewById(R.id.edtName);
        editTextAge = findViewById(R.id.edtAge);
        editTextPhoneNumber = findViewById(R.id.edtPhoneNumber);
        editTextPincode = findViewById(R.id.edtPincode);
        buttonSave = findViewById(R.id.btnSave);
        buttonDeleteAll = findViewById(R.id.btnDeleteAll);
        rvShowUserData = findViewById(R.id.rclvShowUserData);

    }

    /*
    * Validating user input
    * */
    private void validateUser() {
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhoneNumber.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String pincode = editTextPincode.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("enter name");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editTextPhoneNumber.setError("enter phone");
            return;
        }

        User user = new User();
        user.setFirstName(name);
        user.setNumber(phone);
        user.setAddress(pincode);
        user.setAge(Integer.parseInt(age));
        userViewModel.insert(user);
        editTextPhoneNumber.setText("");
        editTextName.setText("");
        editTextAge.setText("");
        editTextPincode.setText("");
    }

    /*
    * Displaying delete all dialog
    * */
    private void displayDeleteConformationAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                if (userViewModel != null) {
                    userViewModel.deleteTable();
                    dialog.dismiss();
                }


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                validateUser();
                break;
            case R.id.btnDeleteAll:
                displayDeleteConformationAlertDialog();
                break;
        }
    }
}
