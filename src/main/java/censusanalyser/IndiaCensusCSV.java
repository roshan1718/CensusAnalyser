package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {


    @CsvBindByName(column = "State", required = true)
    public static String StateName;

    @CsvBindByName(column = "Population", required = true)
    public long Population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public long AreaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public long DensityPerSqKm;


    public IndiaCensusCSV(String state, long population, long area, long density ){
        StateName=state;
        Population=population;
        AreaInSqKm=area;
        DensityPerSqKm= density;
    }

     @Override
    public String toString(){
        return "IndiaCensusCSV Data { " +
                "StateName :"+StateName +
                ",State Population : " +Population +
                ",State AreaInSqKm : " +AreaInSqKm +
                ",State DensityPerSqKm : " +DensityPerSqKm + "}";
    }
}