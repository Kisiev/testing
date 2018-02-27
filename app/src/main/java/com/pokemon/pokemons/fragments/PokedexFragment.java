package com.pokemon.pokemons.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokemon.pokemons.ConstantManager;
import com.pokemon.pokemons.DetailsActivity;
import com.pokemon.pokemons.R;
import com.pokemon.pokemons.adapters.RecyclerViewPokedexAdapter;
import com.pokemon.pokemons.entities.AbilitiesEntity;
import com.pokemon.pokemons.entities.PokemonEntity;
import com.pokemon.pokemons.entities.StatesEntity;
import com.pokemon.pokemons.rest.models.Abilities;
import com.pokemon.pokemons.rest.models.Ability;
import com.pokemon.pokemons.rest.models.Form;
import com.pokemon.pokemons.rest.models.PokemonDetailsModel;
import com.pokemon.pokemons.rest.models.Stat;
import com.pokemon.pokemons.rest.models.States;
import com.pokemon.pokemons.utils.ClickListener;
import com.pokemon.pokemons.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokedexFragment extends Fragment {
    @BindView(R.id.discover_recycler_view)
    RecyclerView recyclerView;

    List<PokemonEntity> pokemonList;
    List<StatesEntity> pokemonStates;
    List<AbilitiesEntity> pokemonAbilities;

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    private void setRecyclerViewAdapter(){
        recyclerView.setAdapter(new RecyclerViewPokedexAdapter(pokemonList, getActivity()));
    }
    // подгружаем список покемонов из локальной базы
    private void getPokemons(){
        pokemonList = PokemonEntity.selectAllPokemons();
    }
    // подгружаем способности выбранного покемона
    private void getAbilities(String url){
        pokemonAbilities = AbilitiesEntity.selectAllAbilities(url);
    }
    // подгружаем характеристики выбранного покемона
    private void getStates(String url){
        pokemonStates = StatesEntity.selectAllStates(url);
    }
    // Создаем единый объект для передачи его в DetailActivity
    private void getPokemonDetails(int pos){
        PokemonDetailsModel pokemonDetails = new PokemonDetailsModel();
        List<Abilities> abilities = new ArrayList<>();
        List<States> states = new ArrayList<>();
        for (int i = 0; i < pokemonAbilities.size(); i ++){
            abilities.add(new Abilities());
            Ability ability = new Ability();
            ability.setName(pokemonAbilities.get(i).getAbilities());
            abilities.get(i).setAbility(ability);
        }
        for (int i = 0; i < pokemonStates.size(); i ++){
            states.add(new States());
            Stat stat = new Stat();
            stat.setName(pokemonStates.get(i).getStatesName());
            states.get(i).setStat(stat);
            states.get(i).setBaseStat(Integer.parseInt(pokemonStates.get(i).getStatesDamage()));
        }

        List<Form> forms = new ArrayList<>();
        forms.add(new Form());
        forms.get(0).setName(pokemonList.get(pos).getName());
        forms.get(0).setUrl(pokemonList.get(pos).getUrl());

        pokemonDetails.setAbilities(abilities);
        pokemonDetails.setStats(states);
        pokemonDetails.setForms(forms);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(getString(R.string.detail_pokemon), pokemonDetails);
        intent.putExtra(getString(R.string.pokemon_image), pokemonList.get(pos).getImageUrl());
        intent.putExtra(getString(R.string.position_item), pos);
        startActivityForResult(intent, ConstantManager.REQUEST_ACTIVITY);
    }
    // отображаем выбранного покемона на странице DetailActivity
    private void setRecyclerViewClick(){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                getAbilities(pokemonList.get(position).getUrl());
                getStates(pokemonList.get(position).getUrl());
                getPokemonDetails(position);
            }
        }));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_layout, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        getPokemons();
        setRecyclerViewAdapter();
        setRecyclerViewClick();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == ConstantManager.REQUEST_ACTIVITY){
                int position = data.getIntExtra(getString(R.string.position_item), 1);
                getAbilities(pokemonList.get(position).getUrl());
                getStates(pokemonList.get(position).getUrl());
                getPokemons();
                setRecyclerViewAdapter();
            }
        }
    }
}
