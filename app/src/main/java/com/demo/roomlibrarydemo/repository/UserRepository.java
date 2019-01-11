package com.demo.roomlibrarydemo.repository;

import android.app.Application;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.demo.roomlibrarydemo.deo.UserDeo;
import com.demo.roomlibrarydemo.model.User;
import com.demo.roomlibrarydemo.roomdatabase.UserRoomDatabase;

import java.util.List;




public class UserRepository {
    private UserDeo userDeo;
    private LiveData<List<User>> listLiveData;

    public UserRepository(Application application) {
        UserRoomDatabase database = UserRoomDatabase.getDatabase(application);
        userDeo = database.userDeo();
        listLiveData = userDeo.getAllUser();

    }

    public LiveData<List<User>> getListLiveData() {
        return listLiveData;
    }

    public void insert(User user) {
        new insertAsyncTask(userDeo).execute(user);
    }

    public void deleteAll() {
        new deleteTableAsyncTask(userDeo).execute();
    }

    public void deleteSingleUser(int user) {
        new deleteAsyncTask(userDeo).execute(user);
    }

    public void updateUser(User user) {
        new updateAsyncTask(userDeo).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDeo mAsyncTaskDao;

        insertAsyncTask(UserDeo userDeo) {
            mAsyncTaskDao = userDeo;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }

    private static class deleteTableAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDeo mAsyncTaskDao;

        deleteTableAsyncTask(UserDeo userDeo) {
            mAsyncTaskDao = userDeo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private UserDeo mAsyncTaskDao;

        deleteAsyncTask(UserDeo userDeo) {
            mAsyncTaskDao = userDeo;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.singleDelete(integers[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDeo mAsyncTaskDao;
        private String name;
        private String phone;
        private int id;

        updateAsyncTask(UserDeo mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;

        }

        @Override
        protected Void doInBackground(User... users) {
            int id = users[0].getId();
            String name = users[0].getFirstName();
            String phone = users[0].getNumber();
            mAsyncTaskDao.update(id, name, phone);
            return null;
        }
    }
}
