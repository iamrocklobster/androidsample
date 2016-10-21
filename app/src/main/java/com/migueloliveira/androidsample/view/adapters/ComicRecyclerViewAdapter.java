package com.migueloliveira.androidsample.view.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.interfaces.OnCharacterInteractionListener;
import com.migueloliveira.androidsample.interfaces.OnComicInteractionListener;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.models.Comic;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by migueloliveira on 20/10/2016.
 */

public class ComicRecyclerViewAdapter extends RecyclerView.Adapter<ComicRecyclerViewAdapter.ViewHolder> {

    private final List<Comic> mValues;
    private final OnComicInteractionListener mListener;
    private static final Transformation circleTransformation = new RoundedTransformationBuilder().borderColor(Color.BLACK)
            .borderWidth(2f).cornerRadius(72f).build();

    public ComicRecyclerViewAdapter(List<Comic> items, OnComicInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_comic_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mComic = mValues.get(position);

        holder.mName.setText(mValues.get(position).getName());
        if (holder.mDescription != null) {
            holder.mDescription.setText(mValues.get(position).getDescription());
        }

        BlurTransformation blurTransformation = new BlurTransformation(holder.mBackground.getContext());
        Picasso.with(holder.mBackground.getContext()).load(holder.mComic.getBackground()).transform(blurTransformation).fit().centerCrop().into(holder.mBackground);
        Picasso.with(holder.mThumbnail.getContext()).load(holder.mComic.getThumbnail()).transform(circleTransformation).into(holder.mThumbnail);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClick(holder.mComic);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onLongPress(holder.mComic);
                }
                return true;
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
        public final ImageView mBackground;
        public final TextView mName;
        public final TextView mDescription;
        public Comic mComic;

        public ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.comic_view);
            mThumbnail = (ImageView) view.findViewById(R.id.comic_icon);
            mBackground = (ImageView) view.findViewById(R.id.comic_background);
            mName = (TextView) view.findViewById(R.id.comic_name);
            mDescription = (TextView) view.findViewById(R.id.comic_details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
