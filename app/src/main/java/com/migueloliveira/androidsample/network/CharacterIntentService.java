package com.migueloliveira.androidsample.network;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.migueloliveira.androidsample.models.Character;
import com.migueloliveira.androidsample.utils.Constants;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class CharacterIntentService extends IntentService {

    private static final MarvelAPI mMarvelAPI = ServiceGenerator.createService(MarvelAPI.class);

    public CharacterIntentService() {
        super("CharacterIntentService");
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, CharacterIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<Character> characterArrayList = new ArrayList<>();
            Call<JsonObject> getCharacters = mMarvelAPI.getCharacters(100,0);
            try {
                Response<JsonObject> response = getCharacters.execute();
                if (response.isSuccessful()) {
                    JsonObject data = response.body().getAsJsonObject("data");
                    JsonArray results = data.getAsJsonArray("results");
                    for (int i = 0; i < results.size(); i++) {
                        JsonObject character = results.get(i).getAsJsonObject();
                        Integer cId = character.get("id").getAsInt();
                        String cName = character.get("name").getAsString();
                        String cDescription = character.get("description").getAsString();
                        String cThumbnail = character.get("thumbnail").getAsJsonObject().get("path").getAsString();
                        String cExtension = character.get("thumbnail").getAsJsonObject().get("extension").getAsString();
                        Character fChar = new Character(cId, cName, cDescription, cThumbnail, cExtension);
                        characterArrayList.add(fChar);
                    }
                }
                Intent localIntent = new Intent(Constants.BROADCAST_GETCHARACTERS);
                localIntent.putParcelableArrayListExtra(Constants.BROADCAST_GETCHARACTERS_EXTRA_LIST, characterArrayList);
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            } catch (IOException e) {
                e.printStackTrace();
                if (e instanceof UnknownHostException) {
                    Intent localIntent = new Intent(Constants.BROADCAST_GETCHARACTERS);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                }
            }
        }
    }
}
