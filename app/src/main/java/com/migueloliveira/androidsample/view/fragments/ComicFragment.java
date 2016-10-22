package com.migueloliveira.androidsample.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.interfaces.OnComicInteractionListener;
import com.migueloliveira.androidsample.models.Comic;
import com.migueloliveira.androidsample.network.MarvelAPI;
import com.migueloliveira.androidsample.network.ServiceGenerator;
import com.migueloliveira.androidsample.view.adapters.ComicRecyclerViewAdapter;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicFragment extends Fragment {
    private OnComicFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private ImageView mNoConnectionIv;
    private ProgressBar mProgressBar;

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

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBar.setIndeterminate(true);

        mNoConnectionIv = (ImageView) view.findViewById(R.id.noconnection);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        new AsyncTask<Void,Void,List<Comic>>() {
            final MarvelAPI mMarvelAPI = ServiceGenerator.createService(MarvelAPI.class);

            @Override
            protected List<Comic> doInBackground(Void... params) {
                ArrayList<Comic> comicArrayList = new ArrayList<>();
                Call<JsonObject> getComics = mMarvelAPI.getComics(100,0);
                try {
                    Response<JsonObject> response = getComics.execute();
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
                        return comicArrayList;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (e instanceof UnknownHostException) {
                        return null;
                    }
                }

                return comicArrayList;
            }

            @Override
            protected void onPostExecute(List<Comic> comics) {
                mProgressBar.setVisibility(View.GONE);
                if (comics == null) {
                    mNoConnectionIv.setVisibility(View.VISIBLE);
                } else {
                    if (comics.size() == 0) {
                        //TODO show emptyLayout;
                    } else {
                        ComicRecyclerViewAdapter adapter = new ComicRecyclerViewAdapter(comics, new OnComicInteractionListener() {
                            @Override
                            public void onClick(Comic comic) {
                                Log.e("_DEBUG_",comic.toString());
                            }

                            @Override
                            public void onLongPress(Comic mComic) {

                            }
                        });
                        mRecyclerView.setAdapter(adapter);
                    }
                }

            }
        }.execute();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComicFragmentInteractionListener) {
            mListener = (OnComicFragmentInteractionListener) context;
            mListener.onComicFragmentShow();
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
