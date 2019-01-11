package com.demo.roomlibrarydemo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.demo.roomlibrarydemo.model.User;
import com.demo.roomlibrarydemo.repository.UserRepository;

import java.util.List;




public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> listLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        listLiveData = userRepository.getListLiveData();

    }

    public LiveData<List<User>> getAllUser() {
        return listLiveData;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void deleteTable() {
        userRepository.deleteAll();
    }

    public void deleteSingle(int user) {
        userRepository.deleteSingleUser(user);
    }

    public void updateSingleUser(User user) {
        userRepository.updateUser(user);
    }
}
