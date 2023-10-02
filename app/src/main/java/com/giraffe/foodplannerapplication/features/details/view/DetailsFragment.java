package com.giraffe.foodplannerapplication.features.details.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.details.presenter.DetailsPresenter;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsFragment extends Fragment implements DetailsView {
    public final static String TAG = "DetailsFragment";
    private DetailsPresenter presenter;

    private ImageView ivMeal;
    private TextView tvCategory, tvCountry, tvSteps;
    //private WebView wvInstructions;
    YouTubePlayerView youTubePlayerView;
    private RecyclerView rvIngredients;

    private IngredientsAdapter adapter;
    private List<String> ingredients;

    private DetailsFragmentArgs args;

    private Meal meal;

    int counter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new DetailsPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(context)
        ));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = DetailsFragmentArgs.fromBundle(getArguments());
        meal = args.getMeal();
        ingredients = getIngredients(meal);
        adapter = new IngredientsAdapter(ingredients);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflateViews(view);
        initClicks();
        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(ivMeal);
        tvCategory.setText(meal.getStrCategory());
        tvCountry.setText(meal.getStrArea());
        /*String[] steps = meal.getStrInstructions().split("\r");
        counter = 0;
        StringBuilder strBuilder = new StringBuilder();
        Arrays.stream(steps).forEach(s -> {
            strBuilder.append(String.valueOf(++counter).concat("- " + s) + "\n");
        });*/
        tvSteps.setText(meal.getStrInstructions());


        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getVideoId(meal.getStrYoutube());
                youTubePlayer.loadVideo(videoId, 0f);
            }
        });


        /*String dataUrl = "<html>" +
                "<body>" +
                "<h2>Video From YouTube</h2>" +
                "<br>" +
                "<iframe width=\"560\" height=\"315\" src=\"" + meal.getStrYoutube() + "\" frameborder=\"0\" allowfullscreen/>" +
                "</body>" +
                "</html>";
        WebSettings webSettings = wvInstructions.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvInstructions.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvInstructions.getSettings().setLoadWithOverviewMode(true);
        wvInstructions.getSettings().setUseWideViewPort(true);*/

        //wvInstructions.setWebViewClient(new WebViewClient());
        //wvInstructions.setWebChromeClient(new WebChromeClient());
        //wvInstructions.loadUrl(meal.getStrYoutube());

        //WebSettings settings = wvInstructions.getSettings();
        //settings.setJavaScriptEnabled(true);
        //settings.setPluginState(WebSettings.PluginState.ON);




        //wvInstructions.loadData(dataUrl, "text/html", "utf-8");
        rvIngredients.setAdapter(adapter);
    }

    @Override
    public void inflateViews(View view) {
        ivMeal = view.findViewById(R.id.iv_meal);
        tvCategory = view.findViewById(R.id.tv_category);
        tvCountry = view.findViewById(R.id.tv_country);
        tvSteps = view.findViewById(R.id.tv_steps);
        //wvInstructions = view.findViewById(R.id.wv_instructions);
        youTubePlayerView = view.findViewById(R.id.wv_instructions);
        rvIngredients = view.findViewById(R.id.rv_ingredients);
    }

    @Override
    public void initClicks() {

    }

    private ArrayList<String> getIngredients(Meal meal) {
        ArrayList<String> ingredients = new ArrayList<>();
        if (meal.getStrIngredient1() != null && !meal.getStrIngredient1().equals("")) {
            ingredients.add(meal.getStrIngredient1());
        }
        if (meal.getStrIngredient2() != null && !meal.getStrIngredient2().equals("")) {
            ingredients.add(meal.getStrIngredient2());
        }
        if (meal.getStrIngredient3() != null && !meal.getStrIngredient3().equals("")) {
            ingredients.add(meal.getStrIngredient3());
        }
        if (meal.getStrIngredient4() != null && !meal.getStrIngredient4().equals("")) {
            ingredients.add(meal.getStrIngredient4());
        }
        if (meal.getStrIngredient5() != null && !meal.getStrIngredient5().equals("")) {
            ingredients.add(meal.getStrIngredient5());
        }
        if (meal.getStrIngredient6() != null && !meal.getStrIngredient6().equals("")) {
            ingredients.add(meal.getStrIngredient6());
        }
        if (meal.getStrIngredient7() != null && !meal.getStrIngredient7().equals("")) {
            ingredients.add(meal.getStrIngredient7());
        }
        if (meal.getStrIngredient8() != null && !meal.getStrIngredient8().equals("")) {
            ingredients.add(meal.getStrIngredient8());
        }
        if (meal.getStrIngredient9() != null && !meal.getStrIngredient9().equals("")) {
            ingredients.add(meal.getStrIngredient9());
        }
        if (meal.getStrIngredient10() != null && !meal.getStrIngredient10().equals("")) {
            ingredients.add(meal.getStrIngredient10());
        }
        if (meal.getStrIngredient11() != null && !meal.getStrIngredient11().equals("")) {
            ingredients.add(meal.getStrIngredient11());
        }
        if (meal.getStrIngredient12() != null && !meal.getStrIngredient12().equals("")) {
            ingredients.add(meal.getStrIngredient12());
        }
        if (meal.getStrIngredient13() != null && !meal.getStrIngredient13().equals("")) {
            ingredients.add(meal.getStrIngredient13());
        }
        if (meal.getStrIngredient14() != null && !meal.getStrIngredient14().equals("")) {
            ingredients.add(meal.getStrIngredient14());
        }
        if (meal.getStrIngredient15() != null && !meal.getStrIngredient15().equals("")) {
            ingredients.add(meal.getStrIngredient15());
        }
        if (meal.getStrIngredient16() != null && !meal.getStrIngredient16().equals("")) {
            ingredients.add(meal.getStrIngredient16());
        }
        if (meal.getStrIngredient17() != null && !meal.getStrIngredient17().equals("")) {
            ingredients.add(meal.getStrIngredient17());
        }
        if (meal.getStrIngredient18() != null && !meal.getStrIngredient18().equals("")) {
            ingredients.add(meal.getStrIngredient18());
        }
        if (meal.getStrIngredient19() != null && !meal.getStrIngredient19().equals("")) {
            ingredients.add(meal.getStrIngredient19());
        }
        if (meal.getStrIngredient20() != null && !meal.getStrIngredient20().equals("")) {
            ingredients.add(meal.getStrIngredient20());
        }
        for (String s : ingredients) {
            Log.i(TAG,s);
        }
        return ingredients;

    }
    private String getVideoId(String url){
        String[] split = url.split("v=");
        return split[1].trim();
    }
}