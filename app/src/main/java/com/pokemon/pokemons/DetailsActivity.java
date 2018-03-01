package com.pokemon.pokemons;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pokemon.pokemons.entities.PokemonEntity;
import com.pokemon.pokemons.rest.models.PokemonDetailsModel;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{
    PokemonDetailsModel pokemonDetailsModel;
    PokemonEntity pokemonEntity;
    @BindView(R.id.pokemon_image)
    ImageView pokemonImage;

    @BindView(R.id.pokemon_name)
    TextView pokemonName;

    @BindView(R.id.save_pokemon_button)
    ImageView savePokemonButton;

    @BindView(R.id.states_list_text_view)
    TextView statesTextView;

    @BindView(R.id.abilities_list_text_view)
    TextView abilitiesTextView;

    @BindDrawable(R.drawable.ic_add_black_24dp)
    Drawable drawableAdd;

    @BindDrawable(R.drawable.ic_close_black_24dp)
    Drawable drawableRemove;

    Bundle bundle;
    boolean isSavedPokemon = false;
    // отображает характеристики покемона
    private void setPokemonLayout(){
        // меняем иконку на крестик, если в базе уже есть такой покемон и на плюс в противном случае
        savePokemonButton.setImageDrawable(getResources().getDrawable(isSavedPokemon ? R.drawable.ic_close_black_24dp:R.drawable.ic_add_black_24dp));
        MainActivity.setImageUrl(pokemonImage, this, Uri.parse(bundle.getString(getString(R.string.pokemon_image))), 0);
        pokemonName.setText(pokemonDetailsModel.getForms().get(0).getName());

        StringBuilder statesPokemon = new StringBuilder();
        StringBuilder abilitiesPokemon = new StringBuilder();

        for (int i = 0; i < pokemonDetailsModel.getStats().size(); i ++){
            statesPokemon.append(pokemonDetailsModel.getStats().get(i).getStat().getName())
                    .append(" = ")
                    .append(pokemonDetailsModel.getStats().get(i).getBaseStat())
                    .append("\n");
        }
        for (int i = 0; i < pokemonDetailsModel.getAbilities().size(); i ++){
            abilitiesPokemon.append(pokemonDetailsModel.getAbilities().get(i).getAbility().getName()).append("\n");
        }

        statesTextView.setText(statesPokemon);
        abilitiesTextView.setText(abilitiesPokemon);
    }
    //сохранить покемона в локальную базу данных если его нет, иначе удалаем
    private void saveOrDeletePokemon(){
        if (!isSavedPokemon) {
            pokemonEntity.insertPokemon(pokemonDetailsModel.getForms().get(0).getUrl(),
                    pokemonDetailsModel.getForms().get(0).getName(),
                    bundle.getString(getString(R.string.pokemon_image)),
                    pokemonDetailsModel);
            savePokemonButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_black_24dp));
        } else {
            pokemonEntity.deletePokemon(pokemonDetailsModel.getForms().get(0).getUrl());
            savePokemonButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
        }
        isSavedPokemon = !isSavedPokemon;
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.position_item), getIntent().getIntExtra(getString(R.string.position_item), 1));
        setResult(RESULT_OK, intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        ButterKnife.bind(this);
        pokemonEntity = new PokemonEntity();
        savePokemonButton.setOnClickListener(this);
        bundle = getIntent().getExtras();
        pokemonDetailsModel = (PokemonDetailsModel) bundle.getSerializable(getResources().getString(R.string.detail_pokemon));
        if (pokemonDetailsModel != null) {
            isSavedPokemon = pokemonEntity.isSaved(pokemonDetailsModel.getForms().get(0).getUrl());
        }
        setPokemonLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_pokemon_button:
                saveOrDeletePokemon();
                break;
        }
    }
}
