var VOTE = "votes1";

angular.module('myApp.controllers', [])
.controller('mapCtrl', function mapCtrl($scope, d3, Counties, Parties) {

	Parties.get().$promise.then(function(parties) {
		Counties.get({'level' : 2}).$promise.then(function(counties) {
			displayWahlkreise(counties, parties);
		});
	});

	
	var displayWahlkreise = function (wkrs, parties) {

		var strokeWidth = function(scale) {
			return 0.5 + 2 / scale;
		}
		

		var displayCounty = function(e) {
			var isOkValue = function(k, r) { return k!=null && (typeof k=="string" || typeof k=="number" || typeof k=="boolean" || (!r && Object.prototype.toString.call(k) === '[object Array]' && isOkValue(k[0], true))); }

			var txt = "Properties:\n";
			for(key in e.county) { if(isOkValue(e.county[key])) { txt += key + " = " + e.county[key] + "\n"; } }
			
			txt += "\nResults:\n"
			for(var i=0; i<e.results.length; i++) {
				var name = getFromPartyIterator(
					function(party) { return party.partyId==e.results[i].partyId; },
					function(party) { return party!=null ? party.partyName : "???" });
				txt +=  name + ": " + Math.round(e.results[i].votes1) + " Erst-, " + Math.round(e.results[i].votes2) + " Zweitstimmen\n";
			}
			
			txt += "\nConstituency-Ids: "+e.constituencyIds;
			alert(txt);
			
			clicked(this, e);
		}

		
		
		
		
		var HighestVote = 0;
		for(var i=0; i<wkrs.length; i++) {
			for(var j=0; j<wkrs[i].results.length; j++) {
				if(wkrs[i].results[j][VOTE]>HighestVote) {
					HighestVote = wkrs[i].results[j][VOTE];
				}
			}
		}


		
		var getFromPartyIterator = function(fSuccess, fOut) {
			for(var p=0; p<parties.length; p++) {
				if(fSuccess(parties[p])) {
					return fOut(parties[p]);
				}
			}
			return fOut(null);
		}
		
		var getHighestResult = function(d) {
			var indHighest = 0;
			for(var r=1; r<d.results.length; r++) {
				if(d.results[r][VOTE]>d.results[indHighest][VOTE]) {
					indHighest = r;
				}
			}
			return [d.results[indHighest].partyId, d.results[indHighest][VOTE]];
		}


		/* visualization */
		var
		width = 800, height = 600,
		vis = d3.select("body").append("svg").attr("width", width).attr("height", height),
		projection = d3.geo.mercator().center([10.45, 51.16]).scale(2500).translate([width/2,height/2]);


		var drawCoordinatePoints = function(d) {
			return d.map(function(d) {
				return projection(d).join(",");
			}).join(" ");
		}
		
		var zoom = d3.behavior
			.zoom()
			.scaleExtent([1, 8])
			.on("zoom", function() {
				var scale = d3.event.scale;
				var trans = scale == 1 ? "0.0, 0.0" : d3.event.translate;
				g.attr("transform", "translate(" + trans + ") scale(" + scale + ")");
			    features.attr("stroke-width", strokeWidth(scale) + "");
			});
		
		var g = vis.append("g").call(zoom);
		g.append("rect").attr("class", "overlay").attr("width", width).attr("height", height);
		
		var features = g.selectAll("g")
			.data(wkrs).enter().append("g")
			.attr("id", function(d) { return "county-"+d.county.gid; })
			.attr("fill", function(d) {
				var pId = getHighestResult(d)[0];
				return getFromPartyIterator(
						function(party) { return party.partyId==pId; },
						function(party) {
							return party==null ? "#ffffff": party.color
						});
			})
			.attr("fill-opacity", function(d) {
				return getHighestResult(d)[1] / HighestVote;
			})
			.attr("stroke", "#333")
			.attr("stroke-width", strokeWidth(1))
			.attr("class", "county-border")
			.on("click", displayCounty)
	
			.selectAll("polygon")
				.data(function(d) { return d.county.coordinates; }).enter().append("polygon")
				.attr("points", drawCoordinatePoints);


		function clicked(node, a) {
			var elm = vis.select("#"+node.getAttribute("id"));
			elm.transition();
			var zoomFactor = 4;
			var bbox = node.getBBox(), centroid = [bbox.x + bbox.width/2, bbox.y + bbox.height/2];
			var x = centroid[0], y = centroid[1], x1 = (-x*zoomFactor + width/2), y1 = (-y*zoomFactor + height/2);
			
			g.attr("transform", "translate(" + x1 + "," + y1 + ") scale(" + zoomFactor + ")");
		    features.attr("stroke-width", strokeWidth(zoomFactor) + "");
		    
			zoom.translate([x1, y1]);
			zoom.scale(zoomFactor);
		}
	};
	
});