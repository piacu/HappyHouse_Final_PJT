<template>
  <div>
    <div id="menu_wrap" class="bg_white">
      <b-input-group size="sm">
        <b-form-input
          id="searchInput"
          placeholder="검색어를 입력해주세요"
          v-model="searchQuery"
          @keyup="searchPlace"
          @keyup.enter="goToResult(searchResList[0])"
        ></b-form-input>
        <b-button
          type="submit"
          variant="outline-secondary"
          @click="goToResult(searchResList[0])"
          ><b-img
            src="https://cdn-icons-png.flaticon.com/512/71/71403.png"
            fluid
            alt="돋보기"
            style="height: 18px; width: 18px"
          ></b-img
        ></b-button>
        <ul id="searchList">
          <li v-for="(searchRes, index) in searchResList" :key="index">
            <b-link
              @click.prevent="goToResult(searchRes)"
              style="font-size: 12px"
              >{{ searchRes.place_name }}</b-link
            >
          </li>
        </ul>
      </b-input-group>
      <b-button
        v-b-toggle.collapse-50
        size="sm"
        style="width: 100%"
        class="mt-2"
        >검색 조건</b-button
      >
      <b-collapse id="collapse-50">
        <search-bar-filter></search-bar-filter>
      </b-collapse>
    </div>

    <div :style="getIsRmdVisible">
      <b-card
        header="카테고리별 맞춤 추천"
        class="text-center"
        style="max-width: 16rem; margin: 38px; top: 100px"
      >
        <b-card-text>
          <div v-if="getselrecmd != null" @click="recmdInfo">
            <h3>
              <b-icon-bookmark-star
                variant="warning"
                font-scale="2"
              ></b-icon-bookmark-star>
              <br /><br />
              <b-badge variant="light">
                {{ getselrecmd.sidoName }} {{ getselrecmd.gugunName }}
                {{ getselrecmd.dongName }}
              </b-badge>
              에 위치한 <br />
              <b-badge variant="light">{{ getselrecmd.aptName }}</b-badge>
              을(를) 추천합니다.
            </h3>
          </div>
          <div v-else>
            <h3>
              <b-icon-x-circle
                variant="danger"
                font-scale="2"
              ></b-icon-x-circle>
              <br />
              <br />
              추천 매물이 없습니다.
            </h3>
          </div>
        </b-card-text>
      </b-card>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapMutations, mapGetters, mapActions } from "vuex";
import SearchBarFilter from "@/components/map/SearchBarFilter.vue";
export default {
  data() {
    return {
      searchQuery: "",
      isActive: false,
      searchResList: [],
      // isSideberOpen: this.$store.state.isSidebarOpen,
    };
  },
  components: {
    SearchBarFilter,
  },
  created() {
    this.SET_ISNOTRMDVISIBLE();
  },
  methods: {
    searchPlace() {
      const keyword = this.searchQuery.trim();
      // console.log("search keyword : " + keyword);
      // const auth = `${process.env.local.VUE_APP_KAKAOMAP_RESTAPI_KEY}`;
      const auth = "KakaoAK f1c36f65322c75f1f28caf1560a306d1";

      if (keyword.length === 0) {
        this.searchResList.splice(0, this.searchResList.length);
        return;
      }

      const link = `https://dapi.kakao.com/v2/local/search/keyword.json?query=${keyword}`;

      http
        .get(link, {
          headers: {
            "Content-Type": "application/json",
            Authorization: auth,
          },
        })
        .then(({ data }) => {
          // 불러 온 데이터는 data.documents[순서].변수명 으로 참조
          /*
          address_name : 주소
          category_name : 카테고리명
          distance : 현재 좌표와의 거리
          id : 고유번호
          phone : 연락처
          palce_name : 장소명
          place_url : 홈페이지
          road_address_name : 상세 주소
          x : 위도
          y : 경도
          */

          let docLength = data.documents.length;
          docLength = docLength > 10 ? 10 : docLength;

          // 배열 초기화
          this.searchResList.length = 0;

          if (docLength != 0) {
            // console.log(data);
            for (var i = 0; i < docLength; i++) {
              this.searchResList.push(data.documents[i]);
            }
            // console.log(this.searchResList);
          }
        });
    },
    ...mapMutations([
      "SET_ISNOTRMDVISIBLE",
      "SET_ISINTERVISIBLE",
      "SET_SIDEBAR",
      "SET_X",
      "SET_Y",
    ]),
    ...mapActions(["ALLCATEGORY", "getHouseDealList", "detailHouse"]),
    goToResult(value) {
      this.SET_X(value.x);
      this.SET_Y(value.y);
      this.$emit("mvCenter", value);
      this.SET_SIDEBAR(false);
      this.searchQuery = "";
      this.searchResList.length = 0;
    },
    // closeCollapse() {
    // 이 메소드 호출되면 collapse 닫히게 수정해야 함니다
    // },
    recmdInfo() {
      if (this.getselrecmd != null) {
        this.detailHouse(this.getselrecmd);
        this.loadData();
        this.getHouseDealList(this.getselrecmd.aptCode);
        this.setuserinter(this.getselrecmd.aptCode);
        this.openSidebar();
      }
    },
    // selectInfo(data) {
    //   this.detailHouse(data);
    // },
    loadData() {
      this.ALLCATEGORY({
        radius: 500,
        x: this.getselrecmd.lng,
        y: this.getselrecmd.lat,
      });
    },
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
      // this.isSideberOpen = true;
      this.SET_SIDEBAR(true);
    },
  },
  computed: {
    // ...mapState(["userinter", "isSidebarOpen"]),
    ...mapGetters(["getselrecmd", "getIsRmdVisible", "getUserInter"]),
  },
};
</script>

<style scoped>
#searchList {
  background-color: #ffffff;
  /* position: absolute; */
  width: 100%;
  z-index: 1;
}
li {
  margin-top: 5px;
  margin-bottom: 5px;
  margin-left: 10px;
}
#menu_wrap {
  top: 120px;
  left: 40px;
  bottom: 0;
  width: 250px;
  margin-bottom: 30px;
  padding: 5px;
  overflow-y: auto;
  background: #e2dcd9;
  z-index: 2;
  position: relative;
  font-size: 12px;
  border-radius: 10px;
}
.bg_white {
  background: #fff;
}
#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#menu_wrap .option {
  text-align: center;
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
}
#pagination {
  margin: 10px auto;
  text-align: center;
}
#pagination a {
  display: inline-block;
  margin-right: 10px;
}
#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}
#houseRecmd {
  max-width: 16rem;
  margin: 38px;
  top: 100px;
}
</style>
