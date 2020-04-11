package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {


    @CsvBindByName(column = "State", required = true)
    public static String StateName;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;


    public IndiaCensusCSV(String state, int population, int area, int density ){
        StateName=state;
        population=population;
        areaInSqKm=area;
        densityPerSqKm= density;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + StateName + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}