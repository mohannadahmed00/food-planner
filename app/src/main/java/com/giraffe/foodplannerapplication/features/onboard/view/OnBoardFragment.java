package com.giraffe.foodplannerapplication.features.onboard.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giraffe.foodplannerapplication.R;

import java.util.ArrayList;

public class OnBoardFragment extends Fragment implements OnBoardClickListener {
    ViewPager viewPager;
    OnBoardPagerAdapter adapter;

    ArrayList<OnBoardData> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        list.add(new OnBoardData(R.raw.on_board_1, "Various meals throughout the week", "Users can easily create personalized meal plans by selecting recipes from a vast database."));
        list.add(new OnBoardData(R.raw.on_board_2, "Easy meals to prepare step by step", "The app provides smart grocery lists based on the selected meals, ensuring users have all the necessary ingredients on hand."));
        adapter = new OnBoardPagerAdapter(requireContext(), list, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            Navigation.findNavController(view).setGraph(R.navigation.auth_graph);
        } else {
            Navigation.findNavController(view).setGraph(R.navigation.main_graph);
        }
    }

    static class OnBoardData {
        private final int lottieRes;
        private final String title, sub;

        public OnBoardData(int lottieRes, String title, String sub) {
            this.lottieRes = lottieRes;
            this.title = title;
            this.sub = sub;
        }

        public int getLottieRes() {
            return lottieRes;
        }

        public String getTitle() {
            return title;
        }

        public String getSub() {
            return sub;
        }
    }
}