$.ajax({
	url: "chartstest",	
	success: function(result){
		var formatteddata = [];
		for(var key in result){
			var singleObject = {
					name: '',
					data: []
				}
			singleObject.name = key.toUpperCase();
			for(var i = 0; i < result[key].length; i++){
				singleObject.data.push(parseInt(result[key][i].subscribers));
			}
			formatteddata.push(singleObject);
		}
		
		drawMultipleLineChart(formatteddata);
	}
});

function drawMultipleLineChart(formatteddata){
	Highcharts.chart('container', {

	    title: {
	        text: 'Solar Employment Growth by Sector, 2010-2019'
	    },

	    subtitle: {
	        text: 'Source: thesolarfoundation.com'
	    },

	    yAxis: {
	        title: {
	            text: 'Number of Employees'
	        }
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle'
	    },

	    plotOptions: {
	        series: {
	            label: {
	                connectorAllowed: false
	            },
	            pointStart: 2011
	        }
	    },

	    series: formatteddata,

	    responsive: {
	        rules: [{
	            condition: {
	                maxWidth: 500
	            },
	            chartOptions: {
	                legend: {
	                    layout: 'horizontal',
	                    align: 'center',
	                    verticalAlign: 'bottom'
	                }
	            }
	        }]
	    }

	});
}