package com.pokemon.pokemons.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemon.pokemons.MainActivity;
import com.pokemon.pokemons.R;
import com.pokemon.pokemons.rest.models.PokemonModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    PokemonModel pokemonsList;
    Context context;
    public RecyclerViewAdapter(PokemonModel pokemonsList, Context context){
        this.pokemonsList = pokemonsList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item, parent, false);
       return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (pokemonsList.getResults().get(position).getImage() != null) {
            holder.pokemonName.setText(pokemonsList.getResults().get(position).getName());
            MainActivity.setImageUrl(holder.pokemonImage, context, Uri.parse(pokemonsList.getResults().get(position).getImage()), 0);
        }
    }

    @Override
    public int getItemCount() {
        return pokemonsList.getResults().size();
    }

}
