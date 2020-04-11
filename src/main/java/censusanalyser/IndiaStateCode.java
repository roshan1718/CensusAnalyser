package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "SrNo",required = true)
    public int srNo;

    @CsvBindByName(column = "StateName",required = true)
    public String statename;


    @CsvBindByName(column = "TIN",required = true)
    public int tin;

    @CsvBindByName(column = "StateCode",required = true)
    public static String stateCode;


    public IndiaStateCode(int no, String name, String code,  String tin){
        srNo=no;
        statename=name;
        stateCode=code;
        tin=tin;
    }

    @Override
    public String toString() {
        return "com.bridgelabz.censusanalyserproject.IndianStateCode {"+
                "SrNo='" + srNo + '\'' +
                " ,StateName='" + statename + '\'' +
                " ,TIN='" + tin + '\'' +
                " ,StateCode='" + stateCode + '\'' +
                '}';
    }
}
