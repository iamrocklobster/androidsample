package com.migueloliveira.androidsample.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.interfaces.OnCharacterInteractionListener;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.network.CharacterIntentService;
import com.migueloliveira.androidsample.network.MarvelAPI;
import com.migueloliveira.androidsample.network.ServiceGenerator;
import com.migueloliveira.androidsample.utils.Constants;
import com.migueloliveira.androidsample.view.CharacterDetailsActivity;
import com.migueloliveira.androidsample.view.adapters.CharacterRecyclerViewAdapter;
import com.migueloliveira.androidsample.view.custom.CharacterBottomsheet;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCharacterFragmentInteractionListener}
 * interface.
 */
public class CharacterFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ImageView mNoConnectionIv;
    private ProgressBar mProgressBar;

    private CharacterFragmentGetCharactersBroadcastReceiver mReceiver;

    private OnCharacterFragmentInteractionListener mListener;
    private static final MarvelAPI mMarvelAPI = ServiceGenerator.createService(MarvelAPI.class);

    public CharacterFragment() {
        mReceiver = new CharacterFragmentGetCharactersBroadcastReceiver();
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

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBar.setIndeterminate(true);

        mNoConnectionIv = (ImageView) view.findViewById(R.id.noconnection);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_GETCHARACTERS);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, intentFilter);
        CharacterIntentService.startAction(getActivity());

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCharacterFragmentInteractionListener) {
            mListener = (OnCharacterFragmentInteractionListener) context;
            mListener.onCharacterFragmentShow();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCharacterFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCharacterFragmentInteractionListener {
        void onCharacterFragmentShow();
    }

    private class CharacterFragmentGetCharactersBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressBar.setVisibility(View.GONE);
            ArrayList<Character> characters = intent.getParcelableArrayListExtra(Constants.BROADCAST_GETCHARACTERS_EXTRA_LIST);
            if (characters == null) {
                mNoConnectionIv.setVisibility(View.VISIBLE);
            } else {
                if (characters.size() == 0) {
                    //TODO show emptyLayout;
                } else {
                    CharacterRecyclerViewAdapter adapter = new CharacterRecyclerViewAdapter(characters, new OnCharacterInteractionListener() {
                        @Override
                        public void onClick(Character character) {
                            Intent i = new Intent(getActivity(), CharacterDetailsActivity.class);
                            i.putExtra(Constants.INTENT_CHARACTER, character);
                            startActivity(i);
                        }

                        @Override
                        public void onLongPress(Character character) {
                            CharacterBottomsheet characterBottomsheet = CharacterBottomsheet.newInstance(character.getId());
                            characterBottomsheet.show(getActivity().getSupportFragmentManager(), characterBottomsheet.getTag());
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                }
            }
        }
    }
}
