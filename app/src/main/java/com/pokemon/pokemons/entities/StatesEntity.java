package com.pokemon.pokemons.entities;

import com.pokemon.pokemons.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class StatesEntity extends BaseModel {

    @PrimaryKey
    private String id;

    @Column()
    private String urlPokemon;

    @Column()
    private String statesName;

    @Column()
    private String statesDamage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlPokemon() {
        return urlPokemon;
    }

    public void setUrlPokemon(String urlPokemon) {
        this.urlPokemon = urlPokemon;
    }

    public String getStatesName() {
        return statesName;
    }

    public void setStatesName(String statesName) {
        this.statesName = statesName;
    }

    public String getStatesDamage() {
        return statesDamage;
    }

    public void setStatesDamage(String statesDamage) {
        this.statesDamage = statesDamage;
    }

    public static void insertStates(String urlPokemon, String statesName, String statesDamage){
        SQLite.insert(StatesEntity.class).columns("urlPokemon", "statesName", "statesDamage")
                .values(urlPokemon, statesName, statesDamage)
                .execute();
    }
    public static List<StatesEntity> selectAllStates(String url){
        return SQLite.select().from(StatesEntity.class).where(StatesEntity_Table.urlPokemon.eq(url)).queryList();
    }

    public static void deleteStates(String urlPokemon){
        SQLite.delete().from(StatesEntity.class).where(StatesEntity_Table.urlPokemon.eq(urlPokemon)).execute();
    }
}
