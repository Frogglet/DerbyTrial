/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbytrial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Alex
 */
public class MusicStoreLauncher {

    static Connection conn;
    static final boolean RECREATE = false;

    public static void main(String[] args) {
        try {
            conn = getConnection();

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getSchemas();
            boolean createFromScratch = true;
            while (rs.next()) {
                if (rs.getString(1).equals("MUSICSTORE")) {
                    System.out.println("MUSICSTORE found");
                    if (RECREATE) {
                        System.out.println("Dropping MUSICSTORE...");
                        conn.createStatement().execute("set schema MUSICSTORE");
                        deleteMusicStore();
                    } else {
                        createFromScratch = false;
                    }
                }
            }
            if (createFromScratch) {

                try {
                    createMusicStore();
                    populateTables();
                    createViews();
                } catch (SQLException s) {
                    try {
                        conn.rollback();
                    } catch (SQLException q) {
                        System.err.println("Rollback failed");
                        System.err.println(q.getMessage());
                    }
                    System.out.println(s.getMessage());
                } catch (IOException i) {
                    i.printStackTrace();
                    System.exit(1);
                }
            } else {
                conn.createStatement().execute("set schema MUSICSTORE");
            }
            
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT CAST(ARTIST_ID AS CHAR(10)) FROM ARTIST");
            rs2.next();
            System.out.println(rs2.getString(1));
            deleteViews();
            createViews();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
            s.printStackTrace();
        }

        // Open Master Menu JPanel after making connection
        if (!MasterMenu.getIsOpen()) {
            MasterMenu.setIsOpen(true);
            MasterMenu MM = new MasterMenu();
            MM.setVisible(true);
        }
    }
    
    private static void deleteViews() throws SQLException{
        Statement stmt = conn.createStatement();
        
        String query = 
                "SELECT T.TABLENAME "
                + "FROM SYS.SYSVIEWS V, SYS.SYSTABLES T, SYS.SYSSCHEMAS S "
                + "WHERE V.TABLEID = T.TABLEID "
                + "AND T.SCHEMAID = S.SCHEMAID "
                + "AND S.SCHEMANAME = 'MUSICSTORE'";
        ResultSet rs = stmt.executeQuery(query);
        
        Statement st2 = conn.createStatement();
        while (rs.next()){
            st2.executeUpdate("DROP VIEW " + rs.getString(1));
        }
        stmt.close();
        st2.close();
    }
    
