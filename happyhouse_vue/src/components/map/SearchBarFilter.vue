<template>
  <b-card>
    <p>거래 유형</p>
    <b-button-group size="sm" style="width: 100%">
      <b-button
        v-for="(btn, index) in dealTypeButtons"
        :key="index"
        @click="pressBtnDealType(index)"
        :pressed.sync="btn.state"
        variant="outline-secondary"
      >
        {{ btn.caption }}
      </b-button>
    </b-button-group>
    <div style="margin-top: 10px"></div>
    <!-- 매매 선택 시 -->
    <div v-if="selectDealType == 1">
      <label for="range-maemae"><strong>매매 금액</strong></label>
      <b-card>
        <p v-if="dealPrice[1][0] == 0 && dealPrice[1][1] == 20">전체 가격</p>
        <p v-else-if="dealPrice[1][0] == 0">
          {{ dealPrice[1][1] + "억원 까지" }}
        </p>
        <p v-else-if="dealPrice[1][1] == 20">
          {{ dealPrice[1][0] + "억원 부터" }}
        </p>
        <p v-else>
          {{ dealPrice[1][0] + "억원 ~ " + dealPrice[1][1] + "억원" }}
        </p>
        <br />
        <Slider
          id="range-maemae"
          class="slider"
          v-model="dealPrice[1]"
          showTooltip="drag"
          :min="0"
          :max="20"
        />
        <b-row class="text-center" style="padding-top: 5px">
          <b-col class="text-left">최소</b-col>
          <b-col class="text-right">최대</b-col>
        </b-row>
      </b-card>
    </div>
    <!-- 전세 선택 시 -->
    <div v-else-if="selectDealType == 2">
      <label for="range-jeonse"><strong>전세 금액</strong></label>
      <b-card>
        <p v-if="dealPrice[2][0] == 0 && dealPrice[2][1] == 20">전체 가격</p>
        <p v-else-if="dealPrice[2][0] == 0">
          {{ dealPrice[2][1] + "억원 까지" }}
        </p>
        <p v-else-if="dealPrice[2][1] == 20">
          {{ dealPrice[2][0] + "억원 부터" }}
        </p>
        <p v-else>
          {{ dealPrice[2][0] + "억원 ~ " + dealPrice[2][1] + "억원" }}
        </p>
        <br />
        <Slider
          id="range-jeonse"
          class="slider"
          v-model="dealPrice[2]"
          showTooltip="drag"
          :min="0"
          :max="20"
        />
        <b-row class="text-center" style="padding-top: 5px">
          <b-col class="text-left">최소</b-col>
          <b-col class="text-right">최대</b-col>
        </b-row>
      </b-card>
    </div>
    <!-- 월세 선택 시 -->
    <div v-else-if="selectDealType == 3">
      <label for="range-wolse1"><strong>보증금</strong></label>
      <b-card>
        <p v-if="dealPrice[3][0] == 0 && dealPrice[3][1] == 5000">전체 가격</p>
        <p v-else-if="dealPrice[3][0] == 0">
          {{ dealPrice[3][1] + "만원 까지" }}
        </p>
        <p v-else-if="dealPrice[3][1] == 5000">
          {{ dealPrice[3][0] + "만원 부터" }}
        </p>
        <p v-else>
          {{ dealPrice[3][0] + "만원 ~ " + dealPrice[3][1] + "만원" }}
        </p>
        <br />
        <Slider
          id="range-wolse1"
          class="slider"
          v-model="dealPrice[3]"
          showTooltip="drag"
          :min="0"
          :max="5000"
          :step="100"
        />
        <b-row class="text-center" style="padding-top: 5px">
          <b-col class="text-left">최소</b-col>
          <b-col class="text-right">최대</b-col>
        </b-row>
      </b-card>
      <label for="range-wolse2" style="margin-top: 10px"
        ><strong>월세</strong></label
      >
      <b-card>
        <p v-if="dealPrice[4][0] == 10 && dealPrice[4][1] == 200">전체 가격</p>
        <p v-else-if="dealPrice[4][0] == 10">
          {{ dealPrice[4][1] + "만원 까지" }}
        </p>
        <p v-else-if="dealPrice[4][1] == 200">
          {{ dealPrice[4][0] + "만원 부터" }}
        </p>
        <p v-else>
          {{ dealPrice[4][0] + "만원 ~ " + dealPrice[4][1] + "만원" }}
        </p>
        <br />
        <Slider
          id="range-wolse2"
          class="slider"
          v-model="dealPrice[4]"
          showTooltip="drag"
          :min="10"
          :max="200"
          :step="10"
        />
        <b-row class="text-center" style="padding-top: 5px">
          <b-col class="text-left">최소</b-col>
          <b-col class="text-right">최대</b-col>
        </b-row>
      </b-card>
    </div>
    <label for="infra" style="margin-top: 5px">주변 시설</label>
    <b-button-group size="sm" style="width: 100%">
      <b-button
        v-for="(btn, index) in infraButtons1"
        :key="index"
        @click="pressBtnInfra(index, 0)"
        squared
        variant="outline-secondary"
        :pressed.sync="btn.state"
        ><p>{{ btn.caption }}</p></b-button
      ></b-button-group
    >
    <b-button-group size="sm" style="width: 100%">
      <b-button
        v-for="(btn, index) in infraButtons2"
        :key="index"
        @click="pressBtnInfra(index, 1)"
        squared
        variant="outline-secondary"
        :pressed.sync="btn.state"
        ><p>{{ btn.caption }}</p></b-button
      >
    </b-button-group>
    <b-button
      @click="submit"
      variant="secondary"
      style="width: 100%; margin-top: 10px"
      size="sm"
      v-b-toggle.collapse-50
      >적용하기</b-button
    >
    <!-- <p v-for="(sta, idx) in infraButtons1" :key="idx">
        Pressed States: <strong>{{ idx }} : {{ sta.state }}</strong>
      </p> -->
  </b-card>

  <!--
    Vue Slider를 사용하기 위해 아래 모듈 설치 필요
    npm install @vueform/slider
    npm install @vue/composition-api --save-dev
  -->
