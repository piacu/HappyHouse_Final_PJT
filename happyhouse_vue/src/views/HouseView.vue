<template>
  <div class="map_wrap">
    <kakao-map></kakao-map>
  </div>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap.vue";

export default {
  data() {
    return {
      map: null,
      sidoList: {},
      gugunList: {},
      dongList: {},
    };
  },
  components: {
    KakaoMap,
  },
  methods: {},
};
</script>

<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 500px;
}
</style>
