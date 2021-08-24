package com.diegunix.meep.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.diegunix.meep.bean.VehicleBean;
import com.diegunix.meep.service.IntegrationService;
import com.diegunix.meep.service.MeepService;
import com.diegunix.meep.service.event.CreateVehicleEvent;
import com.diegunix.meep.service.event.DeleteVehicleEvent;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntegrationServiceImpl implements IntegrationService {

  private List<VehicleBean> data = new ArrayList<>();

  private MeepService meepService;
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public IntegrationServiceImpl(MeepService meepService, ApplicationEventPublisher applicationEventPublisher) {
    this.meepService = meepService;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void poolingMeep() {
    List<VehicleBean> newData = meepService.getResourcesMeep();
    if (CollectionUtils.isNotEmpty(getData())) {
      searchNew(newData);
      searchDelete(newData);
    }
    setData(newData);
  }

  private void searchDelete(List<VehicleBean> newData) {
    List<VehicleBean> result = new ArrayList<>();
    result.addAll(getData());
    for (VehicleBean v : newData) {
      result.removeIf(d -> d.getId().equals(v.getId()));
    }
    if (CollectionUtils.isNotEmpty(result)) {
      DeleteVehicleEvent eventSlave = new DeleteVehicleEvent(result);
      applicationEventPublisher.publishEvent(eventSlave);
    }
  }

  private void searchNew(List<VehicleBean> newData) {
    List<VehicleBean> result = new ArrayList<>();
    result.addAll(newData);
    for (VehicleBean v : getData()) {
      result.removeIf(d -> d.getId().equals(v.getId()));
    }
    if (CollectionUtils.isNotEmpty(result)) {
      CreateVehicleEvent eventSlave = new CreateVehicleEvent(result);
      applicationEventPublisher.publishEvent(eventSlave);
    }
  }

  @Override
  public List<VehicleBean> getData() {
    return data;
  }

  @Override
  public void setData(List<VehicleBean> data) {
    this.data = data;
  }

  @Async
  @EventListener
  public void sendNotificationMailDeleteEmployee(DeleteVehicleEvent event) {
    try {
      List<VehicleBean> list = event.getSource();
      log.info("Event delete vehicle total num: " + list.size());
      log.info("Event delete vehicle: " + new Gson().toJson(list));
    } catch (Exception e) {
      log.error("Error event delete vehicle: ", e);
    }
  }

  @Async
  @EventListener
  public void sendNotificationMailDeleteEmployee(CreateVehicleEvent event) {
    try {
      List<VehicleBean> list = event.getSource();
      log.info("Event create vehicle total num: " + list.size());
      log.info("Event create vehicle: " + new Gson().toJson(list));
    } catch (Exception e) {
      log.error("Error event create vehicle: ", e);
    }
  }

}
