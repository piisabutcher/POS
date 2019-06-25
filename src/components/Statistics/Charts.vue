<template>
  <div class="container"></div>
</template>

<script>
  import Highcharts from 'highcharts/highstock';
  import HighChartsMore from 'highcharts/highcharts-more';
  import HighChartsDrilldown from 'highcharts/modules/drilldown';
  import HighCharts3D from 'highcharts/highcharts-3d';
  import Highmaps from 'highcharts/modules/map';

  HighChartsMore(Highcharts);
  HighChartsDrilldown(Highcharts);
  HighCharts3D(Highcharts);
  Highmaps(Highcharts);
  export default {
    props: {
      options:{
        type: Object,
        default: function () {

        }
      },
      styles:{
        type: String
      }
    },
    name: 'Charts',
    data() {
      return {
        chart: null
      }
    },
    mounted() {
      this.initChart();
    },
    methods: {
      initChart() {
        this.$el.style.width = '100%';
        this.$el.style.height = '100%';
        this.chart = new Highcharts.Chart(this.$el, this.options);
      }

    },
    watch:{
      options: function (val) {
        new Highcharts.Chart(this.$el, val);
      }

    }
  }

</script>

<style scoped lang="scss">
  .container{
    width: 100%;
    height: calc(100% - 20px);
    vertical-align: top;
  }
</style>
