package com.giraffe.foodplannerapplication.features.onboard.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.giraffe.foodplannerapplication.R;

import java.util.ArrayList;
import java.util.Objects;

public class OnBoardPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<OnBoardFragment.OnBoardData> list;
    LayoutInflater inflater;

    OnBoardClickListener onBoardClickListener;

    OnBoardPagerAdapter(Context context, ArrayList<OnBoardFragment.OnBoardData> list, OnBoardClickListener onBoardClickListener) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onBoardClickListener = onBoardClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        OnBoardFragment.OnBoardData data = list.get(position);
        View itemView = inflater.inflate(R.layout.on_board_item, container, false);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) itemView.findViewById(R.id.lottie_view);
        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        TextView tvSub = (TextView) itemView.findViewById(R.id.tv_sub);
        TextView tvSkip = (TextView) itemView.findViewById(R.id.tv_skip);
        TextView tvSwipe = (TextView) itemView.findViewById(R.id.tv_swipe);
        Button btnLogin = (Button) itemView.findViewById(R.id.btn_login);
        tvSkip.setOnClickListener(view -> onBoardClickListener.onClick(view));
        if (position == list.size() - 1) {
            tvSwipe.setText(R.string.as_a_guest);
            tvSwipe.setTextColor(ContextCompat.getColor(context, R.color.orange));
            tvSwipe.setOnClickListener(view -> onBoardClickListener.onClick(view));
            btnLogin.setVisibility(View.VISIBLE);
            btnLogin.setOnClickListener(view -> onBoardClickListener.onClick(view));
        } else {
            tvSwipe.setText(R.string.swipe);
            tvSwipe.setTextColor(ContextCompat.getColor(context, R.color.orange));
            tvSwipe.setOnClickListener(view -> {
            });
            btnLogin.setVisibility(View.INVISIBLE);
        }
        lottieAnimationView.setAnimation(data.getLottieRes());
        tvTitle.setText(data.getTitle());
        tvSub.setText(data.getSub());
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }


}
