<template>
  <b-container class="bv-example-row mt-3">
    <br /><br /><br /><br />
    <h3 class="underline-steelblue">
      <img
        src="https://img.icons8.com/color/48/000000/edit-user-male--v1.png"
      />
      회원정보 수정 &nbsp;
    </h3>
    <b-row align-content="center">
      <b-col></b-col>
      <b-col cols="6">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left" @submit="onUpdate">
            <b-form-group label="아이디:" label-for="id">
              <b-form-input
                id="id"
                v-model="getLoginUser.id"
                disabled
                placeholder="아이디 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="password">
              <b-form-input
                type="password"
                id="password"
                v-model="getLoginUser.password"
                required
                placeholder="비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                id="name"
                v-model="getLoginUser.name"
                required
                placeholder="이름 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="getLoginUser.email"
                required
                placeholder="이메일 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="나이:" label-for="age">
              <b-form-input
                id="age"
                v-model="getLoginUser.age"
                required
                placeholder="나이 입력...."
              ></b-form-input>
            </b-form-group>
            <b-button
              type="submit"
              variant="success"
              class="m-1"
              style="width: 48%"
              >수정</b-button
            >
            <b-button
              @click="deleteUser"
              variant="danger"
              class="m-1"
              style="width: 48%"
              >탈퇴</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";

export default {
  name: "MemberRegister",
  methods: {
    ...mapActions(["setInfoUser", "clearInfoUser"]),
    onUpdate(event) {
      event.preventDefault();

      let err = true;
      let msg = "";

      //아이디는 못 바꿔
      !this.getLoginUser.password &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.password.focus());
      err &&
        !this.getLoginUser.name &&
        ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
      err &&
        !this.getLoginUser.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.focus());
      err &&
        !this.getLoginUser.age &&
        ((msg = "나이를 입력해주세요"), (err = false), this.$refs.age.focus());

      if (!err) alert(msg);
      else {
        alert("회원정보 수정이 완료되었습니다.");
        this.setInfoUser(this.getLoginUser);
        this.$router.push({ name: "house" });
      }
    },
    deleteUser() {
      this.clearInfoUser(this.getLoginUser);
    },
  },
  computed: {
    ...mapGetters(["getLoginUser"]),
  },
};
</script>

<style>
.alert {
  background-color: #e9d3b9 !important;
  border: none !important;
  color: black !important;
}
</style>
