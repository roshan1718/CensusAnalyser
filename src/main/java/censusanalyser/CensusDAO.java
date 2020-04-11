package censusanalyser;

public class CensusDAO {

    public String StateID;
    public String StateName;
    public long Population;
    public long AreaInSqKm;
    public long DensityPerSqKm;
    public String StateCode;
    public float HousingDensity;
    public int TIN;
    public int SrNo;



    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.StateName = indiaCensusCSV.StateName;
        this.Population = indiaCensusCSV.population;
        this.AreaInSqKm = indiaCensusCSV. areaInSqKm;
        this.DensityPerSqKm = indiaCensusCSV. densityPerSqKm;
    }
    public CensusDAO(IndiaStateCode stateDataCSV) {
        this.StateName = stateDataCSV.statename;
        this.SrNo = stateDataCSV.srNo;
        this.TIN = stateDataCSV.tin;
        this.StateCode = stateDataCSV.stateCode;
    }
    public CensusDAO(USCensusCSV usCensusCSV){
        this.StateID = usCensusCSV.StateID;
        this.StateName = usCensusCSV.StateName;
        this.Population = usCensusCSV.Population;
        this.AreaInSqKm= usCensusCSV.Area;
        this.DensityPerSqKm = usCensusCSV.PopulationDensity;
        this.HousingDensity = usCensusCSV.HousingDensity;
    }
    public  Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusCSV(StateName, Population, AreaInSqKm, DensityPerSqKm);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(StateCode, StateName, Population, AreaInSqKm, DensityPerSqKm);
        return null;
    }
}
