<template>
  <b-container class="bv-example-row mt-3">
    <br /><br /><br /><br />
    <h3 class="underline-steelblue">
      <img src="https://img.icons8.com/color/48/000000/add-user-male--v1.png" />
      회원 가입 &nbsp;
    </h3>
    <br /><br />
    <b-row>
      <b-col></b-col>
      <b-col cols="6">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left" @submit="onSubmit">
            <b-form-group label="아이디:" label-for="id">
              <b-form-input
                id="id"
                v-model="user.id"
                required
                placeholder="아이디 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="password">
              <b-form-input
                type="password"
                id="password"
                v-model="user.password"
                required
                placeholder="비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                id="name"
                v-model="user.name"
                required
                placeholder="이름 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="나이:" label-for="age">
              <b-form-input
                id="age"
                v-model="user.age"
                required
                placeholder="나이 입력...."
              ></b-form-input>
            </b-form-group>
            <b-button type="submit" block variant="success" class="m-1"
              >가입하기</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";

export default {
  name: "MemberRegister",
  data() {
    return {
      //   isLoginError: false,
      user: {
        id: "",
        password: "",
        name: "",
        email: "",
        age: "",
      },
    };
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.user.id &&
        ((msg = "아이디를 입력해주세요"), (err = false), this.$refs.id.focus());
      err &&
        !this.user.password &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.password.focus());
      err &&
        !this.user.name &&
        ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
      err &&
        !this.user.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.focus());
      err &&
        !this.user.age &&
        ((msg = "나이를 입력해주세요"), (err = false), this.$refs.age.focus());

      if (!err) alert(msg);
      else this.registUser();
    },
    registUser() {
      http
        .post(`/user`, {
          id: this.user.id,
          password: this.user.password,
          name: this.user.name,
          email: this.user.email,
          age: this.user.age,
        })
        .then(() => {
          let msg = "회원가입 성공";
          alert(msg);

          //회원가입 후 로그인 페이지로
          this.$router.push({ name: "signIn" });
        })
        .catch(() => {
          let msg = "회원가입 처리 시 문제 발생";
          alert(msg);
        });
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
</style>
