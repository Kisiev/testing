package com.pokemon.pokemons.fragments;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.pokemon.pokemons.ConstantManager;
import com.pokemon.pokemons.DetailsActivity;
import com.pokemon.pokemons.R;
import com.pokemon.pokemons.adapters.RecyclerViewAdapter;
import com.pokemon.pokemons.rest.RestService;
import com.pokemon.pokemons.rest.models.PokemonDetailsModel;
import com.pokemon.pokemons.rest.models.PokemonModel;
import com.pokemon.pokemons.rest.models.PokemonResourceModel;
import com.pokemon.pokemons.utils.ClickListener;
import com.pokemon.pokemons.utils.RecyclerTouchListener;
import com.pokemon.pokemons.utils.SharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DiscoverFragment extends Fragment implements Animation.AnimationListener{
    int offsetList = ConstantManager.OFFSET_LIST;
    Observable<PokemonModel> pokemonModel;
    Observable<PokemonDetailsModel> pokemonDetails;
    PokemonResourceModel pokemonResourceModel;
    RestService restService;
    PokemonModel pokemonList;
    SharedPreference sharedPreference;

    @BindView(R.id.discover_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.preloader_layout)
    View preloaderLayout;

    @BindAnim(R.anim.rotate_loader)
    Animation rotateAnim;

    @BindView(R.id.loader_image)
    ImageView rotateImage;

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
    private void setRecyclerAdapter(PokemonModel pokemonList){
        recyclerView.setAdapter(new RecyclerViewAdapter(pokemonList, getActivity()));
    }
    private void setRecyclerViewClick(){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String[] s = pokemonList.getResults().get(position).getUrl().split("/");
                try {rotateImage.setVisibility(View.VISIBLE);
                    getPokemonDetails(s[s.length - 1], pokemonList.getResults().get(position).getImage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
    // если скрол дошел до конца, то подгружаем еще покемонов
    private void setRecyclerViewScroll(){
        final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (gridLayoutManager.findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 1){
                    try {
                        if (!sharedPreference.getStatusSubscribe()){
                            sharedPreference.setStatusSubscribe(true);
                            preloaderLayout.setVisibility(View.VISIBLE);
                            getPokemons(true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    // подгружает с сервера характеристики выбранного покемона
    private void getPokemonDetails(String id, final String imageUrl) throws IOException {
        preloaderLayout.setVisibility(View.VISIBLE);
        pokemonDetails = restService.getPokemonDetails(id);
        pokemonDetails.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PokemonDetailsModel>() {
                    PokemonDetailsModel pokemonDetals;
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PokemonDetailsModel pokemonDetals) {
                        this.pokemonDetals = pokemonDetals;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        preloaderLayout.setVisibility(View.GONE);
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra(getString(R.string.detail_pokemon), pokemonDetals);
                        intent.putExtra(getString(R.string.pokemon_image), imageUrl);
                        getActivity().startActivity(intent);
                    }
                });

    }
    // достаем список покемонов
    private void getPokemons(final boolean isHoldItems) throws IOException {
        if (isHoldItems) {
            pokemonModel = restService.getAllPokemons(String.valueOf(ConstantManager.LIMIT_POKEMON), String.valueOf(offsetList));
            offsetList += ConstantManager.LIMIT_POKEMON;
        }
        else pokemonModel = restService.getAllPokemons(String.valueOf(ConstantManager.LIMIT_POKEMON), String.valueOf(0));
        pokemonModel.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PokemonModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PokemonModel pokemonModel) {
                        if (isHoldItems)
                            pokemonList.getResults().addAll(pokemonModel.getResults());
                        else
                            pokemonList = pokemonModel;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        preloaderLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        getPokemonResources(isHoldItems ? pokemonList.getResults().size() - ConstantManager.LIMIT_POKEMON : 0);
                    }
                });

    }
    // достаем с сервера иконки покемонов
    private void getPokemonResources(int startIndex){
        Observable<Integer> pokemonModelObservable = Observable.range(startIndex, pokemonList.getResults().size() - startIndex);
        pokemonModelObservable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer index) {
                        try {
                            String[] splitUrl = pokemonList.getResults().get(index).getUrl().split("/");
                            pokemonResourceModel = restService.getPokemonResource(splitUrl[splitUrl.length - 1]);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pokemonList.getResults().get(index).setImage((pokemonResourceModel.getSprites().getFrontDefault()));
                        Log.d("111", pokemonList.getResults().get(index).getImage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        sharedPreference.setStatusSubscribe(false);
                    }

                    @Override
                    public void onComplete() {
                        AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                            @Override
                            public void run() {
                                setRecyclerAdapter(pokemonList);
                                preloaderLayout.setVisibility(View.GONE);
                                sharedPreference.setStatusSubscribe(false);
                                recyclerView.getLayoutManager().scrollToPosition(offsetList - ConstantManager.LIMIT_POKEMON - 2);
                            }
                        });
                    }
                });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_layout, container, false);
        restService = new RestService();
        sharedPreference = new SharedPreference(getActivity());
        ButterKnife.bind(this, view);
        initRecyclerView();
        setRecyclerViewClick();
        setRecyclerViewScroll();
        rotateAnim.setAnimationListener(this);
        preloaderLayout.setVisibility(View.VISIBLE);
        rotateImage.startAnimation(rotateAnim);
        try {
            if (!sharedPreference.getStatusSubscribe()) { // Если не идет загрузка с сервера, то загружаем. Переход на другую вкладку не отменяет загрузку.
                sharedPreference.setStatusSubscribe(true);
                getPokemons(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        animation.reset();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animation.start();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
