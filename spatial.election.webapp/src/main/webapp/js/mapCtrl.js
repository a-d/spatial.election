

function mapCtrl($scope, d3, Constituencies) {
	
	Constituencies.get({'level' : 2}).$promise.then(function (wkrs) {
		
		/* visualization */
		var
		width = 800,
		height = 600,
		vis = d3.select("body").append("svg").attr("width", width).attr("height", height),
		allBaseLines = [],
		scaleX = d3.scale.linear().domain([ 200000, 1000000 ]).range([ 0, width ]),
		scaleY = d3.scale.linear().domain([ 5203000, 6159000 ]).range([ height, 0 ]),
		color = d3.scale.category10();

		window.vis = vis;
		window.wkrs = wkrs;
		
		vis.selectAll("g")
			.data(wkrs).enter().append("g")
			.attr("id", function(d) { return d.gid; })
			.attr("fill", function(d) {
				return "black"; //color(d.wkr_name)
			})
			.attr("stroke", "#333")
			.attr("stroke-width", 2)
			
			.on("click", (function(e) {
				var txt = "";
				var keys = ["gid", "land_name", "land_nr", "wkr_name", "wkr_nr"]
				for(i=0; i<keys.length; i++) txt += keys[i] + " = " + e[keys[i]] + "\n";
				alert(txt);
			}))

			.selectAll("polygon")
				.data(function(d) { return d.coordinates; }).enter().append("polygon")
				.attr("points",
					function(d) {
						return d.map(function(d) {
							return [ scaleX(d[0]), scaleY(d[1]) ].join(",");
						}).join(" ");
					});

/*
		// add legend   
		var legend = vis.append("g")
			.attr("class", "legend")
			.attr("height", 100)
			.attr("width", 100)
			.attr('transform', 'translate(10,20)');

		legend.selectAll('rect').data(wkrs).enter().append("rect")
			.attr("x", 40).attr("y", function(d, i) {
				return i * 20;
			})
			.attr("width", 10)
			.attr("height", 10)
			.style("fill",
				function(d) {
					return color(d.wkr_name);
				});

		legend.selectAll('text').data(wkrs).enter().append("text")
			.attr("x", 55)
			.attr("y", function(d, i) {
				return i * 20 + 10;
			})
			.text(function(d) {
				return d.wkr_name + " (#" + d.wkr_nr + ")";
			});
*/
	});

}