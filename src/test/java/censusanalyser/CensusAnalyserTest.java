package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/Census.txt";
    private static final String WRONG_CSV_FILE_DELIMITER= "./src/test/resources/CensusDelimeter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_FILE_TYPE = "./src/test/resources/StateCode.txt";
    private static final String WRONG_STATE_FILE_DELIMITER = "./src/test/resources/IndiaStateInvalidDelimeter.csv";
    private static final String WRONG_STATE_FILE_HEADER = "./src/test/resources/IndiaStateInvalidHeader.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(INDIA_CENSUS_CSV_FILE_PATH,IndiaCensusCSV.class);
            int numOfRecords = censusAnalyser.loadIndiaCensusData();
            Assert.assertEquals(28,numOfRecords);
        } catch (CsvFileBuilderException e) {

        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_CSV_FILE_PATH,IndiaCensusCSV.class);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_CSV_FILE_TYPE,IndiaCensusCSV.class);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CsvFileBuilderException.class);
            censusAnalyser.loadIndiaCensusData();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_CSV_FILE_DELIMITER,IndiaCensusCSV.class);
            censusAnalyser.loadIndiaCensusData();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.WRONG_FILE_DELIMITER, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenIncorrectHeader_ThenTrue() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_CSV_FILE_HEADER,IndiaCensusCSV.class);
            censusAnalyser.loadIndiaCensusData();
        }catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.WRONG_FILE_HEADER,e.type);
        }
    }
    //TEST FOR INDIA STATE CODE


    @Test
    public void givenIndiaStateCodeCSVFile_ShouldReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(INDIA_STATE_CODE_FILE_PATH,IndiaStateCode.class);
            int numOfRecords = censusAnalyser.loadStateCode();
            Assert.assertEquals(37, numOfRecords);
        } catch (CsvFileBuilderException e) {}
    }
    @Test
    public void givenIndiaStateCodeCSV_WhenWrongPath_ShouldReturnException() throws CensusAnalyserException{
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_STATE_FILE_PATH,IndiaStateCode.class);
            censusAnalyser.loadStateCode();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type );
        }
    }
    @Test
    public void givenIndianStateCodeCSV_WhenWrongType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser( WRONG_STATE_FILE_TYPE,IndiaStateCode.class);
            censusAnalyser.loadStateCode();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeCSV_WhenWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_STATE_FILE_DELIMITER,IndiaStateCode.class);
            censusAnalyser.loadStateCode();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.WRONG_FILE_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeCSV_WhenWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(WRONG_STATE_FILE_HEADER,IndiaStateCode.class);
            censusAnalyser.loadStateCode();
        } catch (CsvFileBuilderException e) {
            Assert.assertEquals(CsvFileBuilderException.ExceptionType.WRONG_FILE_HEADER, e.type);
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {

        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(INDIA_CENSUS_CSV_FILE_PATH,IndiaStateCode.class);
            sortedCensusData = censusAnalyser.SortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeData_WhenSorted_ShouldReturnSortedList() throws CsvFileBuilderException {

        String sortedCensusData = null;

            CensusAnalyser censusAnalyser = new CensusAnalyser(INDIA_CENSUS_CSV_FILE_PATH,IndiaStateCode.class);
            sortedCensusData = censusAnalyser.SortedStateCodeData();
            IndiaStateCode[] stateCode = new Gson().fromJson(sortedCensusData, IndiaStateCode[].class);
            Assert.assertEquals("AD", stateCode[0].stateCode);

    }

}

