package com.giraffe.foodplannerapplication.features.settings.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.settings.presenter.SettingsPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;

public class SettingsFragment extends Fragment implements SettingsView {
    private Button btnLogout;
    private SettingsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingsPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initClicks();
    }

    @Override
    public void initViews(View view) {
        btnLogout = view.findViewById(R.id.btn_logout);

    }

    @Override
    public void initClicks() {
        btnLogout.setOnClickListener(v -> presenter.logout());
    }

    @Override
    public void onLogout(boolean isLoggedOut) {
        if (isLoggedOut) {
            Navigation.findNavController(requireView()).setGraph(R.navigation.auth_graph);
        } else {
            Toast.makeText(getContext(), R.string.an_unknown_error_occurred, Toast.LENGTH_SHORT).show();
        }
    }
}