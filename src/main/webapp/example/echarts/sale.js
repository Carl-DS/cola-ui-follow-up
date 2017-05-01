/**
 * Created by Carl on 2017/3/14.
 */
(function () {
    cola(function (model) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '我的工作台'
            },
            tooltip: {},
            legend: {
                data:['Cola-UI 访问来源']
            },
            xAxis: {
                data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
            },
            yAxis: {},
            series: [{
                name: 'Cola-UI 访问来源',
                type: 'bar',
                data: [335, 310, 234, 135, 1548]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    });
}).call(this);