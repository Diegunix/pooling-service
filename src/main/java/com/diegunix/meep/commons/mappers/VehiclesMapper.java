package com.diegunix.meep.commons.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import com.diegunix.meep.bean.VehicleBean;
import com.diegunix.meep.service.dto.MeepServiceVehicleDto;

@Mapper
public interface VehiclesMapper {

  VehicleBean map(MeepServiceVehicleDto bean);

  @IterableMapping(qualifiedByName = { "defaultMapper" })
  List<VehicleBean> map(List<MeepServiceVehicleDto> content);

}