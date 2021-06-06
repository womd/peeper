package org.chk.peeper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
public class WeatherStationDataProperties {

  //  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  // @JsonSerialize(using = LocalDateTimeSerializer.class)
  // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    String date;

    @JsonProperty("LT")
    Double lt;
    @JsonProperty("LT_MAX")
    Double ltMax;
    @JsonProperty("LT_MIN")
    Double ltMin;
    @JsonProperty("LWD-Region")
    String region;
    String name;
    String operator;
    String plot;
    @JsonProperty("RH")
    Double rh;
    @JsonProperty("TD")
    Double td;
    @JsonProperty("WG")
    Double wg;
    @JsonProperty("WG_BOE")
    Double wgBoe;
    @JsonProperty("WR")
    Double wr;

    @JsonProperty("HS")
    Double hs;

    @JsonProperty("HSD24")
    Double hsd24;

    @JsonProperty("HSD48")
    Double hsd48;

    @JsonProperty("HSD72")
    Double hsd72;

    @JsonProperty("GS_O")
    Double gsO;

    @JsonProperty("OFT")
    Double oft;

    @JsonProperty("GS_U")
    Double gsu;

    @JsonProperty("LD")
    Double ld;
}
