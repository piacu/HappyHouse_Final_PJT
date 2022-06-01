import axios from "axios";

//카카오맵 api 카테고리별 장소 검색 url
//앱 REST API 키를 헤더에 담아 GET으로 요청
//process.env.local.VUE_APP_REST_API_KEY;
const API_URL = "https://dapi.kakao.com/v2/local/search/category.json";
const API_KEY = "05267f66ba51bde0ef5a6675fc8e1f57";
const infrastore = {
  // namespaced: true,
  state: {
    conveniences: [],
    marts: [],
    banks: [],
    restaurants: [],
    cafes: [],
    hospitals: [],
    pharmacies: [],
    traffics: [],
    schools: [],
    pschools: [],
    // allCategory: [],
    recmdhouses: [], // random... 배열 또 하나, rand => 보내야되는데 시간.. 얼마나 걸릴지..
    selrecmd: null,
  },

  getters: {
    getselrecmd(state) {
      return state.selrecmd;
    },
    getrecmdhouses(state) {
      return state.recmdhouses;
    },
    getConveniences(state) {
      return state.conveniences;
    },
    getMarts(state) {
      return state.marts;
    },
    getBanks(state) {
      return state.banks;
    },
    getRestaurants(state) {
      return state.restaurants;
    },
    getCafes(state) {
      return state.cafes;
    },
    getHospitals(state) {
      return state.hospitals;
    },
    getPharmacies(state) {
      return state.pharmacies;
    },
    getTraffics(state) {
      return state.traffics;
    },
    getSchools(state) {
      return state.schools;
    },
    getPSchools(state) {
      return state.pschools;
    },
    getEducations(state) {
      return [state.schools, state.pschools];
    },
    getAllCategory(state) {
      return [
        state.conveniences,
        state.marts,
        state.banks,
        state.restaurants,
        state.cafes,
        state.hospitals,
        state.pharmacies,
        state.traffics,
      ];
    },
  },
  mutations: {
    CONVENIENCE(state, payload) {
      state.conveniences = payload;
    },
    MART(state, payload) {
      state.marts = payload;
    },
    BANK(state, payload) {
      state.banks = payload;
    },
    RESTAURANT(state, payload) {
      state.restaurants = payload;
    },
    CAFE(state, payload) {
      state.cafes = payload;
    },
    HOSPITAL(state, payload) {
      state.hospitals = payload;
    },
    PHARMACY(state, payload) {
      state.pharmacies = payload;
    },
    TRAFFIC(state, payload) {
      state.traffics = payload;
    },
    SCHOOL(state, payload) {
      state.schools = payload;
    },
    PSCHOOL(state, payload) {
      state.pschools = payload;
    },
    RECOMMEND(state, idx) {
      state.recmdhouses[idx]++;
    },
    RESET_RECMDHOUESES(state, length) {
      // state.recmdhouses = new Array(length);
      for (var i = 0; i < length; i++) {
        state.recmdhouses[i] = 0;
      }
    },
    CLEAR_RECOMDHOUSES(state) {
      state.recmdhouses = [];
    },
    CLEAR_SELRECMD(state) {
      state.selrecmd = null;
    },
    SET_SELRECMD(state, house) {
      state.selrecmd = house;
    },
  },
  actions: {
    async FIND(store, { houses, categorycode }) {
      store.commit("CLEAR_RECOMDHOUSES");
      store.commit("RESET_RECMDHOUESES", houses.length);
      for (let i = 0; i < houses.length; i++) {
        let flag = 0;
        // console.log(houses[i].aptName, "시작 !! ", houses.length);
        for (let ci = 0; ci < categorycode.length; ci++) {
          await axios
            .get(API_URL, {
              headers: { Authorization: `KakaoAK ${API_KEY}` },
              params: {
                category_group_code: categorycode[ci],
                x: houses[i].lng,
                y: houses[i].lat,
                radius: 500,
                size: 1,
                sort: "distance",
              },
            })
            .then(({ data }) => {
              if (data.documents.length >= 1) {
                store.commit("RECOMMEND", i);
              } else {
                flag = 1;
              }
            });
          if (flag === 1) {
            break;
          }
          if (store.getters.getrecmdhouses[i] == categorycode.length) {
            store.commit("SET_SELRECMD", houses[i]);
            return;
          }
        }
      }
    },

    ALLCATEGORY(store, payload) {
      store.dispatch("CONVENIENCE", payload);
      store.dispatch("MART", payload);
      store.dispatch("BANK", payload);
      store.dispatch("RESTAURANT", payload);
      store.dispatch("CAFE", payload);
      store.dispatch("HOSPITAL", payload);
      store.dispatch("PHARMACY", payload);
      store.dispatch("TRAFFIC", payload);
      store.dispatch("SCHOOL", payload);
      store.dispatch("PSCHOOL", payload);
    },
    CONVENIENCE(store, payload) {
      return getConv(payload)
        .then((res) => {
          // console.log("[편의점]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("CONVENIENCE", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    MART(store, payload) {
      return getMart(payload)
        .then((res) => {
          // console.log("[대형마트]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("MART", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    BANK(store, payload) {
      return getBank(payload)
        .then((res) => {
          // console.log("[은행]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("BANK", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    RESTAURANT(store, payload) {
      return getRestaurant(payload)
        .then((res) => {
          // console.log("[음식점]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("RESTAURANT", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    CAFE(store, payload) {
      return getCafe(payload)
        .then((res) => {
          // console.log("[카페]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("CAFE", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    HOSPITAL(store, payload) {
      return getHospital(payload)
        .then((res) => {
          // console.log("[병원]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("HOSPITAL", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    PHARMACY(store, payload) {
      return getPharmacy(payload)
        .then((res) => {
          // console.log("[약국]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("PHARMACY", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    TRAFFIC(store, payload) {
      return getMetro(payload)
        .then((res) => {
          // console.log("[지하철]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("TRAFFIC", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    ////보여질 때 학군으로
    SCHOOL(store, payload) {
      return getSchool(payload)
        .then((res) => {
          // console.log("[학교]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("SCHOOL", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    PSCHOOL(store, payload) {
      return getPSchool(payload)
        .then((res) => {
          // console.log("[어린이집,유치원]의 수 : " + res.data.documents.length);
          // console.log(res.data.documents);
          store.commit("PSCHOOL", res.data.documents);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  modules: {},
};

/////////////////////// function - axios //////////////////////////
function getConv(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "CS2",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getMart(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "MT1",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getBank(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "BK9",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getRestaurant(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "FD6",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getCafe(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "CE7",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getHospital(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "HP8",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}
function getPharmacy(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "PM9",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getMetro(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "SW8",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getSchool(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "SC4",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}

function getPSchool(payload) {
  return axios.get(API_URL, {
    headers: { Authorization: `KakaoAK ${API_KEY}` },
    params: {
      category_group_code: "PS3",
      x: payload.x,
      y: payload.y,
      radius: payload.radius,
      size: 5,
      sort: "distance",
    },
  });
}
export default infrastore;
