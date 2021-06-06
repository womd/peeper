package org.chk.peeper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherStationData {

  String id;
  String type;

  private WeatherStationDataGeometry geometry;
  private WeatherStationDataProperties properties;

}