    private static void createViews() throws SQLException{
        Statement stmt = conn.createStatement();
        
        stmt.executeUpdate("CREATE VIEW V_ARTIST_INFO "
                + "(ID, NAME, SONGS, ALBUMS) "
                + "AS "
                + "SELECT AR.ARTIST_ID, AR.ARTIST_NAME, COUNT(DISTINCT(S.SONG_ID)), COUNT(DISTINCT(AL.ALBUM_ID)) "
                + "FROM ARTIST AR, SONG S, ALBUM AL "
                + "WHERE AR.ARTIST_ID = AL.ARTIST_ID "
                + "AND S.ALBUM_ID = AL.ALBUM_ID "
                + "GROUP BY AR.ARTIST_ID, AR.ARTIST_NAME");
        
        stmt.executeUpdate("CREATE VIEW V_ALBUM_INFO "
                + "(ID, NAME, ARTIST, SONGS, RELEASE_DATE) "
                + "AS "
                + "SELECT AL.ALBUM_ID, AL.ALBUM_NAME, AR.ARTIST_NAME, COUNT(DISTINCT(S.SONG_ID)), CAST(AL.RELEASE_DATE AS VARCHAR(15)) "
                + "FROM ARTIST AR, SONG S, ALBUM AL "
                + "WHERE AR.ARTIST_ID = AL.ARTIST_ID "
                + "AND S.ALBUM_ID = AL.ALBUM_ID "
                + "GROUP BY AL.ALBUM_ID, AL.ALBUM_NAME, AR.ARTIST_NAME, CAST(AL.RELEASE_DATE AS VARCHAR(15))");
        
        stmt.executeUpdate("CREATE VIEW V_ALBUM_CARRY_INFO "
                + "(NAME, CARRY) "
                + "AS "
                + "SELECT AL.ALBUM_NAME, "
                    + "EXISTS (SELECT * "
                             + "FROM STOCK S "
                             + "WHERE S.ALBUM_ID = AL.ALBUM_ID) "
                + "FROM ALBUM AL");
        
        stmt.executeUpdate("CREATE VIEW V_STOCK_INFO "
                + "(STOCK_ID, ALBUM_ID, ALBUM_NAME, ARTIST, VENDOR_ID, VENDOR_NAME, FORMAT, PRICE, IN_STOCK, ON_ORDER, VENDOR_PRICE) "
                + "AS "
                + "SELECT S.STOCK_ID, S.ALBUM_ID, AL.ALBUM_NAME, AR.ARTIST_NAME, V.VENDOR_ID, V.VENDOR_NAME, S.FORMAT, S.SALE_PRICE, S.AMOUNT, S.ON_ORDER, S.VENDOR_COST "
                + "FROM STOCK S, ALBUM AL, ARTIST AR, VENDOR V "
                + "WHERE S.ALBUM_ID = AL.ALBUM_ID AND AR.ARTIST_ID = AL.ARTIST_ID AND S.VENDOR_ID = V.VENDOR_ID");
        
        stmt.executeUpdate("CREATE VIEW V_VENDOR_INFO "
                + "(ID, NAME, CONTACT, PHONE, ITEMS_CARRIED, ADDRESS, CITY, STATE, ZIP) "
                + "AS "
                + "SELECT V.VENDOR_ID, V.VENDOR_NAME, V.CONTACT_NAME, V.CONTACT_NUMBER, "
                    + "COUNT(*), V.ADDRESS, V.CITY, V.STATE, V.ZIP "
                + "FROM STOCK S, VENDOR V "
                + "WHERE V.VENDOR_ID = S.VENDOR_ID "
                + "GROUP BY V.VENDOR_ID, V.VENDOR_NAME, V.CONTACT_NAME, V.CONTACT_NUMBER, V.ADDRESS, V.CITY, V.STATE, V.ZIP");
        
        stmt.executeUpdate("CREATE VIEW V_SONG_INFO "
                + "(ID, ALBUM_ID, NAME, ARTIST_NAME, ALBUM_NAME) "
                + "AS "
                + "SELECT S.SONG_ID, AL.ALBUM_ID, S.SONG_NAME, AR.ARTIST_NAME, AL.ALBUM_NAME "
                + "FROM SONG S, ARTIST AR, ALBUM AL "
                + "WHERE S.ALBUM_ID = AL.ALBUM_ID AND AL.ARTIST_ID = AR.ARTIST_ID");
        
        stmt.executeUpdate("CREATE VIEW V_CUSTOMER_INFO "
                + "(ID,NAME,ORDERS,TOTAL_REVENUE,PHONE,ADDRESS,CITY,STATE,ZIP ) "
                + "AS "
                + "SELECT C.CUSTOMER_ID, C.NAME, COUNT(DISTINCT(S.SALE_ID)), SUM(SOL.COST), C.CONTACT_NUMBER, C.ADDRESS, C.CITY, C.STATE, C.ZIP "
                + "FROM CUSTOMER C, SALE S, SALE_ORDER_LINE SOL "
                + "WHERE C.CUSTOMER_ID = S.CUSTOMER_ID AND S.SALE_ID = SOL.SALE_ID "
                + "GROUP BY C.CUSTOMER_ID, C.NAME, C.CONTACT_NUMBER, C.ADDRESS, C.CITY, C.STATE, C.ZIP");
        conn.commit();
        stmt.close();
    }

