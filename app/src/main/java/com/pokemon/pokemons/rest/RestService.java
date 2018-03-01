package com.pokemon.pokemons.rest;

import com.pokemon.pokemons.rest.models.PokemonDetailsModel;
import com.pokemon.pokemons.rest.models.PokemonModel;
import com.pokemon.pokemons.rest.models.PokemonResourceModel;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Call;

public class RestService {
    private RestClient restClient;

    public RestService(){
        if (restClient == null)
            restClient = new RestClient();
    }
    // подгрузить первые 20 записей с сервера
    public Observable<PokemonModel> getAllPokemons() throws IOException {
        return restClient.getPokemonApi().getAllPokemons();
    }
    // подгрузить limit запеисей с сервера с отступом offset
    public Observable<PokemonModel> getAllPokemons(String limit, String offset) throws IOException {
        return restClient.getPokemonApi().getAllPokemons(limit, offset);
    }
    // подгрузить характеристики покемона
    public Observable<PokemonDetailsModel> getPokemonDetails(String pokemonId) throws IOException {
        return restClient.getPokemonApi().getPokemonDetails(pokemonId);
    }
    // подгрузить иконку покемона
    public PokemonResourceModel getPokemonResource(String pokemonId) throws IOException {
        return restClient.getPokemonApi().getPokemonResource(pokemonId).execute().body();
    }
}
