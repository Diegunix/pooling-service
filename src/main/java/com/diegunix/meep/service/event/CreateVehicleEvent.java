package com.diegunix.meep.service.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.diegunix.meep.bean.VehicleBean;

public class CreateVehicleEvent extends ApplicationEvent {

  private static final long serialVersionUID = 1192620080773646286L;

  private String message;

  public CreateVehicleEvent(List<VehicleBean> source) {
    super(source);
  }

  public String getMessage() {
    return message;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<VehicleBean> getSource() {
    return (List<VehicleBean>) super.getSource();
  }
}