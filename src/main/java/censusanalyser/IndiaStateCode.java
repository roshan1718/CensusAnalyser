package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "SrNo",required = true)
    public int SrNo;

    @CsvBindByName(column = "StateName",required = true)
    public String StateName;


    @CsvBindByName(column = "TIN",required = true)
    public int TIN;

    @CsvBindByName(column = "StateCode",required = true)
    public static String StateCode;



    @Override
    public String toString(){
        return "StateCodeCSV { " + "State Number :" +SrNo +
                ", State Name : " +StateName +
                ", TIN : " +TIN +
                ", State Code : " +StateCode + "}";
    }
}

