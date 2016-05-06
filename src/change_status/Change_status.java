package change_status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Change_status {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String[][] Table = null;

        try {
            con = change_status.Utils.getConnection();
            st = con.createStatement();

            String SQL_ITEM_RED = "update elro.items set `QM_STATUS`='RED' where "
                    + "(`LVD_TR` like '%MISSING%'"
                    + " or `EMC_TR` like '%MISSING%'"
                    + " or `RF_TR` like '%MISSING%'"
                    + " or `CPD_CE` like '%MISSING%'"
                    + " or `EUP_TR` like '%MISSING%')"
                    + " and (`COMPONENT1`='' or `COMPONENT1` is null);";
            st.executeUpdate(SQL_ITEM_RED);
            String SQL_ADAPTOR_RED = "update elro.items set `QC_STATUS`='RED' where"
                    + " `ADAPTOR1`=1"
                    + " and (`ADAPTOR1_LVD` like '%MISSING%' or `ADAPTOR1_LVD`='' or `ADAPTOR1_LVD` is null"
                    + " or `ADAPTOR1_EMC` like '%MISSING%' or `ADAPTOR1_EMC`='' or `ADAPTOR1_EMC` is null"
                    + " or `ADAPTOR1_ERP` like '%MISSING%' or `ADAPTOR1_ERP`='' or `ADAPTOR1_ERP` is null)"
                    + " and (`COMPONENT1`='' or `COMPONENT1` is null);";
            st.executeUpdate(SQL_ADAPTOR_RED);

            String SQL_ITEM_GREEN = "update elro.items set `QM_STATUS`='GREEN' where"
                    + " (`LVD_TR` not like '%MISSING%' or `LVD_TR` is null or `LVD_TR`='')"
                    + " and (`EMC_TR` not like '%MISSING%' or `EMC_TR` is null  or `EMC_TR`='')"
                    + " and (`RF_TR` not like '%MISSING%' or `RF_TR` is null or `RF_TR`='')"
                    + " and (`CPD_CE` not like '%MISSING%' or `CPD_CE` is null or `CPD_CE`='')"
                    + " and (`EUP_TR` not like '%MISSING%' or `EUP_TR` is null or `EUP_TR`='')"
                    + " and (PHOTOBIOL_TR not like '%MISSING%' or PHOTOBIOL_TR is null or PHOTOBIOL_TR='')"
                    + " and (IPCLASS_TR not like '%MISSING%' or IPCLASS_TR is null  or IPCLASS_TR='')"
                    + " and (`EUP_STATUS` not like '%1000h%' or `EUP_STATUS` is null  or `EUP_STATUS`='')"
                    + " and (ROHS_TR not like '%MISSING%' or ROHS_TR is null or ROHS_TR='')"
                    + " and (REACH_CE not like '%MISSING%' or REACH_CE is null or REACH_CE='')"
                    + " and (batt_m not like '%MISSING%' or batt_m is null or batt_m='')"
                    + " and (batt_tr2 not like '%MISSING%' or batt_tr2 is null or batt_tr2='')"
                    + " and (`COMPONENT1`='' or `COMPONENT1` is null);";
            st.executeUpdate(SQL_ITEM_GREEN);
            String SQL_ADAPTOR_GREEN = "update elro.items set `QC_STATUS`='GREEN' where"
                    + " `ADAPTOR1`=1"
                    + " and (`ADAPTOR1_LVD` not like '%MISSING%' and `ADAPTOR1_LVD`!='' and `ADAPTOR1_LVD` is not null)"
                    + " and (`ADAPTOR1_EMC` not like '%MISSING%' and `ADAPTOR1_EMC`!='' and `ADAPTOR1_EMC` is not null)"
                    + " and (`ADAPTOR1_ERP` not like '%MISSING%' and `ADAPTOR1_ERP`!='' and `ADAPTOR1_ERP` is not null)"
                    + " and (`ADAPTOR1_ROHS` not like '%MISSING%' and `ADAPTOR1_ROHS`!='' and `ADAPTOR1_ROHS` is not null)"
                    + " and (`COMPONENT1`='' or `COMPONENT1` is null);";
            st.executeUpdate(SQL_ADAPTOR_GREEN);

            String SQL_ITEM_ORANGE = "update elro.items set `QM_STATUS`='ORANGE' where "
                    + "((`LVD_TR` not like '%MISSING%' or `LVD_TR` is null or `LVD_TR`='')"
                    + " and (`EMC_TR` not like '%MISSING%' or `EMC_TR` is null or `EMC_TR`='')"
                    + " and (`RF_TR` not like '%MISSING%' or `RF_TR` is null or `RF_TR`='')"
                    + " and (`CPD_CE` not like '%MISSING%' or `CPD_CE` is null or `CPD_CE`='')"
                    + " and (`EUP_TR` not like '%MISSING%' or `EUP_TR` is null or `EUP_TR`=''))"
                    + " and (PHOTOBIOL_TR like '%MISSING%'"
                    + " or IPCLASS_TR like '%MISSING%'"
                    + " or `EUP_STATUS` like '%1000h%'"
                    + " or rohs_Tr like '%MISSING%'"
                    + " or REACH_CE like '%MISSING%'"
                    + " or batt_m like '%MISSING%'"
                    + " or batt_tr2 like '%MISSING%')"
                    + " and (`COMPONENT1`='' or `COMPONENT1` is null);";
            st.executeUpdate(SQL_ITEM_ORANGE);
            String SQL_ADAPTOR_ORANGE = "update elro.items set `QC_STATUS`='ORANGE' where"
                    + " `ADAPTOR1`=1"
                    + " and (`ADAPTOR1_LVD` not like '%MISSING%' and `ADAPTOR1_LVD`!='' and `ADAPTOR1_LVD` is not null)"
                    + " and (`ADAPTOR1_EMC` not like '%MISSING%' and `ADAPTOR1_EMC`!='' and `ADAPTOR1_EMC` is not null)"
                    + " and (`ADAPTOR1_ERP` not like '%MISSING%' and `ADAPTOR1_ERP`!='' and `ADAPTOR1_ERP` is not null)"
                    + " and (`ADAPTOR1_ROHS` like '%MISSING%' or `ADAPTOR1_ROHS`='' or `ADAPTOR1_ROHS` is null)"
                    + " and (`COMPONENT1`='' or `COMPONENT1` is null);";
            st.executeUpdate(SQL_ADAPTOR_ORANGE);

            String SQL_SETS = "SELECT id as idSet,qm_status as qm_status_set,"
                    + "left(COMPONENT1,9) as sap_comp1,left(COMPONENT2,9) as sap_comp2,left(COMPONENT3,9) as sap_comp3,left(COMPONENT4,9) as sap_comp4,left(COMPONENT5,9) as sap_comp5,"
                    + "left(COMPONENT6,9) as sap_comp6,left(COMPONENT7,9) as sap_comp7,left(COMPONENT8,9) as sap_comp8,left(COMPONENT9,9) as sap_comp9,left(COMPONENT10,9) as sap_comp10,"
                    //                    + "(SELECT qm_status FROM items WHERE sap=(SELECT left(COMPONENT1,9) FROM items WHERE id=idSet)),"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp1) as qm_status_comp1,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp2) as qm_status_comp2,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp3) as qm_status_comp3,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp4) as qm_status_comp4,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp5) as qm_status_comp5,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp6) as qm_status_comp6,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp7) as qm_status_comp7,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp8) as qm_status_comp8,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp9) as qm_status_comp9,"
                    + "(SELECT qm_status FROM items WHERE sap = sap_comp10) as qm_status_comp10"
                    + " from items where COMPONENT1<>'';";
            rs = st.executeQuery(SQL_SETS);
            rs.last();
            int rowNumb = rs.getRow();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnS = rsmd.getColumnCount();
            rs.beforeFirst();
            Table = new String[rowNumb][columnS];
//            String[] header = new String[columnS];
            int i = 0;
            int j = 0;
            while (rs.next() && i < rowNumb) {
                for (j = 0; j < columnS; j++) {
                    Table[i][j] = (rs.getString(j + 1));
//                    header[j] = rsmd.getColumnName(j + 1);
                }
                i++;
            }
            i = j = 0;

            for (i = 0; i < Table.length; i++) {
                if ((Table[i][12] != null && Table[i][12].equals("RED")) || (Table[i][13] != null && Table[i][13].equals("RED"))
                        || (Table[i][14] != null && Table[i][14].equals("RED")) || (Table[i][15] != null && Table[i][15].equals("RED"))
                        || (Table[i][16] != null && Table[i][16].equals("RED")) || (Table[i][17] != null && Table[i][17].equals("RED"))
                        || (Table[i][18] != null && Table[i][18].equals("RED")) || (Table[i][19] != null && Table[i][19].equals("RED"))
                        || (Table[i][20] != null && Table[i][20].equals("RED")) || (Table[i][21] != null && Table[i][21].equals("RED"))) {
                    if (!Table[i][1].equals("RED")) {
                        for (j = 0; j < Table[i].length; j++) {
                            System.out.print(Table[i][j] + "|");
                            String SQL_R = "UPDATE elro.items SET qm_status='RED' WHERE id=" + Table[i][0] + ";";
                            st.executeUpdate(SQL_R);
                        }
                    }
                } else if ((Table[i][12] != null && Table[i][12].equals("ORANGE")) || (Table[i][13] != null && Table[i][13].equals("ORANGE"))
                        || (Table[i][14] != null && Table[i][14].equals("ORANGE")) || (Table[i][15] != null && Table[i][15].equals("ORANGE"))
                        || (Table[i][16] != null && Table[i][16].equals("ORANGE")) || (Table[i][17] != null && Table[i][17].equals("ORANGE"))
                        || (Table[i][18] != null && Table[i][18].equals("ORANGE")) || (Table[i][19] != null && Table[i][19].equals("ORANGE"))
                        || (Table[i][20] != null && Table[i][20].equals("ORANGE")) || (Table[i][21] != null && Table[i][21].equals("ORANGE"))) {
                    if (!Table[i][1].equals("ORANGE")) {
                        for (j = 0; j < Table[i].length; j++) {
                            System.out.print(Table[i][j] + "|");
                            String SQL_O = "UPDATE elro.items SET qm_status='ORANGE' WHERE id=" + Table[i][0] + ";";
                            st.executeUpdate(SQL_O);
                        }
                    }
                } else {
                    if (!Table[i][1].equals("GREEN")) {
                        for (j = 0; j < Table[i].length; j++) {
                            System.out.print(Table[i][j] + "|");
                            String SQL_G = "UPDATE elro.items SET qm_status='GREEN' WHERE id=" + Table[i][0] + ";";
                            st.executeUpdate(SQL_G);
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Change_status.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            change_status.Utils.closeDB(rs, st, con);
        }
    }
}
