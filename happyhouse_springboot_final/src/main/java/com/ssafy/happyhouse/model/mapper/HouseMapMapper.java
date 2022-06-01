package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.HouseDealInfoDTO;
import com.ssafy.happyhouse.model.HouseInfoDto;

@Mapper
public interface HouseMapMapper {

	List<HouseInfoDto> getAllHouse(String lat1, String lat2, String lng1, String lng2, int min, int max) throws SQLException; 
	List<HouseDealInfoDTO> getHouseDeal(String aptCode) throws SQLException;
}
