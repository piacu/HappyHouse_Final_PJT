package com.ssafy.happyhouse.model;

public class HouseInfoDto implements Comparable<HouseInfoDto>{

	private String aptCode;
	private String apartmentName;
	private String dongCode;
	private String dong;
	private String sidoName;
	private String gugunName;
	private int buildYear;
	private String jibun;
	private String lat;
	private String lng;
	private String img;
	private String recentPrice;
	private double dist;
	private String regtime;

	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getAptName() {
		return apartmentName;
	}
	public void setAptName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getDongName() {
		return dong;
	}
	public void setDongName(String dong) {
		this.dong = dong;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public int getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRecentPrice() {
		return recentPrice;
	}
	public void setRecentPrice(String recentPrice) {
		this.recentPrice = recentPrice;
	}
	public double getDist() {
		return dist;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	
	@Override
	public String toString() {
		return "HouseInfoDto [aptCode=" + aptCode + ", apartmentName=" + apartmentName + ", dongCode=" + dongCode
				+ ", dong=" + dong + ", sidoName=" + sidoName + ", gugunName=" + gugunName + ", buildYear=" + buildYear
				+ ", jibun=" + jibun + ", lat=" + lat + ", lng=" + lng + ", img=" + img + ", recentPrice=" + recentPrice
				+ ", dist=" + dist + ", regtime=" + regtime + "]";
	}
	@Override
	public int compareTo(HouseInfoDto o) {
		return Double.compare(this.getDist(), o.getDist());
	}

}
