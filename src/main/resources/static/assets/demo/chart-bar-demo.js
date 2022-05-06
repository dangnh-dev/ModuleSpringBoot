var ctx = document.getElementById("myBarChart");

window.onload = function () {
  $.ajax({
    url: '/admin/bar-chart',
    type: 'GET',
    contentType: 'application/json',
    dataType: 'json',
    success: function (result){
      const productName = [];
      const productAmount = [];

      result.map(p => {
        productName.push(p.name);
        productAmount.push(p.amount);
      })

      drawChart(productName, productAmount);
    },
    error: function (jqXhr, textStatus, errorMessage){
      console.log(errorMessage);
    }
  })
}

function drawChart(productName, productAmount) {
  Highcharts.chart(ctx, {
    chart : {
      type : 'column',
      styledMode : true
    },
    title : {
      text : 'Product Bar Chart'
    },
    xAxis : [ {
      title : {
        text : 'Product in storage'
      },
      categories : productName
    } ],
    yAxis : [ {
      className : 'highcharts-color-0',
      // title : {
      //   text : 'Product Count'
      // }
    } ],
    series : [ {
      data : productAmount,
      name : "Product",
      colorByPoint: true,
    } ]
  });
}
