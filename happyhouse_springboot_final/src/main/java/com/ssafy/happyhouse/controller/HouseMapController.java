package com.ssafy.happyhouse.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.HouseDealInfoDTO;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HouseMapService;

@Controller
@RequestMapping("/map")
@CrossOrigin("*")
public class HouseMapController {

	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;

	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt2(@RequestParam("lat1") String lat1, 
			@RequestParam("lat2") String lat2, 
			@RequestParam("lng1") String lng1, @RequestParam("lng2") String lng2, 
			@RequestParam("min") String min, @RequestParam("max") String max) throws Exception {
		System.out.println(lat1 + " " + lat2  + " " + lng1 + " " + lng2 +" " + min +" " + max );
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getHouseDistance(lat1, lat2,lng1, lng2, Integer.parseInt(min), Integer.parseInt(max)), HttpStatus.OK);
	}
	
	@GetMapping("/aptdeal")
	public ResponseEntity<List<HouseDealInfoDTO>> apt2(@RequestParam("aptCode") String aptCode) throws Exception {
		System.out.println(aptCode);
		return new ResponseEntity<List<HouseDealInfoDTO>>(haHouseMapService.getHouseDeal(aptCode), HttpStatus.OK);
	}
	
}
