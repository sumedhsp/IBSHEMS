package com.example.sumedhsp2.esep;

import android.content.Context;

public class PreferenceClass {
        String username;
        public PreferenceClass(){

        }
        public static boolean saveUsername(String username, Context context){
            return true;        }
        public static String getUsername(Context context){
            return "Sumedh";
        }
}
