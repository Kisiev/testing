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
    List<String> pokemonImageList;
    Context context;
    public RecyclerViewAdapter(PokemonModel pokemonsList, List<String> pokemonImageList, Context context){
        this.pokemonsList = pokemonsList;
        this.pokemonImageList = pokemonImageList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item, parent, false);
       return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.pokemonName.setText(pokemonsList.getResults().get(position).getName());
        MainActivity.setImageUrl(holder.pokemonImage, context, Uri.parse(pokemonImageList.get(position)), 0);
    }

    @Override
    public int getItemCount() {
        return pokemonsList.getResults().size();
    }

}
