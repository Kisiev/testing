package com.pokemon.pokemons.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokemon.pokemons.ConstantManager;
import com.pokemon.pokemons.DetailsActivity;
import com.pokemon.pokemons.R;
import com.pokemon.pokemons.adapters.RecyclerViewAdapter;
import com.pokemon.pokemons.entities.AbilitiesEntity;
import com.pokemon.pokemons.entities.AbilitiesEntity_Table;
import com.pokemon.pokemons.entities.PokemonEntity;
import com.pokemon.pokemons.entities.StatesEntity;
import com.pokemon.pokemons.entities.StatesEntity_Table;
import com.pokemon.pokemons.rest.models.Abilities;
import com.pokemon.pokemons.rest.models.Ability;
import com.pokemon.pokemons.rest.models.Form;
import com.pokemon.pokemons.rest.models.PokemonDetailsModel;
import com.pokemon.pokemons.rest.models.PokemonModel;
import com.pokemon.pokemons.rest.models.PokemonResult;
import com.pokemon.pokemons.rest.models.Stat;
import com.pokemon.pokemons.rest.models.States;
import com.pokemon.pokemons.utils.ClickListener;
import com.pokemon.pokemons.utils.RecyclerTouchListener;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PokedexFragment extends Fragment {
    @BindView(R.id.discover_recycler_view)
    RecyclerView recyclerView;
    PokemonEntity pokemonEntity;
    AbilitiesEntity abilitiesEntity;
    StatesEntity statesEntity;
    List<PokemonEntity> pokemonList;
    List<StatesEntity> pokemonStates;
    List<AbilitiesEntity> pokemonAbilities;
    PokemonModel pokemonModels;
    List<PokemonResult> pokemonResults;
    PokemonDetailsModel pokemonDetails;

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    private void setRecyclerViewAdapter(){
        recyclerView.setAdapter(new RecyclerViewAdapter(pokemonModels, getActivity()));
    }
    public void selectAllPokemons(){
        RXSQLite.rx(SQLite.select().from(PokemonEntity.class))
                .queryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PokemonEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<PokemonEntity> pokemonEntities) {
                        pokemonList = pokemonEntities;
                        getPokemons();
                        setRecyclerViewAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    // подгружаем список покемонов из локальной базы
    private void getPokemons(){
        pokemonModels = new PokemonModel();
        pokemonResults = new ArrayList<>();
        for (int i = 0; i <  pokemonList.size(); i ++){
            pokemonResults.add(new PokemonResult());
            pokemonResults.get(i).setName(pokemonList.get(i).getName());
            pokemonResults.get(i).setUrl(pokemonList.get(i).getUrl());
            pokemonResults.get(i).setImage(pokemonList.get(i).getImageUrl());
        }
        pokemonModels.setResults(pokemonResults);
    }
    // подгружаем способности выбранного покемона
    private void getAbilities(final String url, final int pos){
        RXSQLite.rx(SQLite.select().from(AbilitiesEntity.class).where(AbilitiesEntity_Table.urlPokemon.eq(url)))
                .queryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<AbilitiesEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<AbilitiesEntity> abilitiesEntities) {
                        pokemonAbilities = abilitiesEntities;
                        getStates(url, pos);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    // подгружаем характеристики выбранного покемона
    private void getStates(String url, final int pos){
        RXSQLite.rx(SQLite.select().from(StatesEntity.class).where(StatesEntity_Table.urlPokemon.eq(url)))
                .queryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<StatesEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<StatesEntity> statesEntities) {
                pokemonStates = statesEntities;
                getPokemonDetails(pos);
                startActivity(pos);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
    // Создаем единый объект для передачи его в DetailActivity
    private void getPokemonDetails(int pos){
        pokemonDetails = new PokemonDetailsModel();
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
    }
    private void startActivity(int pos){
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
                getAbilities(pokemonList.get(position).getUrl(), position);
            }
        }));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_layout, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        statesEntity = new StatesEntity();
        abilitiesEntity = new AbilitiesEntity();
        pokemonEntity = new PokemonEntity();
        selectAllPokemons();
        setRecyclerViewClick();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == ConstantManager.REQUEST_ACTIVITY){
                selectAllPokemons();
            }
        }
    }
}
