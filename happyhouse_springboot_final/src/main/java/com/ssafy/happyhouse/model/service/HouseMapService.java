package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.HouseDealInfoDTO;
import com.ssafy.happyhouse.model.HouseInfoDto;

public interface HouseMapService {
	
	List<HouseInfoDto> getHouseDistance(String lat1, String lng1, String lat2, String lng2, int min, int max) throws SQLException; 
	List<HouseDealInfoDTO> getHouseDeal(String aptCode) throws SQLException;
}
