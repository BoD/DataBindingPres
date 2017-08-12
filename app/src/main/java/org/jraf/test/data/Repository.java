package org.jraf.test.data;

import android.os.SystemClock;

import org.jraf.test.model.User;

public class Repository {
    public static User findUser(long userId) {
        return new User("John", "Smith", "jsmith@gmail.com");
    }

    public static void insertOrUpdateUser(User user) {
        SystemClock.sleep(1000);
    }
}
