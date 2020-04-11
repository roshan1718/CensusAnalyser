package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State Id")
    public String StateID;

    @CsvBindByName(column = "State")
    public String StateName;

    @CsvBindByName(column = "Population Density")
    public int PopulationDensity;

    @CsvBindByName(column = "Population")
    public long Population;

    @CsvBindByName(column = "Total area")
    public long Area;

    @CsvBindByName(column = "Housing units")
    public String HousingUnits;

    @CsvBindByName(column = "Water area")
    public String WaterArea;

    @CsvBindByName(column = "Land Area")
    public String LandArea;

    @CsvBindByName(column = "Housing Density")
    public float HousingDensity;

    public USCensusCSV(String stateId, String state, long population, long totalArea, long populationDensity) {
        this.StateID = stateId;
        this.StateName = state;
        this.Population = population;
        this.Area = totalArea;
        this.PopulationDensity = (int) populationDensity;
    }



    @Override
    public String toString() {
        return "USCensusCSV{ StateID= "+ StateID + "State=" + StateName + "Population=" + Population + ", Area=" + Area + ", PopulationDensity=" + PopulationDensity + '}';
    }

}
