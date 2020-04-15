package censusanalyser;

import java.util.Comparator;

public class CensusDAO {

    public String StateID;
    public String StateName;
    public long Population;
    public long AreaInSqKm;
    public long DensityPerSqKm;
    public String StateCode;
    public int TIN;
    public int SrNo;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.StateName = indiaCensusCSV.StateName;
        this.Population = indiaCensusCSV.Population;
        this.AreaInSqKm = indiaCensusCSV. AreaInSqKm;
        this.DensityPerSqKm = indiaCensusCSV. DensityPerSqKm;
    }
    public CensusDAO(IndiaStateCode stateDataCSV) {
        this.StateName = stateDataCSV.StateName;
        this.SrNo = stateDataCSV.SrNo;
        this.TIN = stateDataCSV.TIN;
        this.StateCode = stateDataCSV.StateCode;
    }
    public CensusDAO(USCensusCSV usCensusCSV){
        this.StateID = usCensusCSV.StateID;
        this.StateName = usCensusCSV.StateName;
        this.Population = usCensusCSV.Population;
        this.AreaInSqKm= usCensusCSV.Area;
        this.DensityPerSqKm = usCensusCSV.PopulationDensity;
    }
    public  Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusCSV(StateName, Population, AreaInSqKm, DensityPerSqKm);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(StateCode, StateName, Population, AreaInSqKm, DensityPerSqKm);
        return null;
    }
    public static Comparator<CensusDAO> getSortComparator(CensusAnalyser.SortingMode mode) {
        if (mode.equals(CensusAnalyser.SortingMode.STATENAME))
            return Comparator.comparing(census -> census.StateName);
        if (mode.equals(CensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CensusDAO::getAreaInSqKm).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getDensityPerSqkm).reversed();
        return null;
    }
    public long getPopulation() {
        return Population;
    }
    public long getAreaInSqKm() {
        return AreaInSqKm;
    }
    public long getDensityPerSqkm() {
        return DensityPerSqKm;
    }


}
