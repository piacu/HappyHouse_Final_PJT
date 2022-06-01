<template>
  <!-- Navigation-->
  <nav
    class="navbar navbar-expand-lg navbar-default fixed-top shadow-sm"
    id="mainNav"
  >
    <div class="container-fluid px-5">
      <a href="/">
        <img
          class="houselogo"
          src="@/assets/img/house_logo_nobg.png"
          alt="houselogo"
      /></a>
      <div class="mx-4"></div>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/"
            ><a href="/" class="header-text">시세조회</a></b-nav-item
          >
          <div class="mx-2"></div>
          <b-nav-item href="#" v-if="getLoginUser != null"
            ><router-link
              :to="{ name: 'userinter' }"
              class="text-white header-text"
              >찜한매물</router-link
            ></b-nav-item
          >
          <div class="mx-2"></div>
          <b-nav-item href="#"
            ><router-link :to="{ name: 'board' }" class="header-text"
              >게시판</router-link
            ></b-nav-item
          >

          <!-- <b-nav-item href="#"
            ><router-link :to="{ name: 'chattingview' }" class="text-white"
              ><b-icon icon="hand-thumbs-up" font-scale="1.5"></b-icon>
              채팅바로가기</router-link
            ></b-nav-item
          > -->
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <b-button pill variant="primary" v-if="getLoginUser == null"
            ><router-link :to="{ name: 'signIn' }"
              ><b-icon icon="person-circle"></b-icon> 로그인</router-link
            ></b-button
          >

          <b-nav-item href="#" v-if="getLoginUser != null"
            ><router-link :to="{ name: 'info' }" class="header-text"
              ><b-icon icon="person-circle"></b-icon>
              {{ getLoginUser.id }}님</router-link
            ></b-nav-item
          >
          <div class="mx-2"></div>
          <b-nav-item href="/" v-if="getLoginUser != null"
            ><a @click="logout" class="header-text"> 로그아웃</a></b-nav-item
          >
        </b-navbar-nav>
      </b-collapse>
    </div>
  </nav>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "HeaderNaviBar",
  methods: {
    ...mapActions(["logoutInfoUser"]),
    logout() {
      this.logoutInfoUser();
      //로그아웃 후 메인화면으로
      this.$router.go(this.$router.currentRoute);
    },
  },
  computed: {
    ...mapGetters(["getLoginUser"]),
  },
};
</script>

<style scoped>
#mainNav {
  padding-top: 0.5rem !important;
  padding-bottom: 0.5rem !important;
  z-index: 1000;
}
.houselogo {
  width: 60px;
  height: 40px;
}
.navbar-default {
  background-color: #8f8681 !important;
  border-color: #e7e7e7;
  color: #e7e7e7;
}
.btn.btn-primary.rounded-pill {
  background-color: #f0ebe8 !important;
  border: none !important;
  color: black !important;
}
.header-text {
  color: blanchedalmond;
  font-weight: 1000 !important;
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif;
}
a:link,
a:hover,
a:visited {
  color: #1c1919 !important;
  text-decoration: none !important;
}
</style>
