package parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ElectionResultParser extends SimpleParser{
	
	private String partiesOutput = "/home/martin/Desktop/parties.csv";

	/**
	 * inputFile format: ConstituencyId; firstVoteCurrent; firstVoteLastYear; secondVoteCurrent; secondVoteLastYear 
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
				for (int i = 1; i < nextLine.length; i += 4) { // for now zweitstimmen are discarded

					String[] temp = new String[4];

					temp[0] = constituencyId;
					temp[1] = String.valueOf((i -1)/4);

					if (!nextLine[i].equals("")) {
						temp[2] = nextLine[i];	    				
					} else {
						temp[2] = String.valueOf(0);
					}
					temp[3] = String.valueOf(2013); // extract parameter later
					
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


		System.out.println(result.size());
		return result;
	}
}
