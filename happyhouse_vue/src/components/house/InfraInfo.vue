<template>
  <b-container class="py-2">
    <div class="accordion" role="tablist">
      <b-list-group>
        <b-list-group-item id="headertitle" variant="secondary"
          ><b-row>
            <b-col id="headertitle">주변 시설</b-col>
            <b-col></b-col>
            <b-col
              ><b-button
                @click="allOpenClose"
                size="sm"
                variant="secondary"
                pill
                style="font-size: 11px; background-color: #8f8681; border: none"
                >{{ opencloseState }}</b-button
              ></b-col
            >
          </b-row></b-list-group-item
        >

        <b-list-group-item variant="light">
          <b-card
            no-body
            class="mb-3"
            v-for="(items, index) in getAllCategory"
            :key="index"
          >
            <b-card-header header-tag="header" class="p-1" role="tab">
              <b-button block v-b-toggle="'collapse-' + index" variant="light"
                ><b-row class="px-2" align-v="center">
                  <b-col class="text-left p-0">
                    <h6 class="mb-0 pl-2 font-weight-bolder text-left">
                      {{ category[index] }}
                    </h6>
                  </b-col>
                </b-row></b-button
              >
            </b-card-header>
            <b-collapse
              :id="'collapse-' + index"
              role="tabpanel"
              :visible="visible[index]"
            >
              <b-card-body>
                <b-row class="px-2">
                  <b-col class="text-left p-0">
                    <h6
                      v-if="items.length == 0"
                      class="p-0 my-1 mx-auto"
                      style="color: dimgrey"
                    >
                      <b-icon-emoji-expressionless></b-icon-emoji-expressionless>
                      근처에서 {{ category[index] }}시설을 찾지 못했습니다.
                    </h6>
                    <h6
                      class="p-0 my-1"
                      style="color: dimgrey"
                      v-for="(item, index) in items"
                      :key="index"
                    >
                      <b-badge variant="light">{{ item.distance }}m</b-badge
                      >거리에
                      <b-badge variant="light">{{ item.place_name }}</b-badge
                      >이(가) 있어요
                    </h6>
                  </b-col>
                </b-row>
              </b-card-body>
            </b-collapse>
          </b-card>
          <b-card no-body class="mb-4">
            <b-card-header header-tag="header" class="p-1" role="tab">
              <b-button block v-b-toggle="`collapse-8`" variant="light"
                ><b-row class="px-2" align-v="center">
                  <b-col class="text-left p-0">
                    <h6 class="mb-0 pl-2 font-weight-bolder text-left">학군</h6>
                  </b-col>
                </b-row>
              </b-button>
            </b-card-header>
            <b-collapse
              :id="'collapse-8'"
              role="tabpanel"
              :visible="visible[7]"
            >
              <b-card-body>
                <b-card-text>
                  <b-card
                    no-body
                    class="mb-3"
                    v-for="(items, index) in getEducations"
                    :key="index"
                  >
                    <b-card-header header-tag="header" class="p-1" role="tab">
                      <b-button
                        block
                        v-b-toggle="'collapse-' + index + 8"
                        variant="light"
                        ><b-row class="px-2" align-v="center">
                          <b-col class="text-left p-0">
                            <h6 class="mb-0 pl-2 font-weight-bolder text-left">
                              {{ category[index + 8] }}
                            </h6>
                          </b-col>
                        </b-row></b-button
                      >
                    </b-card-header>
                    <b-collapse
                      :id="'collapse-' + index + 8"
                      role="tabpanel"
                      :visible="visible[index + 7]"
                    >
                      <b-card-body>
                        <b-row class="px-2">
                          <b-col class="text-left p-0">
                            <h6
                              v-if="items.length == 0"
                              class="p-0 my-1 mx-auto"
                              style="color: dimgrey"
                            >
                              <b-icon-emoji-expressionless></b-icon-emoji-expressionless>
                              근처에서 {{ category[index + 8] }}시설을 찾지
                              못했습니다.
                            </h6>
                            <h6
                              class="p-0 my-1"
                              style="color: dimgrey"
                              v-for="(item, index) in items"
                              :key="index"
                            >
                              <b-badge variant="light"
                                >{{ item.distance }}m</b-badge
                              >거리에
                              <b-badge variant="light">{{
                                item.place_name
                              }}</b-badge
                              >이(가) 있어요
                            </h6>
                          </b-col>
                        </b-row>
                      </b-card-body>
                    </b-collapse>
                  </b-card>
                </b-card-text>
              </b-card-body>
            </b-collapse>
          </b-card>
        </b-list-group-item>
      </b-list-group>
    </div>
  </b-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import { mapActions } from "vuex";

export default {
  props: {
    // no: String, 아파트코드번호가 필요할수도?
    lat: String,
    lng: String,
  },
  data() {
    return {
      radius: 500, //범위 : 반경 500M내 5개로
      category: [
        "편의점",
        "대형마트",
        "은행",
        "음식점",
        "카페",
        "병원",
        "약국",
        "교통",
        "학교",
        "어린이집, 유치원",
      ],
      opencloseState: "All Open",
      allVisible: false,
      visible: [
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
      ],
    };
  },
  watch: {
    // house: function () {
    //   this.visible = false;
    // },
  },
  computed: {
    ...mapState(["house"]),
    ...mapGetters([
      "getConveniences",
      "getMarts",
      "getBanks",
      "getRestaurants",
      "getCafes",
      "getHospitals",
      "getPharmacies",
      "getTraffics",
      "getSchools",
      "getPSchools",
      "getEducations",
      "getAllCategory",
    ]),
  },
  methods: {
    ...mapActions([
      "CONVENIENCE",
      "MART",
      "BANK",
      "RESTAURANT",
      "CAFE",
      "HOSPITAL",
      "PHARMACY",
      "TRAFFIC",
      "SCHOOL",
      "PSCHOOL",
      "ALLCATEGORY",
    ]),
    loadData() {
      this.ALLCATEGORY({
        radius: this.radius,
        x: this.house.lng,
        y: this.house.lat,
      });
    },
    allOpenClose() {
      if (this.allVisible)
        this.visible = [
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          false,
        ];
      else
        this.visible = [
          true,
          true,
          true,
          true,
          true,
          true,
          true,
          true,
          true,
          true,
          true,
        ];
      this.opencloseState = this.allVisible ? "All Open" : "All Close";
      this.allVisible = !this.allVisible;
    },
  },
};
</script>

<style>
#headertitle {
  font-size: 15px;
  font-weight: bold;
  border: none;
  background-color: #e1dcd9;
}
</style>
