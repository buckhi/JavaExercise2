import CarRentalManagement.DataSource;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import static CarRentalManagement.DataSource.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File("C:\\Users\\nbhai\\Desktop\\car_rental.csv"));
//        scanner.useDelimiter(" , ");
//
//        while (scanner.hasNext()) {
//            System.out.print(scanner.next());
//        }
//        scanner.close();

        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = connection.createStatement();
//            statement.execute("create table if not exists " + RENTAL_TABLE +
//                    " (" + CLIENT_NAME + " TEXT, " + START_DATE + " TEXT, " + END_DATE + " TEXT, " + ID_CODE + " TEXT, primary key (" +
//                    ID_CODE + "));");
            ResultSet resultSet = statement.executeQuery("select * from " + TABLE_CAR);

            while (resultSet.next()) {
                System.out.println(resultSet.getString(ID_CODE) + " " + resultSet.getString(BRAND) + " " + resultSet.getString(MODEL) + " " +
                        resultSet.getInt(SEAT_NUMBER) + " " + resultSet.getString(LICENSE_PLATE));

            }
            statement.close();
            connection.close();


        } catch (SQLException exception) {
            System.out.println("Error: can't connect to the database " + exception.getMessage());
            exception.printStackTrace();
        }


        System.out.println("====================================");
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = connection.createStatement();

//            statement.execute("delete from " + RENTAL_TABLE + " where " + CLIENT_NAME + "='hai'");
//            insertRental(statement, "hai", "31/03/2022", "01/04/2022", "C03");
//            insertRental(statement, "joe", "23/12/2020", "10/03/2021", "C13");
//            insertRental(statement, "james", "06/01/2021", "31/02/2022", "C21");

            ResultSet resultSet = statement.executeQuery("select * from " + RENTAL_TABLE);
            statement.execute("select " + ID_CODE + " from " + TABLE_CAR + " inner join " +
                    RENTAL_TABLE + " on " + TABLE_CAR + "." + ID_CODE + " = " + RENTAL_TABLE + "." + ID_CODE1 + ";");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(CLIENT_NAME) + " " + resultSet.getString(START_DATE) + " "
                        + resultSet.getString(END_DATE) + " " + resultSet.getString(ID_CODE));
            }
            statement.close();
            connection.close();
        } catch (SQLException exception) {
            System.out.println("Error: can't connect to the database " + exception.getMessage());
            exception.printStackTrace();
        }


    }
}
