package com.pokemon.pokemons.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonResourceModel {
    @SerializedName("is_battle_only")
    @Expose
    private boolean isBattleOnly;
    @SerializedName("sprites")
    @Expose
    private Sprites sprites;
    @SerializedName("version_group")
    @Expose
    private VersionGroup versionGroup;
    @SerializedName("form_order")
    @Expose
    private int formOrder;
    @SerializedName("is_mega")
    @Expose
    private boolean isMega;
    @SerializedName("form_names")
    @Expose
    private List<Object> formNames = null;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("is_default")
    @Expose
    private boolean isDefault;
    @SerializedName("names")
    @Expose
    private List<Object> names = null;
    @SerializedName("form_name")
    @Expose
    private String formName;
    @SerializedName("pokemon")
    @Expose
    private Pokemon pokemon;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("name")
    @Expose
    private String name;

    public boolean isIsBattleOnly() {
        return isBattleOnly;
    }

    public void setIsBattleOnly(boolean isBattleOnly) {
        this.isBattleOnly = isBattleOnly;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public VersionGroup getVersionGroup() {
        return versionGroup;
    }

    public void setVersionGroup(VersionGroup versionGroup) {
        this.versionGroup = versionGroup;
    }

    public int getFormOrder() {
        return formOrder;
    }

    public void setFormOrder(int formOrder) {
        this.formOrder = formOrder;
    }

    public boolean isIsMega() {
        return isMega;
    }

    public void setIsMega(boolean isMega) {
        this.isMega = isMega;
    }

    public List<Object> getFormNames() {
        return formNames;
    }

    public void setFormNames(List<Object> formNames) {
        this.formNames = formNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public List<Object> getNames() {
        return names;
    }

    public void setNames(List<Object> names) {
        this.names = names;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
