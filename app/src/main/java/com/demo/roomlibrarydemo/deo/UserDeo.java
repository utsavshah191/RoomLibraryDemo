package com.demo.roomlibrarydemo.deo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.demo.roomlibrarydemo.model.User;

import java.util.List;




@Dao
public interface UserDeo {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user_data Order By id ASC")
    LiveData<List<User>> getAllUser();

    @Query("Delete from user_data")
    void deleteAll();

    @Query("delete from user_data where id =:userId")
    void singleDelete(int userId);

    @Query("update user_data set firstName=:userName, number=:userNumber where id=:userId")
    int update(int userId,String userName,String userNumber);

}
