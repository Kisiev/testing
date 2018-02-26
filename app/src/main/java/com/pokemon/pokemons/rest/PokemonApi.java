package com.pokemon.pokemons.rest;

import com.pokemon.pokemons.rest.models.PokemonDetailsModel;
import com.pokemon.pokemons.rest.models.PokemonModel;
import com.pokemon.pokemons.rest.models.PokemonResourceModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApi {

    @GET("/api/v2/pokemon")
    Observable<PokemonModel> getAllPokemons(@Query("limit") String limit,
                                            @Query("offset") String offset);

    @GET("/api/v2/pokemon")
    Observable<PokemonModel> getAllPokemons();

    @GET("/api/v2/pokemon/{pokemon-id}/")
    Observable<PokemonDetailsModel> getPokemonDetails(@Path("pokemon-id") String id);

    @GET("/api/v2/pokemon-form/{pokemon-id}/")
    Call<PokemonResourceModel> getPokemonResource(@Path("pokemon-id") String id);
}
