package edu.spatial.election.database.dao;

import java.util.List;

import edu.spatial.election.database.exceptions.PartyNotFoundException;
import edu.spatial.election.domain.Party;

public interface PartyDAO extends SimpleDAO {

	public Party findPartyById(long id) throws PartyNotFoundException;
	public List<Party> getParties();
	public void saveParty(Party party);
	public void deleteParty(long id) throws PartyNotFoundException;
}