    private static void deleteMusicStore() throws SQLException {
        deleteViews();
        String query = "SELECT C.CONSTRAINTNAME, T.TABLENAME, C.TYPE "
                + "FROM SYS.SYSCONSTRAINTS C, SYS.SYSSCHEMAS S, SYS.SYSTABLES T "
                + "WHERE C.SCHEMAID = S.SCHEMAID "
                + "AND C.TABLEID = T.TABLEID "
                + "AND S.SCHEMANAME = 'MUSICSTORE'";
        ResultSet rs = conn.createStatement().executeQuery(query);
        Statement stmt = conn.createStatement();
        while (rs.next()) {
            if (rs.getString(3).equalsIgnoreCase("F")) {
                stmt.executeUpdate("ALTER TABLE MUSICSTORE." + rs.getString(2) + " DROP CONSTRAINT " + rs.getString(1));
            }
        }

        query = "SELECT TABLENAME "
                + "FROM SYS.SYSTABLES T, SYS.SYSSCHEMAS S "
                + "WHERE T.SCHEMAID = S.SCHEMAID "
                + "AND S.SCHEMANAME = 'MUSICSTORE'";

        rs = conn.createStatement().executeQuery(query);
        while (rs.next()) {
            stmt.executeUpdate("DROP TABLE MUSICSTORE." + rs.getString(1));
        }

        stmt.executeUpdate("DROP SCHEMA MUSICSTORE RESTRICT");
        
        stmt.close();

    }

    private static String prepValues(String[] array) {
        StringBuilder valueString = new StringBuilder("values(");
        for (String s : array) {
            valueString.append(s).append(",");
        }
        valueString.deleteCharAt(valueString.length() - 1);
        valueString.append(")");
        return valueString.toString();
    }

