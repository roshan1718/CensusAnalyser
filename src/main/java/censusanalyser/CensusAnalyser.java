package censusanalyser;
import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E, numberOfRecords> {

    List<CensusDAO> list = null;
    Map<String, CensusDAO> map = null;

    public CensusAnalyser(String path, Class<E> csvClass) {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();


    }
    public CensusAnalyser() {

    }
    public int loadIndiaCensusData(String path) throws CensusAnalyserException {
        int numberOfRecords = 0;
        String extension = path.substring(path.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new CensusAnalyserException("Given File Not Found ", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
        try (Reader reader = newBufferedReader(Paths.get(path))) {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> StateCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> stateCensusIterable = () -> StateCensusCSVIterator;

            StreamSupport.stream(stateCensusIterable.spliterator(), false)
                    .forEach(stateCensusCSV -> map.put(stateCensusCSV.state, new CensusDAO(stateCensusCSV)));
                list = map.values().stream().collect(Collectors.toList());
            numberOfRecords = map.size();
         }
         catch (IOException e) {
            throw new CensusAnalyserException("Given File Not Found ",
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        } catch (RuntimeException | CsvFileBuilderException e) {
            throw new CensusAnalyserException("Check Delimiters Or Headers",
                    CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER);

        }
        return numberOfRecords;
    }


    public int loadStateCode(String path ) throws CensusAnalyserException  {
        int numberOfRecords = 0;

        try (Reader reader = newBufferedReader(Paths.get(path))) {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> StateCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeIterable = () -> StateCensusCSVIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false)
                    .filter(stateDataCSV -> map.get(stateDataCSV.statename) != null)
                    .forEach(stateDataCSV -> map.get(stateDataCSV.statename).StateCode = stateDataCSV.stateCode);
            numberOfRecords = map.size();

        } catch (IOException | CsvFileBuilderException e) {
            throw new CensusAnalyserException("Given File Not Found ",
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Check Delimiters Or Headers",
                    CensusAnalyserException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER);
        }
        return (numberOfRecords);
    }


    //METHOD TO LOAD STATE CODE CSV DATA AND COUNT NUMBER OF RECORD IN CSV FILE
    public int loadUSCensusCSVData(String path) throws CensusAnalyserException
    {
        int noOfRecords = 0;
        try (Reader reader = newBufferedReader(Paths.get(path)))
        {
            CsvToBean<USCensusCSV> usCensusCSV = new CsvToBeanBuilder(reader)
                    .withType(USCensusCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<USCensusCSV> usCensusIterator = usCensusCSV.iterator();
            while (usCensusIterator.hasNext())
            {
                USCensusCSV USCensus = usCensusIterator.next();
                System.out.println("State ID: " + USCensus.StateID);
                System.out.println("State Name : " + USCensus.State);
                System.out.println("Area : " + USCensus.Area);
                System.out.println("Housing units : " + USCensus.HousingUnits);
                System.out.println("Water area : " + USCensus.WaterArea);
                System.out.println("Land Area : " + USCensus.LandArea);
                System.out.println("Density : " + USCensus.PopulationDensity);
                System.out.println("Population : " + USCensus.Population);
                System.out.println("Housing Density : " + USCensus.HousingDensity);
                noOfRecords++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return noOfRecords;
    }


    public String SortedCensusData( ) throws CensusAnalyserException {
        if (list == null || list.size() == 0) {
            throw new CensusAnalyserException( "Census Data Not Found",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> comparator = Comparator.comparing(CensusDAO -> CensusDAO.population);
        this.sortData(comparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }



    public String SortedPopulationWiseData() throws CensusAnalyserException {
        if (list == null || list.size() == 0)
        {
            throw new CensusAnalyserException( "Census Data Not Found",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(CensusDAO -> CensusDAO.population);
        this.sortData(censusComparator);
        Collections.reverse(list);
        String sortedStateCodeJson = new Gson().toJson(list);
        return sortedStateCodeJson;
    }
    //METHOD FOR SORTING STATE CENSUS DATA CSV FILE BY DENSITY WISE
    public String sortedDataDensityWise() throws CensusAnalyserException {
        if (list == null || list.size() == 0) {
            throw new CensusAnalyserException( "Census Data Not Found", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.density);
        this.sortData(censusComparator);
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }
    //METHOD FOR SORTING STATE CENSUS DATA CSV FILE BY AREA WISE
    public String sortedDataAreaWise() throws CensusAnalyserException {
        if (list == null || list.size() == 0) {
            throw new CensusAnalyserException( "Census Data Not Found", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.area);
        this.sortData(censusComparator);
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }


    private void sortData(Comparator<CensusDAO> csvComparator)
    {
        for (int i = 0; i < list.size() - 1; i++)
        {
            for (int j = 0; j < list.size() - i - 1; j++)
            {
                CensusDAO census1 = list.get(j);
                CensusDAO census2 = list.get(j + 1);
                if (csvComparator.compare(census1,census2) > 0)
                {
                    list.set(j, census2);
                    list.set(j + 1, census1);
                }
            }
        }
    }
}


