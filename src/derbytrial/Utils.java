/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbytrial;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Utils {

    static Connection conn = MusicStoreLauncher.conn;

    public static int[] getTypes(String tableName) throws SQLException {

        DatabaseMetaData meta = conn.getMetaData();
        ResultSet results
                = meta.getColumns(null, null, tableName, null);

        ArrayList<Integer> types = new ArrayList<>();
        while (results.next()) {
            types.add(results.getInt("DATA_TYPE"));
        }
        int[] array = new int[types.size()];
        for (int i = 0; i < types.size(); i++) {
            array[i] = types.get(i);
        }
        return array;
    }

    public static int getType(String tableName, String columnName) throws SQLException{
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet results
                = meta.getColumns(null, null, tableName, columnName);
        results.next();
        return results.getInt("DATA_TYPE");
    }
}
