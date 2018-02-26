package com.pokemon.pokemons.entities;

import com.pokemon.pokemons.AppDatabase;
import com.pokemon.pokemons.rest.models.Abilities;
import com.pokemon.pokemons.rest.models.PokemonDetailsModel;
import com.pokemon.pokemons.rest.models.States;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Table(database = AppDatabase.class)
public class PokemonEntity extends BaseModel{

    @PrimaryKey
    String id;

    @Column()
    public String url;

    @Column()
    private String name;

    @Column()
    private String imageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static void insertPokemon(String url, String name, String imageUrl, PokemonDetailsModel pokemonDetailsModel){
        SQLite.insert(PokemonEntity.class).columns("url", "name", "imageUrl")
                .values(url, name, imageUrl).execute();
        for (States i : pokemonDetailsModel.getStats()){
            StatesEntity.insertStates(url, i.getStat().getName(), String.valueOf(i.getBaseStat()));
        }
        for (Abilities i : pokemonDetailsModel.getAbilities()){
            AbilitiesEntity.insertAbilities(url, i.getAbility().getName());
        }
    }

    public static void deletePokemon(String url){
        SQLite.delete().from(PokemonEntity.class).where(PokemonEntity_Table.url.eq(url)).execute();
        StatesEntity.deleteStates(url);
        AbilitiesEntity.deleteAbilities(url);
    }

    public static boolean isSaved(String url){
        if(SQLite.select().from(PokemonEntity.class).where(PokemonEntity_Table.url.eq(url)).queryList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static List<PokemonEntity> selectAllPokemons(){
        return SQLite.select().from(PokemonEntity.class)
                .queryList();
    }
}
