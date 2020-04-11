package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser <E>{



    public enum Country {INDIA, US}

    private Country country;

    List<CensusDAO> censusList = null;
    Map<String, CensusDAO> censusDAOMap = null;

    //CONSTRUCTOR
    public CensusAnalyser() {
        this.censusDAOMap = new HashMap<>();
        this.censusList = new ArrayList<>();
    }
    //METHOD TO LOAD RECORDS OF CSV FILE
    public int loadIndiaCensusData(Country country, String... csvFilePath) throws  CensusAnalyserException
    {
        censusDAOMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        censusList = censusDAOMap.values().stream().collect(Collectors.toList());
        return censusDAOMap.size();
    }

    //METHOD TO LOAD STATE CODE CSV DATA AND COUNT NUMBER OF RECORD IN CSV FILE
    public String loadStateCode(Country india, String indiaStateCodeFilePath) throws CensusAnalyserException
    {
        if (censusList == null || censusList.size() == 0)
        {
            throw new CensusAnalyserException( "Census Header Not Found",
                    CensusAnalyserException. ExceptionType. WRONG_FILE_DELIMITER_AND_HEADER);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.StateName);
        this.sortData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    public String sortedDataPopulationWise() throws CensusAnalyserException
    {
        if (censusList == null || censusList.size() == 0)
        {
            throw new CensusAnalyserException( "Census Data Not Found",
                    CensusAnalyserException. ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(CensusDAO -> CensusDAO.Population);
        this.sortData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD FOR SORTING STATE CENSUS DATA CSV FILE BY DENSITY WISE
    public String sortedDataDensityWise() throws CensusAnalyserException
    {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException( "Census Data Not Found",
                    CensusAnalyserException. ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.DensityPerSqKm);
        this.sortData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD FOR SORTING STATE CENSUS DATA CSV FILE BY AREA WISE
    public String sortedDataAreaWise() throws CensusAnalyserException
    {
        if (censusList == null || censusList.size() == 0)
        {
            throw new CensusAnalyserException( "Census Data Not Found",
                    CensusAnalyserException.ExceptionType. NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.AreaInSqKm);
        this.sortData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sortData(Comparator<CensusDAO> csvComparator)
    {
        for (int i = 0; i < censusList.size() - 1; i++)
        {
            for (int j = 0; j < censusList.size() - i - 1; j++)
            {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (csvComparator.compare(census1,census2) > 0)
                {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
}