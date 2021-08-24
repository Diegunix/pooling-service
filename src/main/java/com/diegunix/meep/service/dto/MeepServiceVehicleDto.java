package com.diegunix.meep.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeepServiceVehicleDto {

  private String id;
  private String name;
  @JsonProperty("x")
  private double cordX;
  @JsonProperty("y")
  private double cordY;
  private String licensePlate;
  private Integer range;
  private Integer batteryLevel;
  private Integer helmets;
  private String model;
  private String resourceImageId;
  private List<String> resourcesImagesUrls;
  private Boolean realTimeData;
  private Integer companyZoneId;
  private String resourceType;
}
