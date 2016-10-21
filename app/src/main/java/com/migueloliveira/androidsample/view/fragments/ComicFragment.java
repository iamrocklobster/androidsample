package com.migueloliveira.androidsample.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.interfaces.OnComicInteractionListener;
import com.migueloliveira.androidsample.models.Comic;
import com.migueloliveira.androidsample.network.MarvelAPI;
import com.migueloliveira.androidsample.network.ServiceGenerator;
import com.migueloliveira.androidsample.view.adapters.ComicRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicFragment extends Fragment {
    private static  final String KEY_LAYOUT_MANAGER = "layoutManager";
    private OnComicFragmentInteractionListener mListener;
    private static final MarvelAPI mMarvelAPI = ServiceGenerator.createService(MarvelAPI.class);

    public ComicFragment() {
    }

    public static ComicFragment newInstance() {
        return new ComicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);

        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        Call<JsonObject> getComics = mMarvelAPI.getComics(100,0);
        getComics.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ArrayList<Comic> comicArrayList = new ArrayList<>();
                if (response.isSuccessful()) {
                    JsonObject data = response.body().getAsJsonObject("data");
                    JsonArray results = data.getAsJsonArray("results");
                    for (int i = 0; i < results.size(); i++) {
                        JsonObject comic = results.get(i).getAsJsonObject();
                        Integer cId = comic.get("id").getAsInt();
                        String cName = comic.get("title").getAsString();
                        String cDescription = comic.get("variantDescription").getAsString();
                        String cThumbnail = comic.get("thumbnail").getAsJsonObject().get("path").getAsString();
                        String cExtension = comic.get("thumbnail").getAsJsonObject().get("extension").getAsString();
                        Comic fComic = new Comic(cId, cName, cDescription, cThumbnail, cExtension);
                        comicArrayList.add(fComic);
                    }
                    ComicRecyclerViewAdapter adapter = new ComicRecyclerViewAdapter(comicArrayList, new OnComicInteractionListener() {
                        @Override
                        public void onClick(Comic comic) {
                            Log.e("_DEBUG_",comic.toString());
                        }

                        @Override
                        public void onLongPress(Comic mComic) {

                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComicFragmentInteractionListener) {
            mListener = (OnComicFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnComicFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnComicFragmentInteractionListener {
        void onComicFragmentShow();
    }
}
