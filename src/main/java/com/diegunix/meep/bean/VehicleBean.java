package com.diegunix.meep.bean;

import java.util.List;

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
public class VehicleBean {

  private String id;
  private String name;
  private double cordX;
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