</template>

<script>
import Slider from "@vueform/slider/dist/slider.vue2.js";
import { mapActions, mapMutations } from "vuex";

export default {
  data() {
    return {
      dealTypeButtons: [
        { caption: "전체", state: true },
        { caption: "매매", state: false },
        { caption: "전세", state: false },
        { caption: "월세", state: false },
      ],
      infraButtons1: [
        { caption: "전체", code: "ALL", state: true },
        { caption: "편의점", code: "CS2", state: false },
        { caption: "마트", code: "MT1", state: false },
        { caption: "은행", code: "BK9", state: false },
        { caption: "학군", code: ["SC4", "PS3"], state: false },
      ],
      infraButtons2: [
        { caption: "카페", code: "CE7", state: false },
        { caption: "음식점", code: "FD6", state: false },
        { caption: "병원", code: "HP8", state: false },
        { caption: "약국", code: "PM9", state: false },
        { caption: "교통", code: "SW8", state: false },
      ],
      selectDealType: 0,
      deposit: 0,
      monthMoney: 0,
      dealPrice: [
        [0, 9999],
        [0, 20],
        [0, 20],
        [0, 5000],
        [10, 200],
      ],
    };
  },
  components: {
    Slider,
  },
  methods: {
    pressBtnDealType(index) {
      this.selectDealType = index;
      // console.log("selectdealtype : " + this.selectDealType);
      for (var i = 0; i < 4; i++) {
        this.dealTypeButtons[i].state = false;
      }
      this.dealTypeButtons[index].state = true;
    },
    pressBtnInfra(index, num) {
      if (index == 0 && num == 0) {
        for (let i = 1; i < 5; i++) {
          this.infraButtons1[i].state = false;
        }
        for (let i = 0; i < 5; i++) {
          this.infraButtons2[i].state = false;
        }
      } else {
        this.infraButtons1[0].state = false;
      }
    },
    // ...mapState(["isRmdVisible"]),
    ...mapMutations([
      "CLEAR_DEALTYPECONDITION",
      "CLEAR_MONEYCONDITION",
      "CLEAR_INFRACONDITION_LIST",
      "SET_ISRMDVISIBLE",
      "SET_ISRECMDMARKER",
      "SET_SIDEBAR",
    ]),
    ...mapActions([
      "setDealTypeCondition",
      "setMoneyCondition",
      "setInfraConditionList",
    ]),
    submit() {
      this.CLEAR_DEALTYPECONDITION();
      this.CLEAR_MONEYCONDITION();
      this.CLEAR_INFRACONDITION_LIST();
      this.SET_ISRMDVISIBLE();
      this.SET_ISRECMDMARKER(true);
      this.SET_SIDEBAR(false);
      let dCondition = 0;
      let mCondition = [];
      let iCondition = [];

      dCondition = this.selectDealType;
      // 매매에서 무제한이면 9999
      if (this.dealPrice[1][1] == 20) {
        this.dealPrice[1][1] = 9999;
      }

      mCondition.push(this.dealPrice);

      for (let i = 0; i < 5; i++) {
        if (this.infraButtons1[0].state == true) {
          //전체를 선택하면 전체 버튼만 true
          //전체 카테고리를 넣어주고 반복문 나가기
          iCondition = [
            "CS2",
            "MT1",
            "BK9",
            "FD6",
            "CE7",
            "HP8",
            "PM9",
            "SW8",
            "SC4",
            "PS3",
          ];
          break;
        }
        if (this.infraButtons1[i].state == true) {
          if (i == 4) {
            //학군 버튼일 때 2개의 값이기 때문에 따로 넣어주기
            var edu = this.infraButtons1[i].code;
            for (let j = 0; j < 2; j++) {
              // console.log(edu[j]);
              iCondition.push(edu[j]);
              // iCondition.push(this.infraButtons1[i].code);
            }
          } else {
            iCondition.push(this.infraButtons1[i].code);
          }
        }

        if (this.infraButtons2[i].state == true)
          iCondition.push(this.infraButtons2[i].code);
      }

      console.log("거래유형 조건 : ", dCondition);
      console.log("금액 조건 : ", mCondition);
      console.log("인프라조건 : ", iCondition);

      this.setDealTypeCondition(dCondition);
      this.setMoneyCondition(mCondition);
      this.setInfraConditionList(iCondition);

      this.$emit("closeCollapse");
      // 적용하기 버튼 누르면 조건이 셋팅되고
      // 그 x, y값으로 이동한다음에 리스트를 다시 출력해야된다.
    },
  },
  watch: {},
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
<style scoped>
.card-body {
  padding: 10px;
  position: inherit;
}
.slider {
  padding-left: 10px;
  padding-right: 10px;
}
</style>
