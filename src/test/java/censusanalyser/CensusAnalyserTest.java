package censusanalyser;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;



public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/Census.txt";
    private static final String WRONG_CSV_FILE_DELIMITER= "./src/test/resources/CensusDelimeter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_FILE_TYPE = "./src/test/resources/StateCode.txt";
    private static final String WRONG_STATE_FILE_DELIMITER = "./src/test/resources/IndiaStateInvalidDelimeter.csv";
    private static final String WRONG_STATE_FILE_HEADER = "./src/test/resources/IndiaStateInvalidHeader.csv";

    private static final String US_FILE_PATH = "./src/test/resources/USCensusData.csv";

    CensusAnalyser censusAnalyser = new CensusAnalyser();


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
             int numOfRecords = censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
             ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
             ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CsvFileBuilderException.class);
            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
             censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenIncorrectHeader_ThenTrue() {
        try{
             censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_HEADER);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER,e.type);
        }
    }
    //TEST FOR INDIA STATE CODE


    @Test
    public void givenIndiaStateCodeCSVFile_ShouldReturnsCorrectRecords() {
        try {
             String numOfRecords = censusAnalyser.loadStateCode(CensusAnalyser.Country.INDIA,INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {}
    }
    @Test
    public void givenIndiaStateCodeCSV_WhenWrongPath_ShouldReturnException(){
        try {

             censusAnalyser.loadStateCode(CensusAnalyser.Country.INDIA,WRONG_STATE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type );
        }
    }
    @Test
    public void givenIndianStateCodeCSV_WhenWrongType_ShouldThrowException() {
        try {
             censusAnalyser.loadStateCode(CensusAnalyser.Country.INDIA,WRONG_STATE_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeCSV_WhenWrongDelimiter_ShouldThrowException() {
        try {
             censusAnalyser.loadStateCode(CensusAnalyser.Country.INDIA,WRONG_STATE_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeCSV_WhenWrongHeader_ShouldThrowException() {
        try {
             censusAnalyser.loadStateCode(CensusAnalyser.Country.INDIA,WRONG_STATE_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSorted_ReturnSortedResult(){

        try {
            int SortedData = censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(String.valueOf(SortedData),IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }
        catch ( CensusAnalyserException e){
        }
    }
    @Test
    public void givenStateCodeData_WhenSorted_ShouldReturnSortedList(){
        try {
            String SortedData = censusAnalyser.loadStateCode(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCode[] StateCodes = new Gson().fromJson(SortedData,IndiaStateCode[].class);
            Assert.assertEquals("AD", StateCodes[0].stateCode);
        }catch(CensusAnalyserException e){
        }
    }
    @Test
    public void givenStateCensusData_WhenBasedSortSorted_ReturnSortedResult() {
         try {
             censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData =  censusAnalyser.sortedDataPopulationWise();
             IndiaCensusCSV[] statesCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, statesCensusCSV[0].population);
        } catch ( CensusAnalyserException e) {
            e.getStackTrace();
        }
    }
    @Test
    public void givenStateCensusData_WhenSortByDensity_ReturnSortedResult() throws CsvFileBuilderException{
         try {
             censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.sortedDataDensityWise();
            IndiaCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1102, stateCensusCSV[0].densityPerSqKm);
        } catch ( CensusAnalyserException e) {
            e.getStackTrace();
        }
    }
    @Test
    public void givenStateCensusData_WhenSortByArea_ReturnSortedResult() {
         try {
            censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.sortedDataAreaWise();
             IndiaCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(342239, stateCensusCSV[0].areaInSqKm);
        } catch ( CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    // Test Case For Load Records From USCensusCSV Data
    @Test
    public void givenUSCensusData_WhenTrue_RecordShouldMatch() throws CensusAnalyserException {
         int noOfRecords = censusAnalyser.loadIndiaCensusData(CensusAnalyser.Country.US,US_FILE_PATH);
        Assert.assertEquals(51, noOfRecords);
    }



}

