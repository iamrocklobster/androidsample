package com.migueloliveira.androidsample.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.interfaces.OnCharacterInteractionListener;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.models.Serie;
import com.migueloliveira.androidsample.network.MarvelAPI;
import com.migueloliveira.androidsample.network.ServiceGenerator;
import com.migueloliveira.androidsample.view.adapters.CharacterRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by migueloliveira on 21/10/2016.
 */

public class CharacterBottomsheet extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    dismiss();
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
    private static final MarvelAPI mMarvelAPI = ServiceGenerator.createService(MarvelAPI.class);

    private static Integer mCharacterID;

    public static CharacterBottomsheet newInstance(Integer cId) {
        mCharacterID = cId;
        return new CharacterBottomsheet();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottomsheet_character, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setSkipCollapsed(true);
            bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        }

        final ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.progressbar);
        progressBar.setIndeterminate(true);

        final GridView gridView = (GridView) contentView.findViewById(R.id.gridview);

        Call<JsonObject> getSeries = mMarvelAPI.getCharacterSeries(mCharacterID, 20,0);
        getSeries.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ArrayList<Serie> characterArrayList = new ArrayList<>();
                if (response.isSuccessful()) {
                    JsonObject data = response.body().getAsJsonObject("data");
                    JsonArray results = data.getAsJsonArray("results");
                    for (int i = 0; i < results.size(); i++) {
                        JsonObject character = results.get(i).getAsJsonObject();
                        Integer cId = character.get("id").getAsInt();
                        String cName = character.get("title").getAsString();
                        String cThumbnail = character.get("thumbnail").getAsJsonObject().get("path").getAsString();
                        String cExtension = character.get("thumbnail").getAsJsonObject().get("extension").getAsString();
                        Serie fSerie = new Serie(cId, cName, cThumbnail, cExtension);
                        characterArrayList.add(fSerie);
                    }
                    progressBar.setVisibility(View.GONE);
                    gridView.setAdapter(new SeriesAdapter(getContext(), characterArrayList));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private class SeriesAdapter extends ArrayAdapter<Serie> {

        private List<Serie> mValues;
        private Context mContext;

        public SeriesAdapter(Context context, List<Serie> objects) {
            super(context, -1, objects);
            this.mValues = objects;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mValues.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            Picasso.with(mContext).load(mValues.get(position).getThumbnail()).fit().centerCrop().into(imageView);

            return imageView;
        }
    }
}
