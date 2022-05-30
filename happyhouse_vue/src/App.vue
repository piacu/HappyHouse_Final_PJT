<template>
  <div id="app" style="height: 100%">
    <header-nav />
    <router-view />
  </div>
</template>

<script>
import HeaderNav from "@/components/layout/HeaderNav.vue";

export default {
  name: "App",
  components: {
    HeaderNav,
  },
};
</script>

<style>
@import "https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css";
@import "https://fonts.googleapis.com/css2?family=Newsreader:ital,wght@0,600;1,600&amp;display=swap";
@import "https://fonts.googleapis.com/css2?family=Mulish:ital,wght@0,300;0,500;0,600;0,700;1,300;1,500;1,600;1,700&amp;display=swap";
@import "https://fonts.googleapis.com/css2?family=Kanit:ital,wght@0,400;1,400&amp;display=swap";
@import "@/assets/styles/styles.css";
@import "@/assets/styles/apt.css";

html,
body {
  height: 100%;
}
.app {
  height: 100vh;
}
#map {
  width: 100%;
  height: 100vh;
  position: fixed;
  overflow: hidden;
}
#mainNav {
  padding-top: 0.5rem !important;
  padding-bottom: 0.5rem !important;
  z-index: 1000;
}
.views {
  text-align: center;
}
</style>
