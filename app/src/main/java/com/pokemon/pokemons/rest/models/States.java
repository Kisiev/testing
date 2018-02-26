package com.pokemon.pokemons.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class States implements Serializable {

   @SerializedName("stat")
   @Expose
   private Stat stat;
   @SerializedName("effort")
   @Expose
   private int effort;
   @SerializedName("base_stat")
   @Expose
   private int baseStat;

   public Stat getStat() {
       return stat;
   }

   public void setStat(Stat stat) {
       this.stat = stat;
   }

   public int getEffort() {
       return effort;
   }

   public void setEffort(int effort) {
       this.effort = effort;
   }

   public int getBaseStat() {
       return baseStat;
   }

   public void setBaseStat(int baseStat) {
       this.baseStat = baseStat;
   }

}
