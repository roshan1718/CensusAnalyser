package censusanalyser;

public class CensusDAO {

    public String StateID;
    public String State;
    public String Population;
    public String AreaInSqKm;
    public String DensityPerSqKm;
    public String StateCode;
    public String HousingDensity;
    public String TIN;
    public int SrNo;



    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.State = indiaCensusCSV.state;
        this.Population = indiaCensusCSV.population;
        this.AreaInSqKm = indiaCensusCSV. areaInSqKm;
        this.DensityPerSqKm = indiaCensusCSV. densityPerSqKm;
    }
    public CensusDAO(IndiaStateCode stateDataCSV) {
        this.State = stateDataCSV.statename;
        this.SrNo = stateDataCSV.srNo;
        this.TIN = stateDataCSV.tin;
        this.StateCode = stateDataCSV.stateCode;
    }
    public CensusDAO(USCensusCSV usCensusCSV){
        this.StateID = usCensusCSV.StateID;
        this.State = usCensusCSV.State;
        this.Population = usCensusCSV.Population;
        this.AreaInSqKm= usCensusCSV.Area;
        this.DensityPerSqKm = usCensusCSV.PopulationDensity;
        this.HousingDensity = usCensusCSV.HousingDensity;
    }
}
