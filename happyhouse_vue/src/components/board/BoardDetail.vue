<template>
  <b-container class="bv-example-row mt-3">
    <br /><br />
    <b-row class="mb-1">
      <b-col>
        <b-card-header
          style="text-align: left; padding: 30px; border: 1px #bcbcbc solid"
        >
          <h3>{{ article.subject }}</h3>
          <div style="text-align: right">
            <h6>{{ article.regtime | dateFormat }}</h6>
            <h6>작성자 : {{ article.userid }}</h6>
          </div>
          <hr />
          <b-card-body class="text-left" style="height: 300px">
            <div v-html="message"></div>
          </b-card-body>
        </b-card-header>
      </b-col>
    </b-row>
    <br />
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          v-if="getLoginUser.id === article.userid"
          >글수정</b-button
        >
        <b-button
          variant="outline-danger"
          size="sm"
          @click="deleteArticle"
          v-if="
            (getLoginUser.id === article.userid &&
              getLoginUser.id != 'ssafy') ||
            (getLoginUser.id === article.userid && getLoginUser.id === 'ssafy')
          "
          >글삭제</b-button
        >
        <b-button
          variant="outline-danger"
          size="sm"
          @click="deleteArticle"
          v-if="
            getLoginUser.id != article.userid && getLoginUser.id === 'ssafy'
          "
          >관리자 권한으로 글삭제</b-button
        >
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import moment from "moment";
import http from "@/api/http";
import { mapGetters } from "vuex";

export default {
  name: "BoardDetail",
  data() {
    return {
      article: {},
    };
  },
  computed: {
    ...mapGetters(["getLoginUser"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
    });
  },
  methods: {
    check() {
      if (!this.getLoginUser) {
        alert("로그인하고 이용해주세요.");
        this.$router.push({ name: "signIn" });
      }
    },
    listArticle() {
      this.$router.push({ name: "boardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "boardModify",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "boardDelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>

<style>
.alert.alert-info {
  background-color: #e0c8c4 !important;
  border: none !important;
  color: black !important;
}
</style>
