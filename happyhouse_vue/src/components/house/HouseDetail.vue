<template>
  <b-container v-if="getHouse" class="bv-example-row px-4">
    <b-row>
      <!-- <b-col> -->
      <h3 id="title">
        {{ getHouse.aptName }}
        <b-button
          size="lg"
          variant="none"
          @click="insertinter()"
          v-if="getLoginUser && !getIsInterVisible"
        >
          <b-icon-suit-heart variant="danger"></b-icon-suit-heart>
        </b-button>

        <b-button
          size="lg"
          variant="none"
          @click="deleteinter()"
          v-if="getLoginUser && getIsInterVisible"
          ><b-icon-suit-heart-fill variant="danger"></b-icon-suit-heart-fill>
        </b-button>
      </h3>
      <!-- <div v-if="loginUser"> -->

      <!-- </div> -->
      <!-- </b-col> -->
    </b-row>
    <b-row><br /></b-row>
    <b-row>
      <div>
        <b-table stacked :items="[getHouse]" :fields="fields" id="detailtable">
          <template #cell(buildYear)="data">
            {{ data.item.buildYear }}년
          </template>
          <template #cell(sidoName)="data">
            {{ data.item.sidoName }} {{ data.item.gugunName }}
            {{ data.item.dongName }}
          </template>
          <template #cell(recentPrice)="data">
            {{ data.item.recentPrice }}만원
          </template>
        </b-table>
      </div>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "HouseDetail",
  data() {
    return {
      fields: [
        { key: "buildYear", label: "건축년도" },
        { key: "sidoName", label: "주소" },
        { key: "recentPrice", label: "최근거래금액" },
      ],
      items: null,
      visible: false,
    };
  },
  computed: {
    ...mapGetters(["getHouse", "getIsInterVisible", "getLoginUser"]),
  },
  methods: {
    ...mapActions(["insertUserInter", "deleteUserInter"]),
    insertinter() {
      this.$store.dispatch("insertUserInter", {
        id: this.getLoginUser.id,
        house: this.getHouse,
      });
    },
    deleteinter() {
      this.deleteUserInter({
        id: this.getLoginUser.id,
        aptCode: this.getHouse.aptCode,
      });
    },
  },
  watch: {
    isInterVisible() {
      if (this.isInterVisible) {
        console.log("TRUE 임니다");
      } else {
        console.log("FALSE 임니다");
      }
    },
  },
};
</script>

<style>
#title {
  text-align: center;
  font-size: 35px;
  font-weight: bold;
  margin: 10;
}
.div {
  margin-top: 10;
  margin-bottom: 10;
}
.btn.btn-none.btn-lg {
  padding: 1px !important;
}
#detailtable th {
  background-color: #e1dcd9;
  border-top: 2px solid #e1dcd9;
  border-bottom: 2px solid #e1dcd9;
}
#detailtable td {
  /* padding: 0.75rem !important; */
  /* vertical-align: top; */
  border-top: 2px solid #e1dcd9;
  border-bottom: 2px solid #e1dcd9;
}
</style>
