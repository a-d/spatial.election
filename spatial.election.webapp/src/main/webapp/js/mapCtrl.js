var VOTE = "votes1"; 

function mapCtrl($scope, d3, Counties, Parties) {

	Parties.get().$promise.then(function(parties) {
		Counties.get({'level' : 2}).$promise.then(function(counties) {
			displayWahlkreise(counties, parties);
		});
	});

	
	var displayWahlkreise = function (wkrs, parties) {
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
				var pId = getHighestResult(d)[0];
				window.x1 = pId;
				return getFromPartyIterator(
						function(party) { return party.partyId==pId; },
						function(party) {
							if(party==null)
							return "#ffffff"
							return party.color
						});
			})
			.attr("fill-opacity", function(d) {
				return getHighestResult(d)[1] / HighestVote;
			})
			.attr("stroke", "#333")
			.attr("stroke-width", 2)
			
			.on("click", (function(e) {

				var isOkValue = function(k, r) {
					return k!=null && (typeof k=="string" || typeof k=="number" || typeof k=="boolean" || (!r && Object.prototype.toString.call(k) === '[object Array]' && isOkValue(k[0], true)));
				}
				
				var txt = "Properties:\n";
				for(key in e.county) {
					if(isOkValue(e.county[key])) {
						txt += key + " = " + e.county[key] + "\n";
					}
				}
				
				txt += "\nResults:\n"
				for(var i=0; i<e.results.length; i++) {
					var name = getFromPartyIterator(
						function(party) { return party.partyId==e.results[i].partyId; },
						function(party) { return party!=null ? party.partyName : "???" });
					
					e.results[i].votes1;
					txt +=  name + ": " + Math.round(e.results[i].votes1) + " Erst-, " + Math.round(e.results[i].votes2) + " Zweitstimmen\n";
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
	};
	
}





