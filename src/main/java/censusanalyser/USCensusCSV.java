package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State Id")
    public String StateID;

    @CsvBindByName(column = "State")
    public String StateName;

    @CsvBindByName(column = "Population Density")
    public long PopulationDensity;

    @CsvBindByName(column = "Population")
    public long Population;

    @CsvBindByName(column = "Total area")
    public long Area;

    public USCensusCSV(String stateId, String state, long population, long totalArea, long populationDensity) {
        this.StateID = stateId;
        this.StateName = state;
        this.Population = population;
        this.Area = totalArea;
        this.PopulationDensity = populationDensity;
    }

    @Override
    public String toString() {
        return "USCensusCSV{ StateID= "+ StateID +
                "State=" + StateName +
                "Population=" + Population +
                ", Area=" + Area +
                ", PopulationDensity=" + PopulationDensity + '}';
    }

}
