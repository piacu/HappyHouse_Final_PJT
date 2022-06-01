import axios from "@/api/http";

const housestore = {
  namespace: true,
  state: {
    houses: [],
    house: null,
    housedeal: [],
    x: 37.5666805,
    y: 126.9784147,
    dealTypeCondition: 0,
    moneyCondition: [{ 0: { 0: 0, 1: 9999 } }],
    infraCondition: ["ALL"],
    isSidebarOpen: false,
    isRmdVisible: "display:none",
    isInterVisible: false,
    isRecmdMarker: false,
    sel: null,
  },
  getters: {
    getHouses(state) {
      return state.houses;
    },
    getHouse(state) {
      return state.house;
    },
    getHouseDeal(state) {
      return state.housedeal;
    },
    getX(state) {
      return state.x;
    },
    getY(state) {
      return state.y;
    },
    getDealType(state) {
      return state.dealTypeCondition;
    },
    getMoneyCondition(state) {
      return state.moneyCondition;
    },
    getInfraCondition(state) {
      return state.infraCondition;
    },
    getIsSidebarOpen(state) {
      return state.isSidebarOpen;
    },
    getIsRmdVisible(state) {
      return state.isRmdVisible;
    },
    getIsInterVisible(state) {
      return state.isInterVisible;
    },
    getIsRecmdMarker(state) {
      return state.isRecmdMarker;
    },
    getSel(state) {
      return state.sel;
    },
  },
  mutations: {
    SET_ISRECMDMARKER(state, boolean) {
      state.isRecmdMarker = boolean;
    },
    SET_ISINTERVISIBLE(state, boolean) {
      state.isInterVisible = boolean;
    },
    SET_SIDEBAR(state, boolean) {
      state.isSidebarOpen = boolean;
    },
    RESET_XY(state) {
      state.x = 37.5666805;
      state.y = 126.9784147;
    },
    SET_XY(state, data) {
      state.x = data.lat;
      state.y = data.lng;
    },
    CLEAR_HOUSES_LIST(state) {
      state.house = [];
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },
    SET_HOUSEDEAL_LIST(state, deals) {
      state.housedeal = deals;
    },
    CLEAR_DEALTYPECONDITION(state) {
      state.dealTypeCondition = 0;
    },
    CLEAR_MONEYCONDITION(state) {
      state.moneyCondition = [{ 0: { 0: 0, 1: 9999 } }];
    },
    CLEAR_INFRACONDITION_LIST(state) {
      state.infraCondition = ["ALL"];
    },
    SET_DEALTYPECONDITION(state, list) {
      state.dealTypeCondition = list;
    },
    SET_MONEYCONDITION(state, list) {
      state.moneyCondition = list;
    },
    SET_INFRACONDITION_LIST(state, list) {
      state.infraCondition = list;
    },
    SET_ISRMDVISIBLE(state) {
      state.isRmdVisible = "";
    },
    SET_ISNOTRMDVISIBLE(state) {
      state.isRmdVisible = "display:none";
    },
    SET_X(state, value) {
      state.x = value;
    },
    SET_Y(state, value) {
      state.y = value;
    },
    SET_SEL(state, value) {
      state.sel = value;
    },
  },
  actions: {
    async getHouseList({ commit, state }, latlng) {
      latlng.neLatlng = latlng.neLatlng
        .replace(" ", "")
        .replace(")", "")
        .replace("(", "");
      latlng.swLatlng = latlng.swLatlng
        .replace(" ", "")
        .replace(")", "")
        .replace("(", "");
      const params = {
        lat1: latlng.swLatlng.split(",")[0],
        lng1: latlng.swLatlng.split(",")[1],
        lat2: latlng.neLatlng.split(",")[0],
        lng2: latlng.neLatlng.split(",")[1],
        min: state.moneyCondition[0][state.dealTypeCondition][0] * 10000,
        max: state.moneyCondition[0][state.dealTypeCondition][1] * 10000,
      };
      await axios
        .get(`/map/apt`, { params })
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseDealList({ commit }, aptCode) {
      const params = {
        aptCode: aptCode,
      };
      axios
        .get(`/map/aptdeal`, { params })
        .then(({ data }) => {
          commit("SET_HOUSEDEAL_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    detailHouse({ commit }, house) {
      commit("SET_DETAIL_HOUSE", house);
    },
    setDealTypeCondition({ commit }, payload) {
      commit("SET_DEALTYPECONDITION", payload);
    },
    setMoneyCondition({ commit }, payload) {
      commit("SET_MONEYCONDITION", payload);
    },
    setInfraConditionList({ commit }, list) {
      commit("SET_INFRACONDITION_LIST", list);
    },
  },
  modules: {},
};

export default housestore;
