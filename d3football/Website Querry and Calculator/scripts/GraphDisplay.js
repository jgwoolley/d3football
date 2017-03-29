
var footballData;
//open the football data file to use for the table that we are generating.
d3.csv("../data/FootballDataDumps.csv", function(footballData) {
	//put the output in a variable called footballData
	this.footballData = footballData;

	//call the createChar method once we have opened the file.
	createChart();
});

function createChart() {
	// Load the Visualization API and the corechart package.
	google.charts.load('current', {
		'packages' : [ 'corechart', 'table' ]
	}, 'visualization');

	// Set a callback to run when the Google Visualization API is loaded.
	google.charts.setOnLoadCallback(drawChart);

	// Callback that creates and populates a data table,
	// instantiates the pie chart, passes in the data and
	// draws it.
	var table;

	function drawChart() {
		//class names for specific functions of the table
		var cssClassNames = {
			'headerRow' : '',
			'tableRow' : '',
			'oddTableRow' : '',
			'selectedTableRow' : '',
			'hoverTableRow' : 'hover',
			'headerCell' : 'small-cell',
			'tableCell' : '',
			'rowNumberCell' : ''
		};

		// Create the data table.
		var data = new google.visualization.DataTable();
		//options for the graph. We are displaying row numbers.
		var options = {
			'showRowNumber' : true,
			'allowHtml' : true,
			'cssClassNames' : cssClassNames,
			width : '100%',
			height : '100%'
		};

		//loop through the first row of the football data and add the yard lines to the table.
		for (var i = 0; i < footballData.length; i++) {
			data.addColumn('string', ("00" + footballData[i]["Yard Line"]).slice(-2));
		}

		//add 15 rows for the 4th down distances. 
		data.addRows(15);
		var i = 0;
		//loop through the columns of the csv
		footballData.forEach(function(row) {
			//loop through the rows of the csv
			for (var j = 1; j <= 15; j++) {
				//get the values for go for it, field goal, and punt
				var goForIt = row["4th & " + j + ": GFI"];
				var fieldGoal = row["4th & " + j + ": FG"];
				var punt = row["4th & " + j + ": P"];

				//find the biggest value of the three
				var max = Math.max(goForIt, fieldGoal, punt);
				var cellVal = 0;

				//set the cellVal to either 0, 1, or 2. We will use these values to color the
				//cell green, red, or blue respectively. 
				if (max == goForIt) {
					cellVal = 0;
				} else if (max == fieldGoal) {
					cellVal = 1;
				} else if (max == punt) {
					cellVal = 2;
				}

				//set the cell at the location, cellVal and apply no text to it.
				data.setCell(j - 1, i, cellVal + '', '');
			}
			i++;
		});

		//format the cells which have the value 0 to green, 1 to red, and 2 to blue.
		var formatter = new google.visualization.ColorFormat();
		formatter.addRange(0, 1, 'black', 'green');
		formatter.addRange(1, 2, 'black', 'red');
		formatter.addRange(2, 3, 'black', 'blue');

		//apply the format to all the cells.
		for (var i = 0; i < footballData.length; i++)
			formatter.format(data, i);

		//instantiate and draw our chart, passing in some options.
		//display the output in chart_div 
		var chart = new google.visualization.Table(document.getElementById('chart_div'));
		chart.draw(data, options);

		//Go back through the row numbers and put 4th & before the number. 
		$("td.google-visualization-table-td.google-visualization-table-seq").each(function(e) {
			$(this).text("4th & " + $(this).text());
		});

		//Mouse over table cell function.
		$("td.google-visualization-table-td").mouseover(function() {
			//change the color of the cell to yellow to see when it is moused over.
			$(this).addClass('hover');

			//calculate the row and column of this cell.
			var col = $(this).parent().children().index($(this));
			var row = $(this).parent().parent().children().index($(this).parent());

			if (col > 0) {
				//get the three values of go for it, field goal, and punt
				var goForItVal = footballData[col - 1]["4th & " + (row + 1) + ": GFI"];
				var fieldGoalVal = footballData[col - 1]["4th & " + (row + 1) + ": FG"];
				var puntVal = footballData[col - 1]["4th & " + (row + 1) + ": P"];
			}

			//set the title of this cell to give a description of the data. 
			//it will appear when hovered over.
			$(this).attr('title', 'Expected Points for... Go For It: ' + goForItVal + ', Field Goal: ' 
					+ fieldGoalVal + ', Punt: ' + puntVal 
					+ ', On 4th & ' + (row + 1)
					+ ', At the  ' + col + ' yard line.');

			//console.log('Row: ' + row + ', Column: ' + col);
			//console.log('GFI: ' + goForItVal + ' FG: ' + fieldGoalVal + ' P: ' + puntVal);
		});

		//when the mouse leaves we must remove the hove class so that it changes back to its
		//original color.
		$("td.google-visualization-table-td").mouseleave(function() {
			$(this).removeClass('hover');
		});

		//This will display the title text that we set above when the cell is hovered over.
		$('td.google-visualization-table-td').hover(function() {
			//Hover over code
			var title = $(this).attr('title');
			$(this).data('tipText', title).removeAttr('title');
			$('<p class="tooltip"></p>').text(title).appendTo('body').fadeIn('slow');
		}, function() {
			//Hover out code
			$(this).attr('title', $(this).data('tipText'));
			$('.tooltip').remove();
		}).mousemove(function(e) {
			var mousex;
			if (e.pageX + 100 > $(window).width()) {
				mousex = e.pageX - 100;
			} else {
				mousex = e.pageX + 20;
			}
			 //Get X coordinates
			var mousey = e.pageY + 10; //Get Y coordinates
			$('.tooltip').css({
				top : mousey,
				left : mousex
			})
		});
		
	}
}