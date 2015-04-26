/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbytrial;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Alex
 */
public class DummyDataCreator {

    private static int numNamedArtists = 450,
            numBandArtists = 650,
            vendorCount = 25;

    private static String[] contactSheet, firstNames, lastNames, endsInY,
            endsInIng, mainPlural, mainSingular, oneWordWonders;

    private static String[] pluralWords = {"Zero", "Two", "Three", "Four", "Five",
        "Six", "Seven", "Eight", "Nine", "Ten"};

    private static String[] genres = {"Alternative", "Blues", "Classical", "Country",
        "Electronic", "Hip Hop", "Rap", "Jazz", "New Age", "R&B", "Rock", "World Music",
        "Heavy Metal"};

    private static String[] paymentTypes = {"Cash", "Credit", "Check", "Debit"};

    private static String[] formats = {"CD", "Cassette", "Vinyl"};

    private static String[] singularWords = {"", "One", "The"};

    private static String[] vowels = {"a", "e", "i", "o", "u"};

    private static ArrayList<String> artistList = new ArrayList<>(),
            songList = new ArrayList<>(),
            vendorList = new ArrayList<>(),
            customerList = new ArrayList<>(),
            saleLineList = new ArrayList<>(),
            purchaseList = new ArrayList<>(),
            purchaseLineList = new ArrayList<>();

    private static ArrayList<Stock> stockList = new ArrayList<>();
    private static ArrayList<Album> albumList = new ArrayList<>();

    private static LocalDate startPurchases = LocalDate.of(1987, 2, 14),
            startSales = LocalDate.of(1987, 7, 26);

    private static ArrayList<LocalDate> albumDates = new ArrayList<>();

    private static ArrayList<Sale> salesList = new ArrayList<>();

    private static HashMap<Integer, ArrayList<SaleLine>> stockToSales = new HashMap<>();
    private static HashMap<Integer, ArrayList<Stock>> vendorToStock = new HashMap<>();

    public static void main(String[] args) throws IOException {

        String[] template = new String[0];
        List<String> temp;
        temp = Files.readAllLines(Paths.get("DummyTexts/us-500.csv"));
        temp.remove(0);
        contactSheet = temp.toArray(template);
        escapeQuotes(contactSheet);

        temp = Files.readAllLines(Paths.get("DummyTexts/CSV_Database_of_First_Names.csv"));
        temp.remove(0);
        firstNames = temp.toArray(template);
        escapeQuotes(firstNames);

        temp = Files.readAllLines(Paths.get("DummyTexts/CSV_Database_of_Last_Names.csv"));
        temp.remove(0);
        lastNames = temp.toArray(template);
        escapeQuotes(lastNames);

        endsInY = Files.readAllLines(Paths.get("DummyTexts/endsInY.txt")).toArray(template);
        capitalizeArray(endsInY);
        escapeQuotes(endsInY);

        endsInIng = Files.readAllLines(Paths.get("DummyTexts/firstWordIng.txt")).toArray(template);
        capitalizeArray(endsInIng);
        escapeQuotes(endsInIng);

        mainPlural = Files.readAllLines(Paths.get("DummyTexts/mainWordPlural.txt")).toArray(template);
        capitalizeArray(mainPlural);
        escapeQuotes(mainPlural);

        mainSingular = Files.readAllLines(Paths.get("DummyTexts/mainWordSingular.txt")).toArray(template);
        capitalizeArray(mainSingular);
        escapeQuotes(mainSingular);

        oneWordWonders = Files.readAllLines(Paths.get("DummyTexts/oneWordWonders.txt")).toArray(template);
        capitalizeArray(oneWordWonders);
        escapeQuotes(oneWordWonders);

        createArtists();
        createAlbums();
        createSongs();
        createVendors();
        createStock();
        createCustomers();
        createSales();
        createSaleOrderLines();
        createPurchases();
        createPurchaseOrderLines();
    }

