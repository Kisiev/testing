package com.pokemon.pokemons.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Abilities implements Serializable {

    @SerializedName("slot")
    @Expose
    private int slot;
    @SerializedName("is_hidden")
    @Expose
    private boolean isHidden;
    @SerializedName("ability")
    @Expose
    private Ability ability;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

}
