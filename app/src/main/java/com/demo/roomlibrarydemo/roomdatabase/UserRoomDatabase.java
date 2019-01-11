package com.demo.roomlibrarydemo.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import com.demo.roomlibrarydemo.deo.UserDeo;
import com.demo.roomlibrarydemo.model.User;



@Database(entities = {User.class},version = 3)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDeo userDeo();
    private static volatile UserRoomDatabase INSTANCE;
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'user_data' "
                    + " ADD COLUMN 'age' INTEGER DEFAULT 0 NOT NULL");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'user_data' "
                    + " ADD COLUMN 'address' TEXT");
        }
    };
    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "user_database").addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