    private static void escapeQuotes(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].replaceAll("'", "''");
        }
    }

    private static void capitalizeArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].substring(0, 1).toUpperCase() + array[i].substring(1);
        }
    }

    private static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    private static String randString(String[] array) {
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    private static String getWeirdName() {

        String name = "";

        boolean singular = Math.random() < 0.5;
        boolean suffixIng = Math.random() < 0.5;
        if (singular) {
            name += randString(singularWords);
        } else {
            name += randString(pluralWords);
        }

        if (suffixIng) {
            name += name.length() == 0 ? "" : " ";
            name += randString(endsInIng);
        } else {
            name += name.length() == 0 ? "" : " ";
            name += randString(endsInY);
        }

        if (singular) {
            name += " " + randString(mainSingular);
        } else {
            name += " " + randString(mainPlural);
        }
        return name;
    }

    private static void createArtists() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/ArtistTable.txt"), Charset.defaultCharset())) {

            for (int i = 0; i < numBandArtists; i++) {
                String name = getWeirdName();
                writer.write(wrap(getWeirdName()));
                writer.newLine();

                artistList.add(wrap(name));
            }

            for (int i = 0; i < numNamedArtists; i++) {
                String name = randString(firstNames) + " " + randString(lastNames);
                writer.write(wrap(name));
                writer.newLine();

                artistList.add(wrap(name));
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    //finds random date between firstDate (inclusive) and lastDate(exclusive)
    private static LocalDate getRandomDate(LocalDate firstDate, LocalDate lastDate) {

        int days = (int) ChronoUnit.DAYS.between(firstDate, lastDate);
        int randomDay = randInt(0, days);

        return firstDate.plusDays(randomDay);
    }

    private static int randInt(int start, int end) {
        return start + (int) (Math.random() * (end - start + 1));
    }

    private static void createAlbums() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/AlbumTable.txt"), Charset.defaultCharset())) {
            int minAlbums = 1;
            int maxAlbums = 12;

            for (int i = 0; i < artistList.size(); i++) {
                int numAlbums = randInt(minAlbums, maxAlbums);
                LocalDate bandStart = getRandomDate(LocalDate.of(1920, 1, 1), LocalDate.now().minusYears(2));
                LocalDate bandEnd = bandStart.plusYears(40).compareTo(LocalDate.now()) > 0 ? LocalDate.now() : bandStart.plusYears(40);

                for (int j = 0; j < numAlbums; j++) {
                    String entry = "";
                    LocalDate date = getRandomDate(bandStart, bandEnd.minusYears(1));
                    albumDates.add(date);
                    String name;
                    if (Math.random() < 0.5) {
                        name = randString(oneWordWonders);
                    } else {
                        name = getWeirdName();
                    }

                    int artistID = i + 1;

                    entry += wrap(name) + "\t" + wrap(date.toString()) + "\t"
                            + wrap(randString(genres)) + "\t" + artistID;

                    writer.write(entry);
                    writer.newLine();

                    albumList.add(new Album(artistID));
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static void createSongs() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/SongTable.txt"), Charset.defaultCharset())) {
            int minSongs = 1;
            int maxSongs = 20;

            for (int i = 0; i < albumList.size(); i++) {
                int numSongs = randInt(minSongs, maxSongs);

                for (int j = 0; j < numSongs; j++) {
                    String name;
                    String entry = "";
                    if (Math.random() < 0.5) {
                        name = randString(oneWordWonders);
                    } else {
                        name = getWeirdName();
                    }

                    int albumID = i + 1;
                    
                    int artistID = albumList.get(i).artistID;

                    entry += wrap(name) + "\t" + albumID + "\t" + artistID;

                    writer.write(entry);
                    writer.newLine();

                    songList.add(entry);
                }
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static String wrap(String s) {
        return "'" + s + "'";
    }

    private static void createVendors() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/VendorTable.txt"), Charset.defaultCharset())) {

            for (int i = 0; i < vendorCount && i < contactSheet.length; i++) {
                String[] tokens = contactSheet[i].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int j = 0; j < tokens.length; j++) {
                    tokens[j] = tokens[j].replaceAll("\"", "");
                }
                String name = tokens[0] + " " + tokens[1];
                String companyName = tokens[2];
                String address = tokens[3];
                String city = tokens[4];
                String state = tokens[6];
                String zip = tokens[7];
                String phone = tokens[8];

                String entry = wrap(companyName) + "\t" + wrap(address) + "\t" + wrap(name) + "\t"
                        + wrap(phone) + "\t" + wrap(city) + "\t" + wrap(state) + "\t" + zip;

                writer.write(entry);
                writer.newLine();

                vendorList.add(entry);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static void createStock() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/StockTable.txt"), Charset.defaultCharset())) {

            double chanceWeCarry = 0.2;
            double chanceDepleted = 0.05;

            int stockID = 1;
            for (int i = 0; i < albumList.size(); i++) {
                if (Math.random() <= chanceWeCarry) {
                    int target = randInt(1, 15);
                    int inStock;
                    if (Math.random() <= chanceDepleted) {
                        inStock = target - randInt(1, target);
                    } else {
                        inStock = target;
                    }
                    int onOrder = target - inStock;
                    int vendorID = randInt(1, vendorList.size());
                    int dollarsSale = randInt(5, 25);
                    int centsSale = randInt(1, 99);
                    int dollarsVendor = randInt((int) (dollarsSale * 0.2), (int) (dollarsSale * 0.8));
                    int centsVendor = randInt(1, 99);

                    String salePrice = dollarsSale + "." + centsSale + "0";
                    String vendorPrice = dollarsVendor + "." + centsVendor + "0";

                    salePrice = salePrice.replaceAll("^(.*\\.\\d\\d).*$", "$1");
                    vendorPrice = vendorPrice.replaceAll("^(.*\\.\\d\\d).*$", "$1");

                    int albumID = i + 1;

                    String entry = "" + albumID + "\t" + wrap(randString(formats)) + "\t"
                            + vendorID + "\t" + salePrice + "\t" + inStock
                            + "\t" + onOrder + "\t" + vendorPrice;

                    writer.write(entry);
                    writer.newLine();

                    Stock stock = new Stock(stockID, albumID, vendorID, albumDates.get(i),
                            inStock, onOrder, dollarsVendor, centsVendor, dollarsSale,
                            centsSale);

                    if (vendorToStock.containsKey(vendorID)) {
                        vendorToStock.get(vendorID).add(stock);
                    } else {
                        ArrayList<Stock> list = new ArrayList<>();
                        list.add(stock);
                        vendorToStock.put(vendorID, list);
                    }

                    stockID++;

                    stockList.add(stock);
                }
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static void createCustomers() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/CustomerTable.txt"), Charset.defaultCharset())) {

            for (int i = vendorCount; i < contactSheet.length; i++) {
                String[] tokens = contactSheet[i].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int j = 0; j < tokens.length; j++) {
                    tokens[j] = tokens[j].replaceAll("\"", "");
                }
                String name = tokens[0] + " " + tokens[1];
                String address = tokens[3];
                String city = tokens[4];
                String state = tokens[6];
                String zip = tokens[7];
                String phone = tokens[8];

                String entry = wrap(name) + "\t" + wrap(address) + "\t" + wrap(phone) + "\t"
                        + wrap(city) + "\t" + wrap(state) + "\t" + zip;

                writer.write(entry);
                writer.newLine();

                customerList.add(entry);
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static void createSales() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/SalesTable.txt"), Charset.defaultCharset())) {
            int saleID = 1;
            for (int i = 0; i < customerList.size(); i++) {
                int numSales = randInt(1, 10);

                for (int j = 0; j < numSales; j++) {

                    LocalDate date = getRandomDate(startSales, LocalDate.now());
                    String payType = randString(paymentTypes);
                    int customerID = i + 1;

                    String entry = customerID + "\t" + wrap(date.toString()) + "\t" + wrap(payType);

                    writer.write(entry);
                    writer.newLine();

                    salesList.add(new Sale(saleID, customerID, date));
                    saleID++;
                }
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static String priceMultiply(int dollars, int cents, int num) {
        int newCents = (cents * num) % 100;
        int newDollars = dollars * num + (cents * num) / 100;

        return newDollars + "." + newCents;
    }

    private static void createSaleOrderLines() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/SaleOrderLineTable.txt"), Charset.defaultCharset())) {
            int maxAmount = 5;
            int maxLines = 10;

            for (int i = 0; i < salesList.size(); i++) {
                int lines = randInt(1, maxLines);
                int saleID = salesList.get(i).saleID;
                List<Integer> prevStock = new ArrayList<>();

                for (int j = 0; j < lines; j++) {
                    //finding a random stock index for the SaleOrderLine record
                    //such that the stock has a release date at least 10 days 
                    //before the corresponding sale date

                    //if i sorted this stuff it would be more efficient, but I'm lazy
                    int stockID;
                    boolean validDate;
                    do {
                        stockID = randInt(1, stockList.size());
                        int index = stockID - 1;
                        validDate = stockList.get(index).releaseDate.isBefore(salesList.get(i).date.minusDays(10));
                    } while (prevStock.contains(stockID) || !validDate);

                    prevStock.add(stockID);

                    int amount = randInt(1, maxAmount);

                    SaleLine saleLine = new SaleLine(stockID, salesList.get(i).date, amount);

                    //this map is so that later on we can see all sale lines
                    //that correspond to a given peice of stock
                    if (stockToSales.containsKey(stockID)) {
                        stockToSales.get(stockID).add(saleLine);
                    } else {
                        ArrayList<SaleLine> list = new ArrayList<>();
                        list.add(saleLine);
                        stockToSales.put(stockID, list);
                    }
                    int dollars = stockList.get(stockID - 1).saleDollars;
                    int cents = stockList.get(stockID - 1).saleCents;
                    String price = priceMultiply(dollars, cents, amount);
                    price += "0";

                    price = price.replaceAll("^(.*\\.\\d\\d).*$", "$1");

                    String entry = saleID + "\t" + stockID + "\t" + amount + "\t"
                            + price;
                    writer.write(entry);
                    writer.newLine();

                    saleLineList.add(entry);
                }
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static void createPurchases() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/PurchasesTable.txt"), Charset.defaultCharset())) {

            //Received orders first
            //identify needs
            int purchaseID = 1;

            for (int i = 0; i < vendorList.size(); i++) { //for each vendor
                int vendorID = i + 1;
                ArrayList<Need> orderedNeeds = new ArrayList<>();
                ArrayList<Stock> stock = vendorToStock.get(vendorID);

                for (Stock s : stock) { //for each stock item that the vendor supplies
                    if (stockToSales.containsKey(s.stockID)) {
                        ArrayList<SaleLine> lines = stockToSales.get(s.stockID);

                        for (SaleLine line : lines) {
                            addAscending(orderedNeeds, new Need(line));
                        }
                    }
                    if (s.inStock > 0) {
                        addAscending(orderedNeeds, new Need(s.stockID, s.inStock, LocalDate.now()));
                    }
                }
                if (orderedNeeds.isEmpty()) {
                    continue;
                }

                int cursor = 0;
                int start = 0;
                LocalDate startDate = startPurchases;
                LocalDate endDate;

                do {

                    cursor = randInt(cursor + 1, orderedNeeds.size() - 1);

                    ArrayList<Need> bundle = bundler(orderedNeeds, start, cursor);

                    int shippingWait = randInt(4, 9);

                    endDate = bundle.get(0).dateNeededBy.minusDays(shippingWait);

                    LocalDate dateOrdered = getRandomDate(startDate, endDate);
                    LocalDate dateToReceive = dateOrdered.plusDays(shippingWait);

                    String entry = "" + vendorID + "\t" + wrap(dateOrdered.toString()) + "\t"
                            + wrap(dateToReceive.toString());

                    writer.write(entry);
                    writer.newLine();

                    purchaseList.add(entry);

                    for (Need n : bundle) {
                        int dollars = stockList.get(n.stockID - 1).vendorDollars;
                        int cents = stockList.get(n.stockID - 1).vendorCents;

                        String price = priceMultiply(dollars, cents, n.amount);
                        price += "0";

                        price = price.replaceAll("^(.*\\.\\d\\d).*$", "$1");
                        String lineEntry = "" + purchaseID + "\t" + n.stockID
                                + "\t" + n.amount + "\t" + price + "\t" + "TRUE";
                        purchaseLineList.add(lineEntry);
                    }

                    start = cursor + 1;
                    startDate = endDate;
                    purchaseID++;

                } while (cursor != orderedNeeds.size() - 1);
            }
            //now orders currently in-transit
            for (int i = 0; i < vendorList.size(); i++) {
                int vendorID = i + 1;
                ArrayList<Need> needs = new ArrayList<>();
                ArrayList<Stock> stock = vendorToStock.get(vendorID);

                for (Stock s : stock) { //for each stock item that the vendor supplies
                    if (s.onOrder > 0) {

                        needs.add(new Need(s.stockID, s.onOrder, LocalDate.now()));

                        bundler(needs);

                        int shippingWait = randInt(4, 9);
                        LocalDate dateOrdered = LocalDate.now().minusDays(randInt(0, shippingWait - 2));
                        LocalDate dateToReceive = dateOrdered.plusDays(shippingWait);

                        String entry = "" + vendorID + "\t" + wrap(dateOrdered.toString()) + "\t"
                                + wrap(dateToReceive.toString());

                        writer.write(entry);
                        writer.newLine();

                        for (Need n : needs) {
                            int dollars = stockList.get(n.stockID - 1).vendorDollars;
                            int cents = stockList.get(n.stockID - 1).vendorCents;
                            String price = priceMultiply(dollars, cents, n.amount);
                            price += "0";

                            price = price.replaceAll("^(.*\\.\\d\\d).*$", "$1");

                            String lineEntry = "" + purchaseID + "\t" + n.stockID
                                    + "\t" + n.amount + "\t" + price + "\t" + "FALSE";
                            purchaseLineList.add(lineEntry);
                        }
                        purchaseID++;
                    }
                }

            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static ArrayList<Need> bundler(ArrayList<Need> list) {
        //merging sublist together into a "bundle"
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).stockID == list.get(j).stockID) {
                    list.get(i).merge(list.get(j));
                    list.remove(j);
                    j--;
                }
            }
        }

        return list;
    }

    private static ArrayList<Need> bundler(ArrayList<Need> list, int prev, int cursor) {
        //creating sublist
        ArrayList<Need> bundle = new ArrayList<>();
        for (int i = prev; i <= cursor; i++) {
            bundle.add(list.get(i));
        }

        return bundler(bundle);
    }

    private static <T extends Comparable> void addAscending(ArrayList<T> list, T element) {
        int index = 0;
        while (index < list.size() && element.compareTo(list.get(index)) > 0) {
            index++;
        }
        list.add(index, element);
    }

    private static void createPurchaseOrderLines() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("DummyData/PurchaseOrderLineTable.txt"), Charset.defaultCharset())) {

            for (String entry : purchaseLineList) {
                writer.write(entry);
                writer.newLine();
            }

        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private static class SaleLine {

        public int amount;
        public LocalDate date;
        public int stockID;

        public SaleLine(int stockID, LocalDate date, int amount) {
            this.stockID = stockID;
            this.amount = amount;
            this.date = date;
        }
    }

    private static class Sale {

        public int customerID, saleID;
        public LocalDate date;

        public Sale(int saleID, int customerID, LocalDate date) {
            this.saleID = saleID;
            this.customerID = customerID;
            this.date = date;
        }
    }

    private static class Stock {

        public int stockID, albumID, vendorID;
        public LocalDate releaseDate;
        public int inStock;
        public int onOrder;
        public int vendorDollars;
        public int vendorCents;
        public int saleDollars;
        public int saleCents;

        public Stock(int stockID, int albumID, int vendorID, LocalDate releaseDate,
                int inStock, int onOrder, int vendorDollars, int vendorCents,
                int saleDollars, int saleCents) {
            this.stockID = stockID;
            this.albumID = albumID;
            this.vendorID = vendorID;
            this.releaseDate = releaseDate;
            this.inStock = inStock;
            this.onOrder = onOrder;
            this.vendorDollars = vendorDollars;
            this.vendorCents = vendorCents;
            this.saleCents = saleCents;
            this.saleDollars = saleDollars;
        }
    }

    private static class Need implements Comparable<Need> {

        public int stockID;
        public int amount;
        public LocalDate dateNeededBy;

        public Need(int stockID, int amount, LocalDate dateNeededBy) {
            this.stockID = stockID;
            this.amount = amount;
            this.dateNeededBy = dateNeededBy;
        }

        public Need(SaleLine line) {
            stockID = line.stockID;
            amount = line.amount;
            dateNeededBy = line.date;
        }

        public int compareTo(Need n) {
            return dateNeededBy.compareTo(n.dateNeededBy);
        }

        public void merge(Need n) {
            amount += n.amount;
        }
    }
    
    private static class Album{
        public int artistID;
        public Album (int artistID){
            this.artistID = artistID;
        }
    }

}
