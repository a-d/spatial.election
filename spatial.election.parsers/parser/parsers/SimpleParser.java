package parsers;

import java.util.List;

public abstract class SimpleParser {

	private String inputFileName;
	private String outputFileName;
	
	public SimpleParser(String fileName, String outputFileName) {
		this.inputFileName = fileName;
		this.outputFileName = outputFileName;
	}
	
	abstract List<? extends Object> parse();

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
}
