import CarRentalManagement.DataSource;

import java.sql.*;

public class JDBC extends DataSource {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = connection.createStatement();

            statement.execute("create table if not exists " + TABLE_CAR +
                    " (" + ID_CODE + " TEXT, " +
                    MODEL + " TEXT, " +
                    BRAND + " TEXT, " +
                    SEAT_NUMBER + " INTEGER, " +
                    LICENSE_PLATE + " TEXT" +
                    ")");

//            statement.execute("drop table " + RENTAL_TABLE + ";");

//            statement.execute("create table if not exists " + RENTAL_TABLE +
//                    " (" + ORDER_NUMBER + " INTEGER AUTOINCREMENT , "
//                    + CLIENT_NAME + " TEXT, " +
//                    START_DATE + " TEXT, " +
//                    END_DATE + " TEXT, " +
//                    ID_CODE1 + " INTEGER, " +
//                    "foreign key (" + ID_CODE1 + ") references " +
//                    TABLE_CAR + "(" + ID_CODE + "), " +
//                    "PRIMARY KEY (" + ORDER_NUMBER + ")" +
//                    ");");

            statement.execute("create table if not exists " + RENTAL_TABLE +
                    " (" + ORDER_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + CLIENT_NAME + " TEXT, " +
                    START_DATE + " TEXT, " +
                    END_DATE + " TEXT, " +
                    ID_CODE1 + " INTEGER, " +
                    "foreign key (" + ID_CODE1 + ") references " +
                    TABLE_CAR + "(" + ID_CODE + "));");

//            statement.execute("delete from " + RENTAL_TABLE);
//            statement.execute("ALTER TABLE " + RENTAL_TABLE + " AUTO_INCREMENT "  + "= 1;");


            ResultSet resultSet = statement.executeQuery("select * from " + TABLE_CAR);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(ID_CODE) + " " +
                        resultSet.getInt(SEAT_NUMBER) + " " +
                        resultSet.getString(LICENSE_PLATE));
            }
            statement.close();
            connection.close();


        } catch (SQLException e) {
            System.out.println("something went wrong " + e.getMessage());
            e.printStackTrace();
        }
    }
}
