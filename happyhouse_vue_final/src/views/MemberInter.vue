<template>
  <b-container class="mt-3 views">
    <br /><br /><br /><br />
    <div style="text-align: center">
      <h3 class="underline-steelblue">
        <!-- <b-icon icon="bookmark-heart-fill" variant="warning"></b-icon> -->
        <img
          src="https://img.icons8.com/external-flat-02-chattapat-/64/000000/external-heart-love-flat-02-chattapat--4.png"
        />
        내가 찜한 매물&nbsp;
      </h3>
    </div>
    <br /><br />
    <member-inter-list />
  </b-container>
</template>

<script>
import MemberInterList from "@/components/user/MemberInterList.vue";
export default {
  name: "InterList",
  components: {
    MemberInterList,
  },
};
</script>

<style>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
  font-weight: bold;
}
</style>
