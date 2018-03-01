package com.pokemon.pokemons.entities;

import com.pokemon.pokemons.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Table(database = AppDatabase.class)
public class StatesEntity extends BaseRXModel {

    List<StatesEntity> entities;

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

    public void insertStates(String urlPokemon, String statesName, String statesDamage){
        SQLite.insert(StatesEntity.class).columns("urlPokemon", "statesName", "statesDamage")
                .values(urlPokemon, statesName, statesDamage)
                .execute();
    }

    public void deleteStates(String urlPokemon){
        SQLite.delete().from(StatesEntity.class).where(StatesEntity_Table.urlPokemon.eq(urlPokemon)).async().execute();
    }
}
