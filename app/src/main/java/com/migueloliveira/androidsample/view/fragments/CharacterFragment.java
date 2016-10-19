package com.migueloliveira.androidsample.view.fragments;

import android.content.Context;
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
import com.migueloliveira.androidsample.interfaces.OnCharacterInteractionListener;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.network.MarvelAPI;
import com.migueloliveira.androidsample.network.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCharacterFragmentInteractionListener}
 * interface.
 */
public class CharacterFragment extends Fragment {

    private static  final String KEY_LAYOUT_MANAGER = "layoutManager";
    private OnCharacterFragmentInteractionListener mListener;
    private static final MarvelAPI mMarvelAPI = ServiceGenerator.createService(MarvelAPI.class);

    public CharacterFragment() {
    }

    public static CharacterFragment newInstance() {
        return new CharacterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final ArrayList<Character> characterArrayList = new ArrayList<>();

        Call<JsonObject> getCharacters = mMarvelAPI.getCharacters(100,0);
        getCharacters.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject data = response.body().getAsJsonObject("data");
                    JsonArray results = data.getAsJsonArray("results");
                    for (int i = 0; i < results.size(); i++) {
                        JsonObject character = results.get(i).getAsJsonObject();
                        Integer cId = character.get("id").getAsInt();
                        String cName = character.get("name").getAsString();
                        String cDescription = character.get("description").getAsString();
                        String cThumbnail = character.get("thumbnail").getAsJsonObject().get("path").getAsString();
                        Character fChar = new Character(cId, cName, cDescription, cThumbnail);
                        characterArrayList.add(fChar);
                    }
                    CharacterRecyclerViewAdapter adapter = new CharacterRecyclerViewAdapter(characterArrayList, new OnCharacterInteractionListener() {
                        @Override
                        public void onClick(Character character) {
                            Log.e("_DEBUG_",character.toString());
                        }
                    },
                    Boolean.FALSE);
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
        if (context instanceof OnCharacterFragmentInteractionListener) {
            mListener = (OnCharacterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCharacterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCharacterFragmentInteractionListener {
        void onCharacterFragmentShow();
    }
}
