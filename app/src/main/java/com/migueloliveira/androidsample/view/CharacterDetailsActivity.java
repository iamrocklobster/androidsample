package com.migueloliveira.androidsample.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CharacterDetailsActivity extends AppCompatActivity {

    private ImageView mThumbnail;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextInputLayout mTextInputDescription;
    private TextView mTextViewModified;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        Character character = getIntent().getParcelableExtra(Constants.INTENT_CHARACTER);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(character.getName());
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mThumbnail = (ImageView) findViewById(R.id.thumbnail);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mTextInputDescription = (TextInputLayout) findViewById(R.id.textinput_description);
        mTextViewModified = (TextView) findViewById(R.id.modified);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_48dp));
                mTextInputDescription.getEditText().setTextIsSelectable(true);
                mTextInputDescription.getEditText().setFocusable(true);
                mTextInputDescription.getEditText().setClickable(true);
                mTextInputDescription.setHintEnabled(true);
                mTextInputDescription.setHint("Description");
            }
        });

        mTextInputDescription.getEditText().setTextIsSelectable(false);
        mTextInputDescription.getEditText().setFocusable(false);
        mTextInputDescription.getEditText().setClickable(false);
        mTextInputDescription.getEditText().setText(character.getDescription());

        mTextViewModified.setText(character.getModified());

        Picasso.with(this).load(character.getBackground()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mThumbnail.setImageBitmap(bitmap);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatchMut = palette.getLightMutedSwatch();
                        if (swatchMut != null) {
                            mFab.setBackgroundTintList(ColorStateList.valueOf(swatchMut.getRgb()));
                            //mCollapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(swatchMut.getTitleTextColor()));
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}