    public static void populateTables() throws IOException, SQLException {
        List<String> albumList = Files.readAllLines(Paths.get("DummyData/AlbumTable.txt"));
        List<String> artistList = Files.readAllLines(Paths.get("DummyData/ArtistTable.txt"));
        List<String> customerList = Files.readAllLines(Paths.get("DummyData/CustomerTable.txt"));
        List<String> purchaseLineList = Files.readAllLines(Paths.get("DummyData/PurchaseOrderLineTable.txt"));
        List<String> purchaseList = Files.readAllLines(Paths.get("DummyData/PurchasesTable.txt"));
        List<String> saleLineList = Files.readAllLines(Paths.get("DummyData/SaleOrderLineTable.txt"));
        List<String> saleList = Files.readAllLines(Paths.get("DummyData/SalesTable.txt"));
        List<String> songList = Files.readAllLines(Paths.get("DummyData/SongTable.txt"));
        List<String> stockList = Files.readAllLines(Paths.get("DummyData/StockTable.txt"));
        List<String> vendorList = Files.readAllLines(Paths.get("DummyData/VendorTable.txt"));

        Statement stmt = conn.createStatement();
        stmt.execute("set schema MUSICSTORE");

        System.out.println("1");
        //artist table
        for (int i = 0; i < artistList.size(); i++) {

            String create = "insert into ARTIST(ARTIST_NAME) values (" + artistList.get(i) + ")";
            stmt.executeUpdate(create);
        }
        System.out.println("2");

        //album table
        for (String s : albumList) {
            String[] array = s.split("\t");
            String create
                    = "insert into ALBUM(ALBUM_NAME,RELEASE_DATE,GENRE,ARTIST_ID) "
                    + prepValues(array);
            stmt.executeUpdate(create);
        }
        System.out.println("3");
        //song table
        for (String s : songList) {
            String[] array = s.split("\t");

            String create
                    = "insert into SONG(SONG_NAME,ALBUM_ID) "
                    + prepValues(array);
            stmt.executeUpdate(create);
        }
        System.out.println("4");
        //vendor table
        for (String s : vendorList) {
            String[] array = s.split("\t");

            String create
                    = "insert into VENDOR(VENDOR_NAME,ADDRESS,CONTACT_NAME,"
                    + "CONTACT_NUMBER,CITY,STATE,ZIP) "
                    + prepValues(array);
            stmt.executeUpdate(create);
        }
        System.out.println("5");
        //Customer table
        for (String s : customerList) {
            String[] array = s.split("\t");

            String create
                    = "insert into CUSTOMER(NAME,ADDRESS,CONTACT_NUMBER,CITY,STATE,ZIP) "
                    + prepValues(array);
            stmt.executeUpdate(create);
        }
        System.out.println("6");
        //Stock table
        for (String s : stockList) {
            String[] array = s.split("\t");

            String create
                    = "insert into STOCK(ALBUM_ID,FORMAT,VENDOR_ID,SALE_PRICE,AMOUNT,ON_ORDER,VENDOR_COST) "
                    + prepValues(array);
            stmt.executeUpdate(create);
        }
        System.out.println("7");

        //Sale table        
        for (String s : saleList) {
            String[] array = s.split("\t");

            String create
                    = "insert into SALE(CUSTOMER_ID,DATE,PAY_METHOD) "
                    + prepValues(array);

            stmt.executeUpdate(create);
        }
        System.out.println("8");

        //Sale order line table
        for (String s : saleLineList) {
            String[] array = s.split("\t");

            String create
                    = "insert into SALE_ORDER_LINE " + prepValues(array);

            stmt.executeUpdate(create);
        }
        System.out.println("9");

        //purchase table
        for (String s : purchaseList) {
            String[] array = s.split("\t");

            String create
                    = "insert into PURCHASE_ORDER(VENDOR_ID,ORDER_DATE,DATE_TO_RECEIVE) "
                    + prepValues(array);

            stmt.executeUpdate(create);
        }
        System.out.println("10");

        //purchase order line table
        for (String s : purchaseLineList) {
            String[] array = s.split("\t");

            String create
                    = "insert into PURCHASE_ORDER_LINE " + prepValues(array);

            stmt.executeUpdate(create);
        }
        
        stmt.close();
        System.out.println("11");
        conn.commit();
    }

    public static void printTables() {
        
        String query = "SELECT TABLENAME "
                + "FROM SYS.SYSTABLES T, SYS.SYSSCHEMAS S "
                + "WHERE T.SCHEMAID = S.SCHEMAID "
                + "AND S.SCHEMANAME = 'MUSICSTORE'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            stmt.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public static void printSchemas() {
        String query = "SELECT SCHEMANAME FROM SYS.SYSSCHEMAS";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            
            stmt.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {

        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "Alex");
        connectionProps.put("password", "password");

        conn = DriverManager.getConnection(
                "jdbc:derby:testdb;create=true",
                connectionProps);

        System.out.println("Connected to database");

        conn.setAutoCommit(false);
        return conn;
    }

