import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import housestore from "@/store/modules/housestore.js";
import userstore from "@/store/modules/userstore.js";
import infrastore from "@/store/modules/infrastore.js";

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    housestore,
    userstore,
    infrastore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

export default store;
