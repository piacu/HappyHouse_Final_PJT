<template>
  <b-container class="bv-example-row mt-3 text-center">
    <br /><br /><br /><br />
    <h3 class="underline-steelblue">Recommend House</h3>
    <recommend-list :categories="categories"></recommend-list>
  </b-container>
</template>

<script>
import RecommendList from "@/components/house/RecommendList.vue";

export default {
  name: "RecommendView",
  components: {
    RecommendList,
  },
  data() {
    return {
      //체크박스로 선택했을 시 값이 0,1,2 ... 이렇게 넘어 옴
      categories: [
        "0", //"편의점",
        // "대형마트",
        "1", //"은행",
        // "음식점",
        // "카페",
        // "병원",
        // "약국",
        // "교통",
        // "학교",
        // "어린이집, 유치원",
      ],
    };
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(196, 51, 240, 0.3) 30%
  );
}
</style>
