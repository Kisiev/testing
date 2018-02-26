package com.pokemon.pokemons.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokemon.pokemons.MainActivity;
import com.pokemon.pokemons.R;
import com.pokemon.pokemons.entities.PokemonEntity;
import com.pokemon.pokemons.rest.models.PokemonModel;

import java.util.List;

public class RecyclerViewPokedexAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    List<PokemonEntity> pokemonEntities;
    Context context;
    public RecyclerViewPokedexAdapter(List<PokemonEntity> pokemonEntities, Context context){
        this.pokemonEntities = pokemonEntities;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.pokemonName.setText(pokemonEntities.get(position).getName());
        MainActivity.setImageUrl(holder.pokemonImage, context, Uri.parse(pokemonEntities.get(position).getImageUrl()), 0);
    }

    @Override
    public int getItemCount() {
        return pokemonEntities.size();
    }
}
