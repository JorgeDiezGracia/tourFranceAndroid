package com.svalero.tourfrance.api;

import com.svalero.tourfrance.model.Cyclist;
import com.svalero.tourfrance.model.Team;
import com.svalero.tourfrance.model.Stage;
import com.svalero.tourfrance.model.Climb;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TourFranceService {

    // --- CYCLISTS ---
    @GET("cyclists")
    Call<List<Cyclist>> getAllCyclists();

    @GET("cyclists")
    Call<List<Cyclist>> getCyclistsByName(@Query("name") String name);

    @GET("cyclists/{id}")
    Call<Cyclist> getCyclistById(@Path("id") long id);

    @POST("cyclists")
    Call<Cyclist> addCyclist(@Body Cyclist cyclist);

    @PUT("cyclists/{id}")
    Call<Cyclist> updateCyclist(@Path("id") long id, @Body Cyclist cyclist);

    @DELETE("cyclists/{id}")
    Call<Void> deleteCyclist(@Path("id") long id);

    // --- TEAMS ---
    @GET("teams")
    Call<List<Team>> getAllTeams();

    @GET("teams")
    Call<List<Team>> getTeamsByName(@Query("name") String name);

    @GET("teams/{id}")
    Call<Team> getTeamById(@Path("id") long id);

    @POST("teams")
    Call<Team> addTeam(@Body Team team);

    @PUT("teams/{id}")
    Call<Team> updateTeam(@Path("id") long id, @Body Team team);

    @DELETE("teams/{id}")
    Call<Void> deleteTeam(@Path("id") long id);

    // --- STAGES ---
    @GET("stages")
    Call<List<Stage>> getAllStages();

    @GET("stages")
    Call<List<Stage>> getStagesByType(@Query("type") String type);

    @GET("stages/{id}")
    Call<Stage> getStageById(@Path("id") long id);

    @POST("stages")
    Call<Stage> addStage(@Body Stage stage);

    @PUT("stages/{id}")
    Call<Stage> updateStage(@Path("id") long id, @Body Stage stage);

    @DELETE("stages/{id}")
    Call<Void> deleteStage(@Path("id") long id);

    // --- CLIMBS ---
    @GET("climbs")
    Call<List<Climb>> getAllClimbs();

    @GET("climbs")
    Call<List<Climb>> getClimbsByCategory(@Query("category") String category);

    @GET("climbs/{id}")
    Call<Climb> getClimbById(@Path("id") long id);

    @POST("climbs")
    Call<Climb> addClimb(@Body Climb climb);

    @PUT("climbs/{id}")
    Call<Climb> updateClimb(@Path("id") long id, @Body Climb climb);

    @DELETE("climbs/{id}")
    Call<Void> deleteClimb(@Path("id") long id);
}
