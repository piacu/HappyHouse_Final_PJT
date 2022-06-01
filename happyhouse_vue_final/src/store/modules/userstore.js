// import axios from "axios";
import axios from "@/api/http";
import router from "@/router";

const housestore = {
  state: {
    loginUser: null,
    userinter: [],
  },
  getters: {
    getLoginUser(state) {
      return state.loginUser;
    },
    getUserInter(state) {
      return state.userinter;
    },
  },
  mutations: {
    SET_LOGIN_USER(state, user) {
      state.loginUser = user;
    },
    LOGOUT(state) {
      state.loginUser = null;
    },
    SET_INFO_USER(state, user) {
      state.loginUser = user;
    },
    SET_USERINTER_LIST(state, data) {
      state.userinter = data;
    },
    CLEAR_USERINTER(state) {
      state.userinter = [];
    },
  },
  actions: {
    setLoginUser(store, user) {
      // let matched = false;
      const params = {
        id: user.userid,
        password: user.userpwd,
      };
      axios
        .get(`/user`, { params })
        .then(({ data }) => {
          // console.log(data);
          if (data.id != null) {
            store.commit("SET_LOGIN_USER", data);
            // store.commit("housestore/SET_SIDEBAR", true, { root: true });
            store.commit("SET_SIDEBAR", false);
            store.commit("CLEAR_DEALTYPECONDITION");
            store.commit("CLEAR_INFRACONDITION_LIST");
            store.commit("CLEAR_MONEYCONDITION");
            router.push({ name: "house" });
            store.dispatch("getUserInter", user.userid);
          } else {
            let msg = "로그인에 실패하였습니다.";
            alert(msg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getUserInter({ commit }, id) {
      await axios
        .get(`userinterlist/${id}`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_USERINTER_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    setInfoUser({ commit }, user) {
      // let matched = false;
      axios
        .put(`/user`, {
          id: user.id,
          password: user.password,
          name: user.name,
          email: user.email,
          age: user.age,
        })
        .then(({ data }) => {
          commit("SET_INFO_USER", data);
          //router.push({ name: "home" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    clearInfoUser({ commit }, user) {
      const params = {
        id: user.id,
      };
      axios
        .delete(`/user`, { params })
        .then(() => {
          let msg = "회원 삭제 성공";
          alert(msg);

          //회원 탈퇴 시 로그아웃
          commit("LOGOUT");
          commit("SET_SIDEBAR", false);
          commit("RESET_XY");
          commit("CLEAR_DEALTYPECONDITION");
          commit("CLEAR_MONEYCONDITION");
          commit("CLEAR_USERINTER");
          //탈퇴 후 메인화면으로
          router.push("/");
        })
        .catch((error) => {
          let msg = "회원 삭제 처리 시 문제 발생";
          alert(msg);
          console.log(error);
        });
    },
    logoutInfoUser: function ({ commit }) {
      commit("LOGOUT");
      commit("SET_SIDEBAR", false);
      commit("RESET_XY");
      commit("CLEAR_DEALTYPECONDITION");
      commit("CLEAR_MONEYCONDITION");
      commit("CLEAR_USERINTER");
    },
    insertUserInter({ commit, dispatch }, data) {
      axios
        .post(`/userinter`, {
          id: data.id,
          aptCode: data.house.aptCode,
          apartmentName: data.house.aptName,
          buildyear: data.house.buildYear,
          dongCode: data.house.dongCode,
          dong: data.house.dongName,
          jibun: data.house.jibun,
          lat: data.house.lat,
          lng: data.house.lng,
          sidoname: data.house.sidoName,
          gugunname: data.house.gugunName,
          recentPrice: data.house.recentPrice,
        })
        .then(() => {
          let msg = "찜 성공";
          commit("SET_ISINTERVISIBLE", true);
          alert(msg);
          dispatch("getUserInter", data.id);
        })
        .catch((error) => {
          let msg = "찜하기 에러!!";
          alert(msg);
          console.log(error);
        });
    },
    deleteUserInter({ commit, dispatch }, data) {
      // console.log(commit);
      axios
        .delete(`/userinter/${data.id}/${data.aptCode}`)
        // .post(`/userinter`, { params })
        .then(() => {
          commit("SET_ISINTERVISIBLE", false);
          dispatch("getUserInter", data.id);
          alert("찜 삭제 완료");
        })
        .catch((error) => {
          let msg = "찜삭제하기 에러!!";
          alert(msg);
          console.log(error);
        });
    },
  },
  modules: {},
};

export default housestore;
