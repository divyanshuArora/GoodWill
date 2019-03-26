    package com.arora.divyanshu.goodwill;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

    /**
     * Created by server3 on 4/15/2017.
     */
    public class SessionManager {


        Context context;
        SharedPreferences pref;
        Editor editor;
        // Constructor
        public SessionManager(Context context){
            this.context = context;
            pref = this.context.getSharedPreferences("My_Pref", Context.MODE_PRIVATE);
            editor = pref.edit();
        }




        public void addString(String key, String str)
        {
          editor.putString(key,str);
            editor.commit();
        }

        public String getString(String key)
        {

            return  pref.getString(key,"");
        }

        public void addBoolean(String key, boolean str)
        {
            editor.putBoolean(key,str);
            editor.commit();
        }

        public boolean getBoolean(String key)
        {

            return  pref.getBoolean(key,false);
        }

        public void addInt(String key, int value)
        {
            editor.putInt(key,value);
            editor.commit();
        }

        public int getInt(String key)
        {

            return  pref.getInt(key,0);
        }

        public  void clear()
        {
            editor.clear();
            editor.commit();

        }

    }
