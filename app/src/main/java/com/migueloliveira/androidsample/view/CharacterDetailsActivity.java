package com.migueloliveira.androidsample.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.migueloliveira.androidsample.R;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.utils.Constants;

public class CharacterDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        Character character = getIntent().getParcelableExtra(Constants.INTENT_CHARACTER);
        Log.e("_DEBUG_",character.toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(character.getName());
        setSupportActionBar(toolbar);
    }
}
