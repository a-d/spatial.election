

function mapCtrl($scope, d3, Counties) {
		
	Counties.get({'level' : 2}).$promise.then(function (wkrs) {

		/* visualization */
		var
		width = 800,
		height = 600,
		vis = d3.select("body").append("svg").attr("width", width).attr("height", height),
		allBaseLines = [],
		color = d3.scale.category10(),
		
		/*
		 * constituencies:
		scaleX = d3.scale.linear().domain([ 200000, 1000000 ]).range([ 0, width ]),
		scaleY = d3.scale.linear().domain([ 5203000, 6159000 ]).range([ height, 0 ]),
		*/
		
		/*
		 * counties:
		 */
		scaleX = d3.scale.linear().domain([ 5, 17]).range([ 0, width ]),
		scaleY = d3.scale.linear().domain([ 47, 56]).range([ height, 0 ]);

		
		vis.selectAll("g")
			.data(wkrs).enter().append("g")
			.attr("id", function(d) { return d.county.gid; })
			.attr("fill", function(d) {
				return "black"; //color(d.wkr_name)
			})
			.attr("stroke", "#333")
			.attr("stroke-width", 2)
			
			.on("click", (function(e) {

				var isOkValue = function(k) {
					return k!=null && (typeof k=="string" || typeof k=="number" || typeof k=="boolean" || (Object.prototype.toString.call(k) === '[object Array]' && isOkValue(k[0])));
				}
				
				var txt = "Properties:\n";
				for(key in e.county) {
					if(isOkValue(e.county[key])) {
						txt += key + " = " + e.county[key] + "\n";
					}
				}
				
				txt += "\nResults:\n"
				for(key in e.results) {
					if(isOkValue(e.results[key]) {
						txt += key + " = " + e.results[key] + "\n";
					}
				}
				
				txt += "\nConstituency-Ids: "+e.constituencyIds;
			
				alert(txt);
			}))

			.selectAll("polygon")
				.data(function(d) { return d.county.coordinates; }).enter().append("polygon")
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