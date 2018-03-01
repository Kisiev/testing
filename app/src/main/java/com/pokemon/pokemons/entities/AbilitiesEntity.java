package com.pokemon.pokemons.entities;

import android.os.AsyncTask;

import com.pokemon.pokemons.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.AsyncModel;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Table(database = AppDatabase.class)
public class AbilitiesEntity extends BaseModel {
    List<AbilitiesEntity> abilitiesEntityList;
    @PrimaryKey
    private String id;

    @Column()
    private String urlPokemon;

    @Column()
    private String abilities;

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

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public void insertAbilities(String urlPokemon, String abilities){
        SQLite.insert(AbilitiesEntity.class).columns("urlPokemon", "abilities")
                .values(urlPokemon, abilities).execute();
    }
    public void deleteAbilities(String urlPokemon){
        SQLite.delete().from(AbilitiesEntity.class).where(AbilitiesEntity_Table.urlPokemon.eq(urlPokemon)).async().execute();
    }
}
