package com.giraffe.foodplannerapplication.features.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.plan.presenter.PlanPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Pair;

public class PlanFragment extends Fragment implements PlanView {
    private final static String TAG = "PlanFragment";

    private PlanPresenter presenter;


    private TextView tvLogin;
    private Button btnLogin;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private PlanPagerAdapter adapter;

    private ArrayList<Pair<String, Integer>> days;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlanPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
        days = new ArrayList<>();
        days.add(new Pair<>("Saturday", Constants.DAYS.SATURDAY));
        days.add(new Pair<>("Sunday", Constants.DAYS.SUNDAY));
        days.add(new Pair<>("Monday", Constants.DAYS.MONDAY));
        days.add(new Pair<>("Tuesday", Constants.DAYS.TUESDAY));
        days.add(new Pair<>("Wednesday", Constants.DAYS.WEDNESDAY));
        days.add(new Pair<>("Thursday", Constants.DAYS.THURSDAY));
        days.add(new Pair<>("Friday", Constants.DAYS.FRIDAY));
        adapter = new PlanPagerAdapter(getChildFragmentManager(), getLifecycle(), days);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvLogin = view.findViewById(R.id.tv_login);
        btnLogin = view.findViewById(R.id.btn_login);
        tabLayout = view.findViewById(R.id.tl_days);
        viewPager = view.findViewById(R.id.vp_days);
        btnLogin.setOnClickListener(v -> Navigation.findNavController(v).setGraph(R.navigation.auth_graph));
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(days.get(position).getFirst())
        ).attach();
        presenter.isLoggedIn();

    }

    @Override
    public void onGetLoggedInFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(isLoggedIn -> {
                    if (isLoggedIn) {
                        tvLogin.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.INVISIBLE);
                        tabLayout.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.VISIBLE);
                    } else {
                        tvLogin.setVisibility(View.VISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                        tabLayout.setVisibility(View.INVISIBLE);
                        viewPager.setVisibility(View.INVISIBLE);
                    }
                }, throwable -> {
                    tvLogin.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.INVISIBLE);
                    Log.i(TAG, throwable.getMessage());
                });
    }
}