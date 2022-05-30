<template>
  <b-tr>
    <b-td @click="addToCount(articleno)" v-if="isNotice === '1'">
      <strong>{{ articleno }}</strong></b-td
    >
    <b-td @click="addToCount(articleno)" v-else> {{ articleno }}</b-td>

    <b-th
      class="text-left"
      @click="addToCount(articleno)"
      v-if="isNotice === '1'"
      ><strong>{{ subject }}</strong>
    </b-th>
    <b-td class="text-left" @click="addToCount(articleno)" v-else
      >{{ subject }}
    </b-td>

    <b-td @click="addToCount(articleno)" v-if="isNotice === '1'"
      ><strong>{{ userid }}</strong></b-td
    >
    <b-td @click="addToCount(articleno)" v-else>{{ userid }}</b-td>

    <b-td @click="addToCount(articleno)" v-if="isNotice === '1'"
      ><strong>{{ regtime | dateFormat }}</strong></b-td
    >

    <b-td @click="addToCount(articleno)" v-else>{{
      regtime | dateFormat
    }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";
import { mapGetters } from "vuex";

export default {
  name: "BoardListItem",
  props: {
    articleno: Number,
    userid: String,
    subject: String,
    hit: Number,
    regtime: String,
    isNotice: String,
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
  methods: {
    addToCount(articleno) {
      if (!this.getLoginUser) {
        alert("로그인하고 이용해주세요.");
        this.$router.push({ name: "signIn" });
      } else {
        this.$router.push({
          name: "boardDetail",
          params: { articleno: articleno },
        });
      }
    },
  },
  computed: {
    ...mapGetters(["getLoginUser"]),
  },
};
</script>

<style></style>
