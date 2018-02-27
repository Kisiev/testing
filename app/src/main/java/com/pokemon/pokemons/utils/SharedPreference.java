package com.pokemon.pokemons.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pokemon.pokemons.ConstantManager;

public class SharedPreference {
    SharedPreferences sharedPreferences;
    public SharedPreference(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setStatusSubscribe(boolean status){
        sharedPreferences.edit().putBoolean(ConstantManager.SUBSCRIBE_STATUS, status).apply();
    }
    public boolean getStatusSubscribe(){
        return sharedPreferences.getBoolean(ConstantManager.SUBSCRIBE_STATUS, false);
    }
}
