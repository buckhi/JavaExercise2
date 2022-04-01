import CarRentalManagement.DataSource;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static CarRentalManagement.DataSource.*;

public class Main extends DataSource {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, SQLException {
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
                    System.out.println(resultSet.getString(CLIENT_NAME) + " " + resultSet.getString(START_DATE) + " "
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
                System.out.println("Enter name of client's rental need to delete: ");
                String f = scanner.next();
                String deleteRental = "delete from " + RENTAL_TABLE + " where " + ID_CODE1 + "=" + "'" + f + "';";
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
                        START_DATE + ", " + END_DATE + ", " + ID_CODE + ") " +
                        "values('" + g + "', '" + h + "', '" + i + "', '" + l + "')";
                statement.executeUpdate(insertNewRental);
                break;

            case 6:
//                HSSFWorkbook wb = new HSSFWorkbook();
//                HSSFSheet sheet = wb.createSheet("Excel Sheet");
//                HSSFRow rowhead = sheet.createRow((short) 0);
                break;
            case 7:
                break;
            default:
                System.out.println("Invalid option!");


        }
        statement.close();
        connection.close();


    }
}
