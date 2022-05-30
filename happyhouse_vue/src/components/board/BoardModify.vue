<template>
  <b-container class="bv-example-row mt-3">
    <board-input-item type="modify" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/board/item/BoardInputItem.vue";

export default {
  name: "BoardModify",
  components: {
    BoardInputItem,
  },
};
</script>

<style>
.alert.alert-info {
  background-color: #e0c8c4 !important;
  border: none !important;
  color: black !important;
}
</style>
