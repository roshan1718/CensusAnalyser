package censusanalyser;

public class CensusDAO {
    public String state;
    public int population;
    public int area;
    public int density;
    public String stateCode;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.population = indiaCensusCSV.population;
        this.area = indiaCensusCSV. areaInSqKm;
        this.density = indiaCensusCSV. densityPerSqKm;
    }
}
