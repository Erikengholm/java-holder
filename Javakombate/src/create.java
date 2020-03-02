import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class create {
    private static String database = "jdbc:mysql://localhost:3306/javacombo" + "?serverTimezone=UTC";
    private static String user = "Student";
    private static String pass = "";

    static void createCombaten(ArrayList < Combaten > list) {

        Scanner scan = new Scanner(System.in);
        while (true) {
            Combaten c = new Combaten();
            System.out.println("skriv in önskad namn");
            c.setName(scan.nextLine());
            c.createstats();
            c.setgender(scan);
            System.out.println("vill du avsluta j=ja");
            list.add(c);

            if (scan.next().equalsIgnoreCase("j")) {
                break;
            }
        }


    }
    @SuppressWarnings("resource")
    static void chooseplayer(ArrayList < Combaten > list) throws SQLException {
        ArrayList < String > namns = new ArrayList < String > ();
        databaseNameList(namns);
        int i = 0;
        for (String namn: namns)
            System.out.println(namn);
        System.out.println("vem vill du vara av dessa karaktärer");
        Scanner scan = new Scanner(System.in);
        String choosenone = scan.next();
        for (Combaten c: list) {
            if (c.getName()
                .equalsIgnoreCase(choosenone)) {
                c.setplayer(true);
                i++;
            }
        }
        System.out.println("det finns just nu " + i + " spelare");
    }
    static void getSqlCombatenList(ArrayList < Combaten > list) throws SQLException {
        Connection mycon = null;
        PreparedStatement myStm = null;

        mycon = DriverManager.getConnection(database, user, pass);
        myStm = mycon.prepareStatement("select * from fighter");
        ResultSet myres = myStm.executeQuery();
        while (myres.next()) {
            System.out.println("Namn : " + myres.getString("Nickname") + " \nAttack : " + myres.getString("Attack") + "\nDefense : " + myres.getInt("Defense") + "\ncharge : " + myres.getInt("Special"));
            System.out.println("-----------------------------------\n");
            Combaten c = new Combaten();
            c.setstats(myres.getInt("Attack"), myres.getInt("Defense"), myres.getInt("Special"));
            c.setName(myres.getString("Nickname"));
            c.setKön(myres.getBoolean("gender"));
            list.add(c);
        }

    }

    static void insertCombatenListTosql(ArrayList < Combaten > list) throws SQLException {

        Connection mycon = null;
        PreparedStatement myStm = null;

        mycon = DriverManager.getConnection(database, user, pass);
        System.out.println("ansluten till databasen\r");

        ArrayList < String > namns = new ArrayList < String > ();
        databaseNameList(namns);

        boolean b;
        for (Combaten
            var: list) {
            b = false;
            myStm = mycon.prepareStatement("insert into Fighter (Nickname, Attack,Defense,Special,Gender)" +
                "values ( ?,?,?,?,?)");

            //jag sätter strängerna där ? är detta är en inbygdd method i java

            myStm.setString(1
                , var.getName());
            myStm.setInt(2
                , var.getAttack());
            myStm.setInt(3
                , var.getDefense());
            myStm.setInt(4
                , var.getSpec());
            myStm.setBoolean(5
                , var.isKön());

            for (String n: namns) {
                if (var.getName()
                    .equalsIgnoreCase(n))
                    b = true;

            }
            if (!b) {
                myStm.executeUpdate();
                System.out.println("fighter har blivit skapad");
            }

        }
        mycon.close();
    }

    static void databaseNameList(ArrayList < String > namns) throws SQLException {
        Connection mycon = null;
        PreparedStatement myStm = null;

        mycon = DriverManager.getConnection(database, user, pass);
        myStm = mycon.prepareStatement("select * from fighter");
        ResultSet myres = myStm.executeQuery();
        while (myres.next()) {
            namns.add(myres.getString("Nickname"));
        }
        mycon.close();
    }
}