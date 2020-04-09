package censusanalyser;
import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser<E> {
    private String CSV_FILE_PATH;

    List<IndiaCensusCSV> StateCensusCSVList = null;
    List<IndiaStateCode> StateDataCSVList = null;

    Map<String, IndiaCensusCSV> csvStatesCensusMap = null;
    Map<String, IndiaStateCode> StateDataCSVMap = null;


    public CensusAnalyser(String path,Class<E> csvClass) {
        this.CSV_FILE_PATH=path;
        this.csvStatesCensusMap = new HashMap<>();
        this.StateDataCSVMap = new HashMap<>();
    }

    public CensusAnalyser() {

    }


    public int loadIndiaCensusData() throws CsvFileBuilderException {

        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> StateCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (StateCensusCSVIterator.hasNext()) {
                IndiaCensusCSV csvStatesCensus = StateCensusCSVIterator.next();
                this.csvStatesCensusMap.put(csvStatesCensus.state, csvStatesCensus);
                StateCensusCSVList = csvStatesCensusMap.values().stream().collect(Collectors.toList());
            }
            int numberOfRecords = csvStatesCensusMap.size();
            return numberOfRecords;
        } catch (NoSuchFileException e) {
             throw new CsvFileBuilderException("Given File Not Found ",
                     CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE);
        } catch (RuntimeException | IOException e) {
            throw new CsvFileBuilderException("Check Delimiters Or Headers",
                    CsvFileBuilderException.ExceptionType.WRONG_FILE_DELIMITER);
        }
    }

    public int loadStateCode( )throws CsvFileBuilderException {

        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> StateCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            while (StateCensusCSVIterator.hasNext()) {
                IndiaStateCode csvStatesCensus = StateCensusCSVIterator.next();
                this.StateDataCSVMap.put(csvStatesCensus.stateCode, csvStatesCensus);
                StateDataCSVList = StateDataCSVMap.values().stream().collect(Collectors.toList());
            }
            int numberOfRecords = StateDataCSVMap.size();
            return (numberOfRecords+1);
        } catch (IOException e) {
            throw new CsvFileBuilderException("Given File Not Found ",
                    CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE);
        } catch (RuntimeException e) {
            throw new CsvFileBuilderException("Check Delimiters Or Headers",
                    CsvFileBuilderException.ExceptionType.WRONG_FILE_DELIMITER);
        }
    }

    public String SortedCensusData( ) throws CsvFileBuilderException {
        if (StateCensusCSVList == null || StateCensusCSVList.size() == 0) {
            throw new CsvFileBuilderException( "Census Data Not Found",
                    CsvFileBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> comparator = Comparator.comparing(StateCensusCSV -> StateCensusCSV.state);
        this.sortData(comparator, StateCensusCSVList);
        String sortedStateCensusJson = new Gson().toJson(StateCensusCSVList);
        return sortedStateCensusJson;
    }

    public String SortedStateCodeData() throws CsvFileBuilderException {
        if (StateDataCSVList == null || StateDataCSVList.size() == 0) {
            throw new CsvFileBuilderException( "Census Data Not Found",
                    CsvFileBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCode> comparator = Comparator.comparing(StateDataCSV -> StateDataCSV.stateCode);
        this.sortData(comparator, StateDataCSVList);
        String sortedStateCodeJson = new Gson().toJson(StateDataCSVList);
        return sortedStateCodeJson;
    }

    private <E> void sortData(Comparator<E> csvComparator, List<E> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                E census1 = list.get(j);
                E census2 = list.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    list.set(j, census2);
                    list.set(j + 1, census1);
                }
            }
        }
    }
}


