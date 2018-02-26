package com.pokemon.pokemons.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemon.pokemons.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    ImageView pokemonImage;
    TextView pokemonName;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        pokemonImage = itemView.findViewById(R.id.discover_pokemon_image);
        pokemonName = itemView.findViewById(R.id.discover_pokemon_name);
    }
}