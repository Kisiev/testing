package com.pokemon.pokemons;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.pokemon.pokemons.fragments.DiscoverFragment;
import com.pokemon.pokemons.fragments.PokedexFragment;
import com.pokemon.pokemons.utils.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    SharedPreference sharedPreference;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreference = new SharedPreference(this);
        sharedPreference.setStatusSubscribe(false);
        navigationMenu.setOnNavigationItemSelectedListener(this);
        replaceFragment(new DiscoverFragment(), R.id.fragment_container);
    }
    // отображать переданный фрагмент в заданном лейауте
    public void replaceFragment(Fragment fragment, int id) {
        String backStackName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);
        FragmentTransaction ft = manager.beginTransaction();
        if (!fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
            ft.replace(id, fragment, backStackName);
            ft.addToBackStack(backStackName);
            ft.commit();
        }
    }
    //  подгружет в imageView картинку
    public static void setImageUrl(ImageView imageView, Context context, Uri imagePath, int imageResource) {
        Glide.with(context)
            .load(imagePath == null ? imageResource: imagePath)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(new BitmapImageViewTarget(imageView).getView());
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.discover_button:
                replaceFragment(new DiscoverFragment(), R.id.fragment_container);
                break;
            case R.id.pokedex_button:
                replaceFragment(new PokedexFragment(), R.id.fragment_container);
                break;
        }
        return false;
    }
}
