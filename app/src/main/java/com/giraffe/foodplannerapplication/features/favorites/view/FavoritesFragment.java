package com.giraffe.foodplannerapplication.features.favorites.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.favorites.presenter.FavoritesPresenter;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class FavoritesFragment extends Fragment implements FavoritesView, FavoritesAdapter.OnFavClick {
    private static final String TAG = "FavoritesFragment";

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<Meal> favourites;

    private FavoritesPresenter presenter;
    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    private ItemTouchHelper itemTouchHelper;

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favourites = new ArrayList<>();
        adapter = new FavoritesAdapter(favourites,this);
        presenter = new FavoritesPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
        presenter.getFavorites();

        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT /*| ItemTouchHelper.DOWN | ItemTouchHelper.UP*/) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //Toast.makeText(getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Toast.makeText(getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                position = viewHolder.getAdapterPosition();
                presenter.deleteFavorite(adapter.getFavorites().get(position));
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_favorites);
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onGetFavorites(Observable<List<Meal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> adapter.setList(meals), throwable -> Log.i(TAG, throwable.getMessage()));
    }

    @Override
    public void onDeleteFavorites(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            favourites.remove(position);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(Meal meal) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToDetailsFragment action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(meal);
        Navigation.findNavController(requireView()).navigate(action);
    }
}