package org.chk.peeper.rest;

import org.chk.peeper.model.WeatherStationDataList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WhetherStationApiService {

    @GET("produkte/ogd.geojson")

    Call<WeatherStationDataList> getData();
}
