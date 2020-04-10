package censusanalyser;

public class CensusDAO {

    public String StateName;
    public String state;
    public int population;
    public int area;
    public int density;
    public String StateCode;
    public String TIN;
    public String SrNo;



    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.population = indiaCensusCSV.population;
        this.area = indiaCensusCSV. areaInSqKm;
        this.density = indiaCensusCSV. densityPerSqKm;
    }
    public CensusDAO(IndiaStateCode stateDataCSV) {
        this.StateName = stateDataCSV.statename;
        this.SrNo = stateDataCSV.srNo;
        this.TIN = stateDataCSV.tin;
        this.StateCode = stateDataCSV.stateCode;
    }
}
