package com.diegunix.meep.service.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.diegunix.meep.bean.VehicleBean;
import com.diegunix.meep.commons.mappers.VehiclesMapper;
import com.diegunix.meep.service.MeepService;
import com.diegunix.meep.service.dto.MeepServiceVehicleDto;

@Service
public class MeepServiceImpl implements MeepService {

  @Value("${meep.service.api.request.url}")
  private String urlMeepVehicles;

  @Value("${meep.service.api.request.query}")
  private String data;

  private RestTemplate restTemplate;

  private VehiclesMapper vehiclesMapper = Mappers.getMapper(VehiclesMapper.class);

  @Autowired
  public MeepServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<VehicleBean> getResourcesMeep() {
    URI targetUrl = UriComponentsBuilder.fromUriString(urlMeepVehicles).queryParam("query", data).build().encode()
        .toUri();
    MeepServiceVehicleDto[] forNow = restTemplate.getForObject(targetUrl, MeepServiceVehicleDto[].class);
    return vehiclesMapper.map(Arrays.asList(forNow));
  }

}
