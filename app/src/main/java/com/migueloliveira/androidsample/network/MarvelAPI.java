package com.migueloliveira.androidsample.network;

import com.google.gson.JsonObject;
import com.migueloliveira.androidsample.utils.Constants;

import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by migueloliveira on 8/10/16.
 */
public interface MarvelAPI {
    @GET("/v1/public/characters")
    Call<JsonObject> getCharacters(
            @Query(Constants.API_LIMIT) Integer limit,
            @Query(Constants.API_OFFSET) Integer offset);

    @GET("/v1/public/characters/{characterId}")
    Call<JsonObject> getCharacterByID(
            @Path(Constants.API_CHARACTERID) Integer characterID
    );

    @GET("/v1/public/characters/{characterId}/series")
    Call<JsonObject> getCharacterSeries(
            @Path(Constants.API_CHARACTERID) Integer characterID,
            @Query(Constants.API_LIMIT) Integer limit,
            @Query(Constants.API_OFFSET) Integer offset);

    @GET("/v1/public/comics")
    Call<JsonObject> getComics(
            @Query(Constants.API_LIMIT) Integer limit,
            @Query(Constants.API_OFFSET) Integer offset);

    @GET("/v1/public/comics/{comicId}")
    Call<JsonObject> getComicByID(
            @Path(Constants.API_COMICID) Integer comicID
    );

    @GET("/v1/public/creators")
    Call<JsonObject> getCreators(
            @Query(Constants.API_LIMIT) Integer limit,
            @Query(Constants.API_OFFSET) Integer offset);

    @GET("/v1/public/creators/{creatorId}")
    Call<JsonObject> getCreatorByID(
            @Path(Constants.API_CREATORID) Integer creatorID
    );

    @GET("/v1/public/events")
    Call<JsonObject> getEvents(
            @Query(Constants.API_LIMIT) Integer limit,
            @Query(Constants.API_OFFSET) Integer offset);

    @GET("/v1/public/events/{eventId}")
    Call<JsonObject> getEventByID(
            @Path(Constants.API_EVENTID) Integer eventID
    );
}
