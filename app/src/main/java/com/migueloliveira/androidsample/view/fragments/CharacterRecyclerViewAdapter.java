package com.migueloliveira.androidsample.view.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.interfaces.OnCharacterInteractionListener;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.view.fragments.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnCharacterInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CharacterRecyclerViewAdapter extends RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder> {

    private final List<Character> mValues;
    private final OnCharacterInteractionListener mListener;

    public CharacterRecyclerViewAdapter(List<Character> items, OnCharacterInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_character_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCharacter = mValues.get(position);

        holder.mName.setText(mValues.get(position).getName());
        holder.mDescription.setText(mValues.get(position).getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClick(holder.mCharacter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mThumbnail;
        public final TextView mName;
        public final TextView mDescription;
        public Character mCharacter;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mThumbnail = (ImageView) view.findViewById(R.id.character_icon);
            mName = (TextView) view.findViewById(R.id.character_name);
            mDescription = (TextView) view.findViewById(R.id.character_details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