    public static void createMusicStore() throws SQLException {
        Statement stmt = conn.createStatement();
        String createString;
        //create schema
        createString
                = "create schema MUSICSTORE";
        stmt.execute(createString);
        //set schema
        stmt.execute("set schema MUSICSTORE");

        //Artist table
        createString
                = "create table ARTIST ("
                + "ARTIST_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "ARTIST_NAME varchar(45) NOT NULL, "
                + "PRIMARY KEY (ARTIST_ID))";
        stmt.executeUpdate(createString);
        //Album table
        createString
                = "create table ALBUM ("
                + "ALBUM_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "ALBUM_NAME varchar(45) NOT NULL ,"
                + "RELEASE_DATE date,"
                + "GENRE varchar(45),"
                + "ARTIST_ID int,"
                + "PRIMARY KEY (ALBUM_ID), "
                + "FOREIGN KEY (ARTIST_ID) REFERENCES ARTIST (ARTIST_ID))";
        stmt.executeUpdate(createString);
        //Songs table
        createString
                = "create table SONG ("
                + "SONG_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "SONG_NAME varchar(45) NOT NULL, "
                + "ALBUM_ID int, "
                + "PRIMARY KEY (SONG_ID), "
                + "FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ALBUM_ID))";
        stmt.executeUpdate(createString);

        //Vendor table
        createString
                = "create table VENDOR ("
                + "VENDOR_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "VENDOR_NAME varchar(45) NOT NULL, "
                + "ADDRESS varchar(45), "
                + "CONTACT_NAME varchar(45), "
                + "CONTACT_NUMBER varchar(45), "
                + "CITY varchar(45), "
                + "STATE varchar(2), "
                + "ZIP int, "
                + "PRIMARY KEY (VENDOR_ID))";
        stmt.executeUpdate(createString);

        //PurchaseOrders table
        createString
                = "create table PURCHASE_ORDER ("
                + "PO_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "VENDOR_ID int NOT NULL,"
                + "ORDER_DATE DATE,"
                + "DATE_TO_RECEIVE DATE,"
                + "PRIMARY KEY (PO_ID),"
                + "FOREIGN KEY (VENDOR_ID) REFERENCES VENDOR (VENDOR_ID))";
        stmt.executeUpdate(createString);

        //Stock table
        createString
                = "create table STOCK ("
                + "STOCK_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "ALBUM_ID int NOT NULL, "
                + "FORMAT varchar(45),"
                + "VENDOR_ID int NOT NULL,"
                + "SALE_PRICE decimal(10,2),"
                + "AMOUNT int,"
                + "ON_ORDER int, "
                + "VENDOR_COST decimal(10,2), "
                + "PRIMARY KEY (STOCK_ID),"
                + "FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ALBUM_ID))";
        stmt.executeUpdate(createString);

        //Purchase order line table
        createString
                = "create table PURCHASE_ORDER_LINE ("
                + "PO_ID int NOT NULL, "
                + "STOCK_ID int NOT NULL, "
                + "AMOUNT int, "
                + "COST decimal(10,2), "
                + "RECEIVED boolean, "
                + "PRIMARY KEY (PO_ID, STOCK_ID), "
                + "FOREIGN KEY (PO_ID) REFERENCES PURCHASE_ORDER (PO_ID), "
                + "FOREIGN KEY (STOCK_ID) REFERENCES STOCK (STOCK_ID))";
        stmt.executeUpdate(createString);

        //Customer table
        createString
                = "create table CUSTOMER ("
                + "CUSTOMER_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "NAME varchar(45), "
                + "ADDRESS varchar(45), "
                + "CONTACT_NUMBER varchar(45), "
                + "CITY varchar(45), "
                + "STATE varchar(2), "
                + "ZIP int, "
                + "PRIMARY KEY (CUSTOMER_ID))";
        stmt.executeUpdate(createString);

        //Sales table
        createString
                = "create table SALE ("
                + "SALE_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "CUSTOMER_ID int, "
                + "DATE date, "
                + "PAY_METHOD varchar(45), "
                + "PRIMARY KEY (SALE_ID), "
                + "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER (CUSTOMER_ID))";

        stmt.executeUpdate(createString);

        //Sale Order Line
        createString
                = "create table SALE_ORDER_LINE ("
                + "SALE_ID int NOT NULL, "
                + "STOCK_ID int NOT NULL, "
                + "AMOUNT int NOT NULL, "
                + "COST decimal (10,2) NOT NULL, "
                + "PRIMARY KEY (SALE_ID, STOCK_ID), "
                + "FOREIGN KEY (SALE_ID) REFERENCES SALE (SALE_ID), "
                + "FOREIGN KEY (STOCK_ID) REFERENCES STOCK (STOCK_ID))";

        stmt.executeUpdate(createString);

        conn.commit();
        stmt.close();
    }
}
