package CarRentalManagement;

import java.sql.*;

public class DataSource {
    public static final String DB_NAME = "car_rental.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\nbhai\\Desktop\\projects\\NguyenBacHaiExercise2\\" + DB_NAME;

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    public static final String TABLE_CAR = "car_rental";
    public static final String ID_CODE = "ID";
    public static final String ID_CODE1 = "IDC";
    public static final String BRAND = "Brand";
    public static final String MODEL = "Model";
    public static final String SEAT_NUMBER = "Seat";
    public static final String LICENSE_PLATE = "Plate";

    public static final String RENTAL_TABLE = "rentalManagement";
    public static final String CLIENT_NAME = "fullname";

    public static final String QUERY_RENTAL_INFO = "select " + TABLE_CAR + "." + ID_CODE + " from " + TABLE_CAR + " inner join " +
            RENTAL_TABLE + " on " + TABLE_CAR + "." + ID_CODE + " = " + RENTAL_TABLE + "." + ID_CODE1 + ";";

    private Connection connection;
    private PreparedStatement queryRentalInfo;

//    public boolean open() {
//        try {
//            connection = DriverManager.getConnection(CONNECTION_STRING);
//            queryRentalInfo = connection.prepareStatement(QUERY_RENTAL_INFO);
//            return true;
//        } catch (SQLException exception) {
//            System.out.println("couldn't connect to the data base " + exception.getMessage());
//            return false;
//        }
//    }
//
//    public void close() {
//        try {
//            if (queryRentalInfo != null) {
//                queryRentalInfo.close();
//            }
//        } catch (SQLException exception) {
//            System.out.println("couldn't close connection " + exception.getMessage());
//        }
//    }


    public static void insertRental(Statement statement, String name, String startDate, String endDate, String ID) throws SQLException {
        statement.execute("insert into " + RENTAL_TABLE + " (" + CLIENT_NAME + ", " +
                START_DATE + ", " + END_DATE + ", " + ID_CODE + ") " +
                "values('" + name + "', '" + startDate + "', '" + endDate + "', '" + ID +"')");

    }


}
