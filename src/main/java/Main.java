import CarRentalManagement.DataSource;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.util.*;

import static CarRentalManagement.DataSource.*;

public class Main extends DataSource {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(CONNECTION_STRING);
        Statement statement = connection.createStatement();
        DataSource dataSource = new DataSource();
        PreparedStatement ps = null;

        System.out.println("-------------Printing all the options------------------\n" +
                "1. Print the car list available for renting\n" +
                "2. Print the info of client rent the car\n" +
                "3. Add new vehicle to rental car list\n" +
                "4. Cancel rental from the list\n" +
                "5. Add new rental of vehicle\n" +
                "6. Export all rental vehicles in CSV file\n" +
                "7. Import new vehicles from CSV file to database\n" +
                "8. Print available vehicle to rent\n" +

                "Enter your choice: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:

                ResultSet resultSet = statement.executeQuery("select * from " + TABLE_CAR + " order by " + ID_CODE + " asc;");

                while (resultSet.next()) {
                    System.out.println(resultSet.getString(ID_CODE) + " " + resultSet.getString(BRAND) + " " + resultSet.getString(MODEL) + " " +
                            resultSet.getInt(SEAT_NUMBER) + " " + resultSet.getString(LICENSE_PLATE));

                }

                break;
            case 2:

                resultSet = statement.executeQuery("select * from " + RENTAL_TABLE);

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(ORDER_NUMBER) + " " + resultSet.getString(CLIENT_NAME) + " " + resultSet.getString(START_DATE) + " "
                            + resultSet.getString(END_DATE) + " " + resultSet.getString(ID_CODE1));
                }

                break;

            case 3:
                System.out.println("Enter ID of car: ");
                String a = scanner.next();
                System.out.println("Enter brand of car: ");
                String b = scanner.next();
                System.out.println("Enter model of car: ");
                String c = scanner.next();
                System.out.println("Enter seat number of car: ");
                int d = scanner.nextInt();
                System.out.println("Enter license plate of car: ");
                String e = scanner.next();

                String insertNewCar = "insert into " + TABLE_CAR + "(" + ID_CODE + "," + BRAND + "," + MODEL + "," + SEAT_NUMBER +
                        "," + LICENSE_PLATE + ") values(" + "'" + a + "', '" + b + "', '" + c + "', '" + d + "', '" + e + "')";
                statement.executeUpdate(insertNewCar);
                break;

            case 4:
                System.out.println("Enter order of client's rental need to delete: ");
                String f = scanner.next();
                String deleteRental = "delete from " + RENTAL_TABLE + " where " + ORDER_NUMBER + "='" + f + "';";
                statement.executeUpdate(deleteRental);
                break;
            case 5:
                System.out.println("Enter client's name: ");
                String g = scanner.next();
                System.out.println("Enter start date: ");
                String h = scanner.next();
                System.out.println("Enter end date: ");
                String i = scanner.next();
                System.out.println("Enter ID of the vehicle want to rent: ");
                String l = scanner.next();
                String insertNewRental = "insert into " + RENTAL_TABLE + " (" + CLIENT_NAME + ", " +
                        START_DATE + ", " + END_DATE + ", " + ID_CODE1 + ") " +
                        "values('" + g + "', '" + h + "', '" + i + "', '" + l + "')";
                statement.executeUpdate(insertNewRental);
                break;

            case 6:
                resultSet = statement.executeQuery("select * from " + TABLE_CAR);
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("Excel Sheet");
                HSSFRow rowHead = sheet.createRow((short) 0);
                rowHead.createCell((short) 0).setCellValue("Id");
                rowHead.createCell((short) 1).setCellValue("Brand");
                rowHead.createCell((short) 2).setCellValue("Model");
                rowHead.createCell((short) 3).setCellValue("Seat");
                rowHead.createCell((short) 4).setCellValue("Plate");
                int index = 1;
                while (resultSet.next()) {

                    HSSFRow row = sheet.createRow((short) index);
                    row.createCell((short) 0).setCellValue(resultSet.getString(1));
                    row.createCell((short) 1).setCellValue(resultSet.getString(2));
                    row.createCell((short) 2).setCellValue(resultSet.getString(3));
                    row.createCell((short) 3).setCellValue(resultSet.getInt(4));
                    row.createCell((short) 4).setCellValue(resultSet.getString(5));
                    index++;
                }
                FileOutputStream fileOut = new FileOutputStream("src/main/java/exportCar.xls");
                wb.write(fileOut);
                fileOut.close();
                System.out.println("Data is saved in excel file.");

                break;
            case 7:
                String fileName = "src/main/resources/importCar.xlsx";
                Vector dataHolder = read(fileName);
                saveToDatabase(dataHolder);


                break;
                case 8:
                String printRemainders = "select * from " + TABLE_CAR + " left join " + RENTAL_TABLE +
                        " on " + RENTAL_TABLE + "." + ID_CODE1 + "=" + TABLE_CAR + "." + ID_CODE +
                        " where " + RENTAL_TABLE + "." + ID_CODE1 + " is null";
                resultSet = statement.executeQuery(printRemainders);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(ID_CODE) + " " + resultSet.getString(BRAND) + " " + resultSet.getString(MODEL) + " " +
                            resultSet.getInt(SEAT_NUMBER) + " " + resultSet.getString(LICENSE_PLATE));

                }
                break;
            default:
                System.out.println("Invalid option!");


        }
        statement.close();
        connection.close();


    }

    public static Vector read(String fileName) {
        Vector cellVectorHolder = new Vector();
        try {
            FileInputStream myInput = new FileInputStream(fileName);
            //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                //Vector cellStoreVector=new Vector();
                List list = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    list.add(myCell);
                }
                cellVectorHolder.addElement(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellVectorHolder;
    }

    private static void saveToDatabase(Vector dataHolder) {
        String ID = "";
        String brand = "";
        String model = "";
        String seat = "";
        String plate = "";
        System.out.println(dataHolder);

        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext(); ) {
            List list = (List) iterator.next();
            ID = list.get(0).toString();
            brand = list.get(1).toString();
            model = list.get(2).toString();
            seat = list.get(3).toString();
            plate = list.get(4).toString();

            try {
                Connection connection = DriverManager.getConnection(CONNECTION_STRING);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO " + TABLE_CAR + " VALUES(?,?,?,?,?)");
                statement.setString(1, ID);
                statement.setString(2, brand);
                statement.setString(3, model);
                statement.setString(4, seat);
                statement.setString(5, plate);
                statement.executeUpdate();

                System.out.println("Data is inserted");
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}



