<template>
  <b-container class="px-3 py-2">
    <!-- <div :style="getIsRmdVisible"> -->
    <b-sidebar v-model="getIsSidebarOpen" id="sidebar-right" right shadow>
      <div class="px-3 py-2">
        <house-detail />
        <house-deal />
        <infra-info />
      </div>
      <br /><br />
      <br /><br />
    </b-sidebar>
    <!-- </div> -->
  </b-container>
</template>

<script>
import HouseDetail from "@/components/house/HouseDetail.vue";
import HouseDeal from "@/components/house/HouseDeal.vue";
import InfraInfo from "@/components/house/InfraInfo.vue";
import { mapGetters, mapState } from "vuex";

export default {
  components: {
    HouseDetail,
    InfraInfo,
    HouseDeal,
  },
  computed: {
    ...mapState(["housestore/isSidebarOpen"]),
    ...mapGetters(["getIsSidebarOpen"]),
    // getIsSidebarOpen: {
    //   set: function () {},
    //   get: function () {
    //     return this.$store.state.isSidebarOpen;
    //   },
    // },
  },
};
</script>

<style>
.b-sidebar.b-sidebar-right {
  top: 56.5px !important;
  width: 400px !important;
}
</style>
