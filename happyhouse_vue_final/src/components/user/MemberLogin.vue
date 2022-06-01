<template>
  <b-container class="bv-example-row mt-3">
    <br /><br /><br /><br />
    <h3 class="underline-steelblue">
      <img
        src="https://img.icons8.com/color/48/000000/blocked-account-male.png"
      />
      로그인 &nbsp;
    </h3>
    <br /><br />
    <b-row>
      <b-col></b-col>
      <b-col cols="6">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >로그인</b-button
            >
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "MemberLogin",
  data() {
    return {
      isLoginError: false,
      user: {
        userid: "",
        userpwd: "",
      },
    };
  },
  methods: {
    ...mapActions(["setLoginUser"]),
    confirm() {
      let err = true;
      let msg = "";
      !this.user.userid &&
        ((msg = "아이디를 입력해주세요"), (err = false), this.$refs.id.focus());
      err &&
        !this.user.userpwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.focus());
      if (!err) alert(msg);
      else this.setLoginUser(this.user);
    },

    movePage() {
      this.$router.push({ name: "signUp" });
    },
  },
};
</script>

<style>
.alert {
  background-color: #e9d3b9 !important;
  border: none !important;
  color: black !important;
}
/* .my-box {
  width: 500px;
  height: 550px;
  background: #f1f1f1;
  backdrop-filter: blur(4px);
  box-shadow: 0px 4px 30px rgba(0, 0, 0, 0.25);
}
.logincss {
  background: #414143;
  border: none;
}
.registercss {
  background: #686871;
  border: none;
} */
</style>
