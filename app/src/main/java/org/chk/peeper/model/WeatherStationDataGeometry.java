package org.chk.peeper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WeatherStationDataGeometry {

  List<Double> coordinates;
  String type;

}
