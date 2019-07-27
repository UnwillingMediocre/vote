<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>投票分析</title>
    <script src="static/js/jquery-3.3.1.js"></script>
</head>
<body style="height: 100%; margin: 0">
<div id="container" style="height: 500px;"></div>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
<script type="text/javascript"
        src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    app.title = '投票结果-条形图';

    var vote_id = '${param.vote_id}';
    console.log(vote_id);
    var yAxis_data = [];
    var series_data = [];

    $.ajax({
        type: "GET",
        url: "/vote_war/vote?method=voteAnalysis&time=" + new Date().getTime(),
        dataType: "json",
        data: {
            vote_id: vote_id
        },
        success: function (result) {
            console.log(result);
            var options = result.optionInfoVoList;
            $.each(options, function (index, item) {
                yAxis_data.push(item.content);
                series_data.push(item.count);
            });
            myChart.setOption({
                title: {
                    text: result.title,
                    left: '6%',
                    textStyle: {
                        //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                        fontWeight: 'bolder',
                        //字体系列
                        fontFamily: 'sans-serif',
                        //字体大小
                        fontSize: 25
                    }
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['票数'],
                    textStyle: {
                        fontSize: 16,
                    }
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: yAxis_data

                },
                yAxis: {
                    type: 'value',
                },
                series: [
                    {
                        name: ['票数'],
                        type: 'line',
                        itemStyle: {
                            normal: {
                                color: '#9bca63',
                                label: {
                                    show: true
                                },
                                lineStyle: {
                                    color: '#27727b',
                                    fontSize:20
                                }
                            }
                        },
                        data: series_data
                    }
                ]
            });
        }
    });
    console.log(yAxis_data);
    console.log(series_data);
</script>
</body>
</html>