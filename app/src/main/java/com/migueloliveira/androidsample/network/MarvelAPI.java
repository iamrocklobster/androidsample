package com.migueloliveira.androidsample.network;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by migueloliveira on 8/10/16.
 */
public interface MarvelAPI {
    @GET("/v1/public/characters")
    Call<JsonObject> getCharacters(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("name") String name,
            @Query("modifiedSince") Date modifiedSince,
            @Query("comics") String comics,
            @Query("series") String series,
            @Query("events") String events,
            @Query("orderBy") String orderBy);
}
