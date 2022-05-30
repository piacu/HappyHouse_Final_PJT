<template>
  <b-container>
    <b-list-group>
      <member-inter-list-detail
        v-for="(house, index) in getUserInter"
        :key="index"
        :house="house"
      />
    </b-list-group>
    <br /><br /><br />
  </b-container>
</template>

<script>
import { mapGetters } from "vuex";
import MemberInterListDetail from "@/components/user/MemberInterListDetail.vue";
export default {
  name: "InterDetail",
  components: {
    MemberInterListDetail,
  },
  computed: {
    ...mapGetters(["getUserInter"]),
  },
};
</script>

<style></style>
