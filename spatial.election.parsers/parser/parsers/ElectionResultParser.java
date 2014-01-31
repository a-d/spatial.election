package parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ElectionResultParser extends AbstractParser{

	private String partiesOutput = "/home/martin/Desktop/parties.csv";
	private static final int CONSTITUENCY_ID = 0;
	private static final int PARTY_ID = 1;
	private static final int ELECTION_ID = 2;
	private static final int FIRST_VOTE = 3;
	private static final int SECOND_VOTE = 4;

	/**
	 * inputFile format: ConstituencyId; [firstVoteCurrent; firstVoteLastYear; secondVoteCurrent; secondVoteLastYear]*
	 * 
	 */
	public ElectionResultParser(String inputFileName, String outputFileName) {
		super(inputFileName, outputFileName);
	}

	@Override
	public List<String[]> parse() {

		List<String[]> result = new LinkedList<String[]>();
		List<String[]> parties = new LinkedList<String[]>();

		String [] header;
		String [] nextLine;

		CSVReader reader;

		try {
			reader = new CSVReader(new FileReader(getInputFileName()), ';');

			header = reader.readNext();
			// parse parties and dump to csv
			for (int j = 1; j < header.length; j += 4) {
				int id = parties.size();
				parties.add(new String[] {String.valueOf(id), header[j]});
			}

			CSVWriter writer = new CSVWriter(new FileWriter(partiesOutput), ',');
			writer.writeAll(parties);
			writer.close();

			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				String constituencyId = nextLine[0];
				for (int i = 1; i < nextLine.length; i += 4) {

					String[] temp = new String[5];

					temp[CONSTITUENCY_ID] = constituencyId;
					temp[PARTY_ID] = String.valueOf((i -1)/4); // partyId
					temp[ELECTION_ID] = String.valueOf(1); 

					if (!nextLine[i].equals("")) { // erststimme
						temp[FIRST_VOTE] = nextLine[i];	    				
					} else {
						temp[FIRST_VOTE] = String.valueOf(0);
					}

					if (!nextLine[i+2].equals("")) { // zweitstimme
						temp[SECOND_VOTE] = nextLine[i+1];	    				
					} else {
						temp[SECOND_VOTE] = String.valueOf(0);
					}

					result.add(temp);
				}
			}

			reader.close();

			CSVWriter writer2 = new CSVWriter(new FileWriter(getOutputFileName()), ',');
			writer2.writeAll(result);
			writer2.close();

		} catch (FileNotFoundException fileNotFound) {
			fileNotFound.printStackTrace();
		}  catch (IOException io) {
			io.printStackTrace();
		}

		return result;
	}
}
