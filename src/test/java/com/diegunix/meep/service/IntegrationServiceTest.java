package com.diegunix.meep.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;

import com.diegunix.meep.bean.VehicleBean;
import com.diegunix.meep.service.impl.IntegrationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegrationServiceTest {

  IntegrationService service;

  private MeepService meepService;
  private ApplicationEventPublisher applicationEventPublisher;

  @Before
  public void prepareEnvironment() {
    meepService = mock(MeepService.class);
    applicationEventPublisher = mock(ApplicationEventPublisher.class);

    service = new IntegrationServiceImpl(meepService, applicationEventPublisher);
  }

  @Test
  public void testPoolingMeep() {
    List<VehicleBean> founds = new ArrayList<>();
    founds.add(VehicleBean.builder().id("xxx").build());
    when(meepService.getResourcesMeep()).thenReturn(founds);
    service.setData(founds);
    service.poolingMeep();
    Mockito.verify(meepService, Mockito.times(1)).getResourcesMeep();
  }
  
  @Test
  public void testPoolingMeepNew() {
    List<VehicleBean> founds = new ArrayList<>();
    founds.add(VehicleBean.builder().id("xxx").build());
    List<VehicleBean> data = new ArrayList<>();
    data.add(VehicleBean.builder().id("xxx").build());
    data.add(VehicleBean.builder().id("xxz").build());
    when(meepService.getResourcesMeep()).thenReturn(founds);
    service.setData(data);
    service.poolingMeep();
    Mockito.verify(meepService, Mockito.times(1)).getResourcesMeep();
  }
  
  @Test
  public void testPoolingMeepOld() {
    List<VehicleBean> founds = new ArrayList<>();
    founds.add(VehicleBean.builder().id("xxx").build());
    founds.add(VehicleBean.builder().id("xxz").build());
    List<VehicleBean> data = new ArrayList<>();
    data.add(VehicleBean.builder().id("xxz").build());
    when(meepService.getResourcesMeep()).thenReturn(founds);
    service.setData(founds);
    service.poolingMeep();
    Mockito.verify(meepService, Mockito.times(1)).getResourcesMeep();
  }

}
