<template>
  <div>
    <b-button class="chatBtn" :pressed.sync="toggle" variant="primary"
      ><b-icon icon="messenger"></b-icon
    ></b-button>
    <div v-if="toggle">
      <b-card class="chat-box">
        <b-card class="chat-card" ref="recvList">
          <b-card-text>클린한 채팅유저가 됩시다.</b-card-text>
          <div id="listout" v-for="(item, idx) in recvList" :key="idx">
            <div id="user-name">{{ item.userName }}</div>
            <span id="chats">
              {{ item.content }}
            </span>
            <span id="time">{{ new Date() | formatTime }}</span>
          </div>
        </b-card>
        <b-form-input
          v-model="message"
          type="text"
          placeholder="입력하세요..."
          @keyup="sendMessage"
        ></b-form-input>
      </b-card>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

import moment from "moment";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      toggle: false,
      userName: "익명",
      message: "",
      recvList: [],
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect();
  },
  methods: {
    sendMessage(e) {
      if (e.keyCode === 13 && this.userName !== "" && this.message !== "") {
        this.send();
        this.message = "";
      }
    },
    send() {
      // console.log("Send message:" + this.message);

      // 로그인 / 로그아웃 시 보내는 사람 변경
      if (this.getLoginUser != null) {
        this.userName = this.getLoginUser.id;
      } else {
        this.userName = "익명";
      }

      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userName,
          content: this.message,
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = "http://localhost:80";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          // console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", (res) => {
            // console.log("구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
  computed: {
    ...mapGetters(["getLoginUser"]),
  },
  watch: {
    recvList() {
      this.$nextTick(() => {
        window.scrollTo(200, 0);
        let messages = this.$refs.recvList;

        messages.scrollTo({
          top: messages.scrollHeight,
          behavior: "smooth",
        });
      });
    },
  },
  filters: {
    formatTime(regtime) {
      return moment(new Date(regtime)).format("HH:mm");
    },
  },
};
</script>

<style scoped>
@media (max-height: 1023px) {
  .chatBtn {
    top: 160%;
  }
  .chat-box {
    top: 610px;
  }
}
@media (max-height: 900px) {
  .chatBtn {
    top: 130%;
  }
  .chat-box {
    top: 460px;
  }
}
@media (max-height: 768px) {
  .chatBtn {
    top: 100%;
  }
  .chat-box {
    top: 310px;
  }
}
@media (max-height: 600px) {
  .chatBtn {
    top: 80%;
  }
  .chat-box {
    top: 210px;
  }
}

.chatBtn {
  left: 5%;
  /* background-color: #04aa6d; */
  border: none;
  /* color: white; */
  padding: 20px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
  margin: 4px 2px;
  border-radius: 50%;
  position: absolute;
}
.chat-box {
  left: 160px;
  width: 300px !important;
  height: 250px !important;
  border: 1px solid;
  font-size: 16px;
  margin: 4px 2px;
  position: fixed;
}
.chat-card {
  width: 100%;
  height: 200px;
  overflow-y: scroll;
}
.card-body {
  padding: 5px;
}
#listout {
  margin-top: 10px;
}
#user-name {
  margin-bottom: 5px;
}
#chats {
  font-size: 12px;
  background-color: #f0f0f0;
  border: 1px;
  border-radius: 5px;
  padding: 5px;
}
#time {
  font-size: 5px;
  margin-left: 4px;
}
</style>
