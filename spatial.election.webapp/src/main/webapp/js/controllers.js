var VOTE = "votes1";
var ELECTION_PARTIES = [];

angular.module('myApp.controllers', [])
.controller('mapCtrl', function mapCtrl($scope, d3, Counties, Parties, CountyData, Constituency) {
	

	Parties.get().$promise.then(function(parties) {
		ELECTION_PARTIES = parties;
	});
	
	
	Counties.get({'level' : 2}).$promise.then(function(counties) {
		displayWahlkreise(counties);
	});

	var showCounty = function(
			gid,			// 158
			state,			// [3, "Berlin"]
			district,		// [12, "Berlin"]
			county,			// [141, "Berlin"]
			province,		// "Berlin"
			results,		// { "SPD" : [2014, 123Erststimme, 456Zweitstimme] }
			constituencyIds	// { 58 : 12.0, 63 : 10.3, 61 : 18.0, 62 : 0.3, 59 : 0.1, ... }
	) {
		$scope.gid = gid;
		$scope.state = state;
		$scope.district = district;
		$scope.county = county;
		$scope.province = province;
		$scope.results = results;
		$scope.constituencyIds = constituencyIds;
		$scope.$apply();

		$scope.countyData = CountyData.get({ 'id' : gid });
	}
	
	
	var displayWahlkreise = function (wkrs) {
		
		var strokeWidth = function(scale) {
			return 0.5 + 2 / scale;
		}

		var displayCounty = function(e) {
			if(!doclick) return;
			var results = [];
			var constituencies = [];

			var maxVotes1 = 1, maxVotes2 = 1; 
			for(var i=0; i<e.results.length; i++) {
				if(e.results[i].votes1>maxVotes1) {
					maxVotes1 = e.results[i].votes1;
				}
				if(e.results[i].votes2>maxVotes2) {
					maxVotes1 = e.results[i].votes2;
				}
			}
			
			for(var i=0; i<e.results.length; i++) {
				var name = getFromPartyIterator(
						function(party) { return party.partyId==e.results[i].partyId; },
						function(party) { return party!=null ? party.partyName : "???" });
				var color = getFromPartyIterator(
						function(party) { return party.partyId==e.results[i].partyId; },
						function(party) { return party!=null ? party.color : "#999999" });
				results[results.length] = {
						"name" : name,
						"color" : color,
						"electionId" : e.results[i].electionId,
						"votes1" : Math.round(e.results[i].votes1),
						"votes1rel" : Math.round(10000*e.results[i].votes1/maxVotes1)/100 || "",
						"votes2" : Math.round(e.results[i].votes2),
						"votes2rel" : Math.round(10000*e.results[i].votes2/maxVotes2)/100 || ""
				};
			}

		    results.sort(function(a, b){
		        a = a.votes1 || 0;
		        b = b.votes2 || 0;
		        return b - a;
		    });
			
			for(var key in e.constituencies) {
				constituencies[constituencies.length] = {
					"const" : Constituency.get({'id' : key}),
					"value" : e.constituencies[key]
				}
			}
			
			
			showCounty(
					e.county.gid,
					[e.county.stateId, e.county.stateName],
					[e.county.districtId, e.county.districtName],
					[e.county.countyId, e.county.countyName],
					e.county.provinceName,
					results,
					constituencies
			);
			
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
			for(var p=0; p<ELECTION_PARTIES.length; p++) {
				if(fSuccess(ELECTION_PARTIES[p])) {
					return fOut(ELECTION_PARTIES[p]);
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
		width1 = 800, height1 = 600;
		
		var
		width = d3.select("#mysvg").node().offsetWidth,
		height = width * height1/width1;
		
		var
		vis = d3.select("#mysvg").append("svg:svg").attr("width", width).attr("height", height),
		projection = d3.geo.mercator().center([10.45, 51.30]).scale(2500).translate([width/2,height/2]);


		var clicked = function(node, a) {
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
		
		var drawCoordinatePoints = function(d) {
			return d.map(function(d) {
				return projection(d).join(",");
			}).join(" ");
		}
		
		var zooming = false;
		var zoom = d3.behavior
			.zoom()
			.scaleExtent([1, 8])
			.on("zoom", function() {
				if(!zooming) {
					doclick = false;
					zooming = true;
					var scale = d3.event.scale;
					var trans = scale == 1 ? "0.0, 0.0" : d3.event.translate;
					g.attr("transform", "translate(" + trans + ") scale(" + scale + ")");
				    features.attr("stroke-width", strokeWidth(scale) + "");
				    zooming = false;
				}
			});

		var g = vis.append("g").call(zoom);
		g.append("rect").attr("class", "overlay").attr("width", width).attr("height", height);
		g = g.append("g");
		
		/*
		var g = vis.append("g").call(zoom);
		g.append("rect").attr("class", "overlay").attr("width", width).attr("height", height);
*/


		var doclick = false;
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
			.on("mousedown", function() { doclick = true; })
	
			.selectAll("polygon")
				.data(function(d) { 
					return d.county.coordinates; }).enter().append("polygon")
				.attr("points", drawCoordinatePoints);

	};
	
});