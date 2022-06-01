package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.HouseDealInfoDTO;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/map")
@CrossOrigin("*")
@Api("HouseMap Controller V1")
public class HouseMapController {

	@Autowired
	private HouseMapService houseMapService;

	@ApiOperation(value = "지도 영역의 남서쪽, 북동쪽 위경도로 지도 안의 아파트 리스트를 반환한다.", response = String.class)
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> retrieveApt(@RequestParam("lat1") String lat1, 
			@RequestParam("lat2") String lat2, 
			@RequestParam("lng1") String lng1, @RequestParam("lng2") String lng2, 
			@RequestParam("min") String min, @RequestParam("max") String max) throws Exception {
		System.out.println(lat1 + " " + lat2  + " " + lng1 + " " + lng2 +" " + min +" " + max );
		return new ResponseEntity<List<HouseInfoDto>>(houseMapService.getHouseDistance(lat1, lat2,lng1, lng2, Integer.parseInt(min), Integer.parseInt(max)), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트코드에 해당하는 아파트의 모든 거래를 반환한다.", response = String.class)
	@GetMapping("/aptdeal")
	public ResponseEntity<List<HouseDealInfoDTO>> retrieveApt(@RequestParam("aptCode") String aptCode) throws Exception {
		System.out.println(aptCode);
		return new ResponseEntity<List<HouseDealInfoDTO>>(houseMapService.getHouseDeal(aptCode), HttpStatus.OK);
	}
	
}
