<template>
  <b-list-group-item
    href="#"
    @click="itemDetailPush(house)"
    class="flex-column align-items-start"
  >
    <div class="d-flex w-100 justify-content-between">
      <h4 class="mb-1 title-design">{{ house.aptName }}</h4>
      <small class="text-muted">{{ house.regtime | regtime_substr }}</small>
    </div>

    <p class="mb-2" style="text-align: left">
      {{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}
      {{ house.jibun }}
    </p>
    <h5 class="mb-1" style="color: rgb(189 105 89); text-align: left">
      {{ house.recentPrice }}만원
    </h5>
  </b-list-group-item>
</template>

<script>
import router from "@/router";
import { mapMutations } from "vuex";
export default {
  props: {
    house: Object,
  },
  methods: {
    ...mapMutations([
      "SET_DETAIL_HOUSE",
      "SET_MAPCENTER",
      "SET_XY",
      "SET_SIDEBAR",
      "CLEAR_DEALTYPECONDITION",
      "CLEAR_MONEYCONDITION",
      "CLEAR_INFRACONDITION_LIST",
      "SET_ISINTERVISIBLE",
    ]),
    itemDetailPush(house) {
      this.SET_DETAIL_HOUSE(house);
      this.SET_XY(house);
      this.SET_SIDEBAR(true);
      this.CLEAR_DEALTYPECONDITION();
      this.CLEAR_MONEYCONDITION();
      this.CLEAR_INFRACONDITION_LIST();
      this.SET_ISINTERVISIBLE(true);
      router.push({ name: "house" });
    },
  },
  filters: {
    regtime_substr(value) {
      return value.substr(0, 10);
    },
  },
};
</script>

<style>
.title-design {
  color: #8f8681;
  text-align: inherit;
  font-weight: bold;
}
</style>
