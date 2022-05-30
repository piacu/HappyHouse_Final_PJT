<template>
  <b-container>
    <b-table
      sticky-header
      :items="getHouseDeal"
      :fields="fields"
      caption-top
      head-variant="light"
      id="dealtable"
    >
      <template #cell(dealDate)="data">
        {{ data.item.dealYear.substr(2, 4) }} . {{ data.item.dealMonth }}.
        {{ data.item.dealDay }}
      </template>

      <template #cell(floor)="data"> {{ data.item.floor }}층 </template>
      <template #table-caption>전체 거래 내역 조회</template>
    </b-table>
  </b-container>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      fields: [
        { key: "dealDate", label: "계약일" },
        { key: "dealAmount", label: "거래가격" },
        { key: "area", label: "면적" },
        { key: "floor", label: "층" },
      ],
      items: null,
    };
  },
  computed: {
    ...mapGetters(["getHouseDeal"]),
  },
};
</script>

<style>
#dealtable th {
  background-color: #e1dcd9;
  border-top: 2px solid #e1dcd9;
  border-bottom: 2px solid #e1dcd9;
}
#dealtable td {
  /* padding: 0.75rem !important; */
  /* vertical-align: top; */
  border-top: 2px solid #e1dcd9;
  border-bottom: 2px solid #e1dcd9;
}
</style>
