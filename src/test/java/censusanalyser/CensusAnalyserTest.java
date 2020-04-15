package censusanalyser;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;


import static censusanalyser.CensusAnalyser.Country.INDIA;
import static censusanalyser.CensusAnalyser.Country.US;


public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/Census.txt";
    private static final String WRONG_CSV_FILE_DELIMITER= "./src/test/resources/CensusDelimeter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_FILE_TYPE = "./src/test/resources/StateCode.txt";
    private static final String WRONG_STATE_FILE_DELIMITER = "./src/test/resources/IndiaStateInvalidDelimeter.csv";
    private static final String WRONG_STATE_FILE_HEADER = "./src/test/resources/IndiaStateInvalidHeader.csv";

    private static final String US_FILE_PATH = "./src/test/resources/USCensusData.csv";


    CensusAnalyser indianCensusAnalyzer = new CensusAnalyser( INDIA);
    CensusAnalyser usCensusAnalyzer = new CensusAnalyser(US);


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
             int numOfRecords = indianCensusAnalyzer.loadIndiaCensusData( INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {

            indianCensusAnalyzer.loadIndiaCensusData(INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            indianCensusAnalyzer.loadIndiaCensusData( INDIA,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            indianCensusAnalyzer.loadIndiaCensusData( INDIA,WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenIncorrectHeader_ThenTrue() {
        try{
            indianCensusAnalyzer.loadIndiaCensusData(INDIA,WRONG_CSV_FILE_HEADER);
        }catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER,e.type);
        }
    }
    //TEST FOR INDIA STATE CODE


    @Test
    public void givenIndiaStateCodeCSVFile_ShouldReturnsCorrectRecords() {
        try {
           int numOfRecords = indianCensusAnalyzer.loadIndiaCensusData(INDIA,INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {}
    }
    @Test
    public void givenIndiaStateCodeCSV_WhenWrongPath_ShouldReturnException(){
        try {

            indianCensusAnalyzer.loadIndiaCensusData(INDIA,WRONG_STATE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type );
        }
    }
    @Test
    public void givenIndianStateCodeCSV_WhenWrongType_ShouldThrowException() {
        try {
            indianCensusAnalyzer.loadIndiaCensusData(INDIA,WRONG_STATE_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeCSV_WhenWrongDelimiter_ShouldThrowException() {
        try {
            indianCensusAnalyzer.loadIndiaCensusData(INDIA,WRONG_STATE_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeCSV_WhenWrongHeader_ShouldThrowException() {
        try {
            indianCensusAnalyzer.loadIndiaCensusData(INDIA,WRONG_STATE_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianStateCensusData_WhenSorted_ReturnSortedResult(){

        try {
            indianCensusAnalyzer.loadIndiaCensusData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String SortedData = indianCensusAnalyzer.loadStateCode(CensusAnalyser.SortingMode.STATENAME);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(SortedData,  IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].StateName);
        }
        catch ( CensusAnalyserException e){
        }
    }

    @Test
    public void givenStateCensusData_WhenBasedSortSorted_ReturnSortedResult() {
         try {
             indianCensusAnalyzer.loadIndiaCensusData(INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData =  indianCensusAnalyzer.sortedDataPopulationWise();
             IndiaCensusCSV[] statesCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, statesCensusCSV[0].Population);
        } catch ( CensusAnalyserException e) {
             e.printStackTrace();
        }
    }
    @Test
    public void givenStateCensusData_WhenSortByDensity_ReturnSortedResult() throws CsvFileBuilderException{
         try {
             indianCensusAnalyzer.loadIndiaCensusData(INDIA,INDIA_CENSUS_CSV_FILE_PATH);
             String sortedCensusData = indianCensusAnalyzer.loadStateCode(CensusAnalyser.SortingMode.DENSITY);
            IndiaCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1102, stateCensusCSV[0].DensityPerSqKm);
        } catch ( CensusAnalyserException e) {
            e.getStackTrace();
        }
    }
    @Test
    public void givenStateCensusData_WhenSortByArea_ReturnSortedResult() {
         try {
             indianCensusAnalyzer.loadIndiaCensusData(INDIA,INDIA_CENSUS_CSV_FILE_PATH);
             String sortedCensusData = indianCensusAnalyzer.loadStateCode(CensusAnalyser.SortingMode.AREA);
             IndiaCensusCSV[] stateCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(342239, stateCensusCSV[0].AreaInSqKm);
        } catch ( CensusAnalyserException e) {
            e.getStackTrace();
        }
    }

    // Test Case For Load Records From USCensusCSV Data
    @Test
    public void givenUSCensusData_WhenTrue_RecordShouldMatch()  {

        try {
          int noOfRecords = usCensusAnalyzer.loadIndiaCensusData(US,US_FILE_PATH);
            Assert.assertEquals(51, noOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
    // Test Case For Sort The Data of USCensusCSV By Population
    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ReturnSortedResult() {

        try {
            usCensusAnalyzer.loadIndiaCensusData(US, US_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.sortedDataPopulationWise();
            CensusDAO[] censusDAO = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("California",censusDAO[0].StateName);
        } catch ( CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    // Test Case For Sort The Data of USCensusCSV By PopulationDensity
    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        final String CSV_FILE_PATH = "src/test/resources/USCensusData.csv";
        try {
            usCensusAnalyzer.loadIndiaCensusData(US, US_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.loadStateCode(CensusAnalyser.SortingMode.DENSITY);
            CensusDAO[] censusDAO = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("District of Columbia",censusDAO[0].StateName);
        } catch ( CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    // Test Case For Sort The Data of USCensusCSV By Area
    @Test
    public void givenTheUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
         try {
            usCensusAnalyzer.loadIndiaCensusData(US, US_FILE_PATH);
            String sortedCensusData = usCensusAnalyzer.loadStateCode(CensusAnalyser.SortingMode.AREA);
            CensusDAO[] censusDAO = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Alaska",censusDAO[0].StateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


}

