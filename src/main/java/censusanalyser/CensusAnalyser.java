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

public class CensusAnalyser<E> {

    List<CensusDAO> list = null;
    Map<String, CensusDAO> map = null;

    public CensusAnalyser(String path, Class<E> csvClass) {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();


    }
    public CensusAnalyser() {

    }
    public int loadIndiaCensusData(String path) throws CsvFileBuilderException {
        int numberOfRecords = 0;
        String extension = path.substring(path.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new CsvFileBuilderException("Given File Not Found ", CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> StateCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (StateCensusCSVIterator.hasNext()) {
                 CensusDAO censusDAO = new CensusDAO(StateCensusCSVIterator.next());
                this.map.put(censusDAO.state, censusDAO);
                list = map.values().stream().collect(Collectors.toList());
            }
            numberOfRecords = map.size();
        } catch (NoSuchFileException e) {
            throw new CsvFileBuilderException("Given File Not Found ",
                    CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE);
        } catch (RuntimeException | IOException e) {
            throw new CsvFileBuilderException("Check Delimiters Or Headers",
                    CsvFileBuilderException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER);
        }
        return numberOfRecords;
    }

    public int loadStateCode(String path )throws CsvFileBuilderException {
        int numberOfRecords = 0;

        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            CsvBuilder csvBuilder = (CsvBuilder) CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> StateCensusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            while (StateCensusCSVIterator.hasNext()) {
                IndiaStateCode stateDataCSV = StateCensusCSVIterator.next();
                CensusDAO censusDAO=map.get(stateDataCSV.statename);
                this.map.put(censusDAO.StateCode, censusDAO);
                if (censusDAO == null)
                    continue;
                censusDAO.StateCode = stateDataCSV.stateCode;
            }
            numberOfRecords = map.size();

        } catch (IOException e) {
            throw new CsvFileBuilderException("Given File Not Found ",
                    CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE);
        } catch (RuntimeException e) {
            throw new CsvFileBuilderException("Check Delimiters Or Headers",
                    CsvFileBuilderException.ExceptionType.WRONG_FILE_DELIMITER_AND_HEADER);
        }
        return (numberOfRecords);
    }

    public String SortedCensusData( ) throws CsvFileBuilderException {
        if (list == null || list.size() == 0) {
            throw new CsvFileBuilderException( "Census Data Not Found",
                    CsvFileBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> comparator = Comparator.comparing(CensusDAO -> CensusDAO.population);
        this.sortData(comparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }



    public String SortedPopulationWiseData() throws CsvFileBuilderException {
        if (list == null || list.size() == 0)
        {
            throw new CsvFileBuilderException( "Census Data Not Found",
                    CsvFileBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(CensusDAO -> CensusDAO.population);
        this.sortData(censusComparator);
        Collections.reverse(list);
        String sortedStateCodeJson = new Gson().toJson(list);
        return sortedStateCodeJson;
    }
    //METHOD FOR SORTING STATE CENSUS DATA CSV FILE BY DENSITY WISE
    public String sortedDataDensityWise() throws CsvFileBuilderException {
        if (list == null || list.size() == 0) {
            throw new CsvFileBuilderException( "Census Data Not Found", CsvFileBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.density);
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


