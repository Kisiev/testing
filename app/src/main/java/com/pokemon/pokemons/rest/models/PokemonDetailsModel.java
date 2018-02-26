package com.pokemon.pokemons.rest.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PokemonDetailsModel implements Serializable{
    @SerializedName("forms")
    @Expose
    private List<Form> forms = new ArrayList<>();
    @SerializedName("abilities")
    @Expose
    private List<Abilities> abilities = new ArrayList<>();
    @SerializedName("stats")
    @Expose
    private List<States> stats = new ArrayList<>();

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    public List<States> getStats() {
        return stats;
    }

    public void setStats(List<States> stats) {
        this.stats = stats;
    }
}

