package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HouseDealInfoDTO;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<HouseInfoDto> getHouseDistance(String lat1, String lat2, String lng1, String lng2, int min, int max) throws SQLException {
		return houseMapMapper.getAllHouse(lat1, lat2, lng1, lng2, min, max);
	}
	
	@Override
	public List<HouseDealInfoDTO> getHouseDeal(String aptCode) throws SQLException {
		return houseMapMapper.getHouseDeal(aptCode);
	}
}
