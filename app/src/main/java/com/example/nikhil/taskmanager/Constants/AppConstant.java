package com.example.nikhil.taskmanager.Constants;

import com.example.nikhil.taskmanager.model.Users;

import java.util.ArrayList;
import java.util.List;

public class AppConstant {

    public static class BundleKey {
        public static String TEAM_NAME = "team_name";
        public static final String FULL_NAME = "full_name";
        public static String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String CONFIRM_PASSWORD = "confirm_password";
        public static final String USER_NAME = "user_name";
        public static String nameOfTeam = "NAME_OF_TEAM";
        public static String Priority = "Low";
        public  static List<Users> dataOfUsers = new ArrayList<>();
    }
}
