package com.diegunix.meep.service;

import java.util.List;

import com.diegunix.meep.bean.VehicleBean;

public interface IntegrationService {

  void poolingMeep();

  public void setData(List<VehicleBean> data);

  public List<VehicleBean> getData();

}
