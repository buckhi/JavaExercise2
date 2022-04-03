package CarRentalManagement;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static final String DB_NAME = "car_rental.db";
//    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\nbhai\\Desktop\\projects\\NguyenBacHaiExercise2\\" + DB_NAME;
        public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/machouse/IdeaProjects/JavaExercise2" + DB_NAME;

    public static final String RENTAL_TABLE = "RentalManagement";
    public static final String ORDER_NUMBER = "Number";
    public static final String CLIENT_NAME = "Fullname";
    public static final String START_DATE = "StartDate";
    public static final String END_DATE = "EndDate";
    public static final String ID_CODE1 = "IDC";


    public static final String TABLE_CAR = "car_rental";
    public static final String ID_CODE = "ID";
    public static final String BRAND = "Brand";
    public static final String MODEL = "Model";
    public static final String SEAT_NUMBER = "Seat";
    public static final String LICENSE_PLATE = "Plate";


    public static final String QUERY_RENTAL_INFO = "select " + TABLE_CAR + "." + ID_CODE + " from " + TABLE_CAR + " inner join " +
            RENTAL_TABLE + " on " + TABLE_CAR + "." + ID_CODE + " = " + RENTAL_TABLE + "." + ID_CODE1 + ";";

    private Connection connection;
    private PreparedStatement queryRentalInfo;


    public static void insertRental(Statement statement, String name, String startDate, String endDate, String ID) throws SQLException {
        statement.execute("insert into " + RENTAL_TABLE + " (" + CLIENT_NAME + ", " +
                START_DATE + ", " + END_DATE + ", " + ID_CODE + ") " +
                "values('" + name + "', '" + startDate + "', '" + endDate + "', '" + ID + "')");

    }

    public static void insertVehicle(Statement statement, String ID, String brand, String model, int seat, String licenePlate) throws SQLException {
        statement.execute("insert into " + TABLE_CAR + " (" + ID + ", " + BRAND + ", " + MODEL + ", " + SEAT_NUMBER +
                ", " + LICENSE_PLATE + ") " + "values('" + ID + "', '" + brand + "', '" + model + "', '" + seat + "', '" + licenePlate +
                "')");
    }







}
