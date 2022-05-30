<template>
  <b-container class="bv-example-row mt-3 views">
    <br /><br /><br /><br />
    <h3 class="underline-hotpink">
      <img src="https://img.icons8.com/color/48/000000/clr-beemo.png" />
      자유게시판 &nbsp;
    </h3>
    <router-view></router-view>
  </b-container>
</template>
<script>
export default {
  name: "BoardView",
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.3) 30%
  );
  font-weight: bold;
}
</style>
