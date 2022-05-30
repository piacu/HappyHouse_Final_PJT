<template>
  <div>
    <div id="map"></div>
    <search-bar v-on:mvCenter="moveCenter"></search-bar>
    <side-bar />
    <chatting-app></chatting-app>
  </div>
</template>

<script type="text/javascript" src="@/assets/js/map.js"></script>
<script>
import SearchBar from "@/components/map/SearchBar.vue";
import SideBar from "@/components/map/SideBar.vue";
import ChattingApp from "@/components/map/ChattingApp.vue";
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
  data() {
    return {
      map: null,
      clusterer: null,
      markers: [],
      latlng: {
        swLatlng: null,
        neLatlng: null,
      },
    };
  },
  components: {
    SearchBar,
    SideBar,
    ChattingApp,
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addMapScript();
  },
  watch: {
    // store의 houses가 변경되면 마커 다시 찍기
    getHouses: function (value) {
      this.updataMap(value);
    },
    getInfraCondition: function () {
      var latlng = this.map.getCenter();
      this.SET_X(latlng.Ma);
      this.SET_Y(latlng.La);
      this.initMap();
    },
    getselrecmd: function (value) {
      if (value != null) {
        this.updataMap(this.getHouses);
      }
    },
    getUserInter: function () {
      this.updataMap(this.getHouses);
    },
  },
  computed: {
    ...mapGetters([
      "getselrecmd",
      "getHouses",
      "getHouse",
      "getX",
      "getY",
      "getUserInter",
      "getInfraCondition",
      "getIsRecmdMarker",
    ]),
  },
  methods: {
    ...mapActions([
      "getHouseList",
      "detailHouse",
      "ALLCATEGORY",
      "getHouseDealList",
      "FIND",
    ]),
    ...mapMutations([
      "SET_SIDEBAR",
      "RESET_XY",
      "SET_ISINTERVISIBLE",
      "SET_X",
      "SET_Y",
      "SET_SEL", // 이거 머야?
      "SET_ISRECMDMARKER",
      "CLEAR_SELRECMD",
      "CLEAR_DEALTYPECONDITION",
      "CLEAR_MONEYCONDITION",
      "CLEAR_INFRACONDITION_LIST",
    ]),
    // 초기 맵 시작
    addMapScript() {
      this.SET_SIDEBAR(false);
      this.SET_ISRECMDMARKER(false);
      this.CLEAR_DEALTYPECONDITION();
      this.CLEAR_MONEYCONDITION();
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAOMAP_KEY}&libraries=services,clusterer`;
      document.head.appendChild(script);
    },
    // 맵 재로딩시
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.getX, this.getY),
        level: 3,
        maxLevel: 6,
      };
      this.map = new kakao.maps.Map(container, options);

      // 마커 클러스터러를 생성합니다
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: this.map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 5, // 클러스터 할 최소 지도 레벨
      });

      kakao.maps.event.addListener(this.map, "tilesloaded", this.getAreaInfo);
      kakao.maps.event.addListener(this.map, "click", this.closeSidebar);
      this.RESET_XY();
      // console.log("[맵 재로딩 완료] x좌표 : ", this.x, " Y좌표 : ", this.y);
    },

    // 지도 영역정보와 영역 내의 아파트 정보를 얻어옵니다
    getAreaInfo() {
      let Map = this.map;
      var bounds = Map.getBounds();
      // 영역정보의 남서쪽 정보를 얻어옵니다
      var swLatlng = bounds.getSouthWest();
      // 영역정보의 북동쪽 정보를 얻어옵니다
      var neLatlng = bounds.getNorthEast();

      this.latlng.swLatlng = swLatlng.toString();
      this.latlng.neLatlng = neLatlng.toString();
      var categorycode = this.getInfraCondition;
      // getHouseList - axios
      this.getHouseList(this.latlng).then(() => {
        this.FIND({ houses: this.getHouses, categorycode: categorycode });
      });
    },
    // 지도가 업데이트 되었으므로 이전에 있던 마크 데이터를 갱신한다.
    updataMap(houses) {
      this.markers = [];
      this.clusterer.clear();
      for (var i = 0; i < houses.length; i++) {
        var pos = new kakao.maps.LatLng(houses[i].lat, houses[i].lng);
        this.addMarker(pos, houses[i]);
      }
    },

    addMarker(pos, data) {
      var imageSize = new kakao.maps.Size(35, 35);
      var scr_red =
        "https://img.icons8.com/color/48/E74C3C/romantic-place--v1.png";
      var scr_orange = "https://img.icons8.com/ios-filled/48/E67E22/marker.png";
      var scr_green = "https://img.icons8.com/ios-filled/48/08BB54/marker.png";

      if (this.getselrecmd != null && this.isrecmd(data.aptCode)) {
        var markerImage = new kakao.maps.MarkerImage(scr_green, imageSize);
      } else if (this.iszzim(data.aptCode)) {
        var markerImage = new kakao.maps.MarkerImage(scr_red, imageSize);
      } else {
        var markerImage = new kakao.maps.MarkerImage(scr_orange, imageSize);
      }

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        position: pos, // 마커를 표시할 위치
        image: markerImage, // 마커 이미지
        clickable: true,
      });
      marker.setMap(this.map);

      this.markers.push(marker);
      this.clusterer.addMarkers(this.markers);
      // 마커에 표시할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
        content: data.aptName, // 인포윈도우에 표시할 내용
      });
      // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
      // 이벤트 리스너로는 클로저를 만들어 등록합니다
      // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
      var overEL = this.makeOverListener(this.map, marker, infowindow);
      var outEL = this.makeOutListener(infowindow);
      kakao.maps.event.addListener(marker, "mouseover", overEL);
      kakao.maps.event.addListener(marker, "mouseout", outEL);

      var open = this.openSidebar;
      var select = this.selectInfo;
      var loaddata = this.loadData;
      var getdeal = this.getAptDeal;
      var setinter = this.setuserinter;
      kakao.maps.event.addListener(marker, "click", function () {
        select(data);
        loaddata();
        getdeal(data.aptCode);
        setinter(data.aptCode);
        open();
      });
    },

    moveCenter() {
      var moveLatLon = new kakao.maps.LatLng(this.getY, this.getX);
      this.map.setCenter(moveLatLon);
      this.getAreaInfo;
    },
    iszzim(aptCode) {
      for (var i = 0; i < this.getUserInter.length; i++) {
        if (this.getUserInter[i].aptCode === aptCode) {
          this.SET_ISINTERVISIBLE(true);
          return true;
        }
      }
      return false;
    },
    isrecmd(aptCode) {
      if (this.getselrecmd.aptCode === aptCode && this.getIsRecmdMarker) {
        return true;
      }
      return false;
    },
    // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
    // setMarkers(map) {
    //   for (var i = 0; i < this.markers.length; i++) {
    //     this.markers[i].setMap(map);
    //   }
    // },
    // clearMarkers() {
    //   this.setMarkers(null);
    //   this.markers = [];
    // },

    // actions function
    getAptDeal(aptCode) {
      this.getHouseDealList(aptCode);
    },
    loadData() {
      this.ALLCATEGORY({
        radius: 500,
        x: this.getHouse.lng,
        y: this.getHouse.lat,
      });
    },
    selectInfo(data) {
      this.detailHouse(data);
    },

    // mutation update
    setuserinter(aptCode) {
      this.SET_ISINTERVISIBLE(false);
      for (var i = 0; i < this.getUserInter.length; i++) {
        if (this.getUserInter[i].aptCode === aptCode) {
          this.SET_ISINTERVISIBLE(true);
          break;
        }
      }
    },
    openSidebar() {
      this.SET_SIDEBAR(true);
    },
    closeSidebar() {
      this.SET_SIDEBAR(false);
    },

    // kakao method //
    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },
    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
    },
  },
};
</script>

<style></style>
