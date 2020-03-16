import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class create {
    private static String database = "jdbc:mysql://localhost:3306/javacombo" + "?serverTimezone=UTC";
    private static String user = "Student";
    private static String pass = "";
    private static ArrayList < String > namns = new ArrayList < String > ();
    
    static void createCombaten(ArrayList < Combaten > list) {

        Scanner scan = new Scanner(System.in);
        if(!list.isEmpty())
        	list.remove(list.size()-1);
        
        while (true) {
            Combaten c = new Combaten();
            System.out.println("skriv in önskad namn");
            c.setName(scan.nextLine());
            c.createstats();
            c.setGenderInConsole(scan);
            System.out.println("vill du avsluta j=ja");
            list.add(c);

            if (scan.next().equalsIgnoreCase("j")) {
                break;
            }
        }


    }

    static void chooseplayer(ArrayList < Combaten > list) {
        NameList(list);
        int i = 0;
        for (String namn: namns)
            System.out.println(namn);
        System.out.println("vem vill du vara av dessa karaktärer");
        @SuppressWarnings("resource")
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
    static void getSqlCombatenList(ArrayList < Combaten > list, int size) throws SQLException {
    	namns.add(" ");
        if(!list.isEmpty()) {
        	NameList(list);
        }
        boolean noreapet=true;
        PreparedStatement myStm;
        myStm = getmysqlconnection().prepareStatement("select * from fighter");
		
        ResultSet myres = myStm.executeQuery();
        while (myres.next()) {
            Combaten c = new Combaten();
            c.setstats(myres.getInt("Attack"), myres.getInt("Defense"), myres.getInt("Special"));
            c.setName(myres.getString("Nickname"));
            c.setGender(myres.getBoolean("gender"));
            for(String namn : namns) {
            	if(myres.getString("Nickname").equalsIgnoreCase(namn))
            		noreapet=false;
            	}
            if(noreapet) {
            	list.add(c);
            	}
            else
            	noreapet=true;

        }        
        	int erase=1;
            Collections.shuffle(list);
            //tar bort alla sista element i listan tills den är rätt storlek
            while(size!=list.size() && size>2 && size<list.size()) {
            	if(list.get(list.size()-1).getplayer())
            		erase++;
            	
                list.remove( list.size() - erase );
                
            }
          
        }

    
    
    
    static void insertCombatenListTosql(ArrayList < Combaten > list) throws SQLException {

        System.out.println("ansluten till databasen\r");

        databaseNameList();

        boolean b;
        for (Combaten
            var: list) {
            b = false;                 
            PreparedStatement myStm;
				myStm = getmysqlconnection().prepareStatement("insert into Fighter (Nickname, Attack,Defense,Special,Gender)" +
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
                , var.getGender());

            for (String n: namns) {
                if (var.getName()
                    .equalsIgnoreCase(n))
                    b = true;

            }
            if (!b) {
                myStm.executeUpdate();
                System.out.println("fighter har blivit skapad");
            }

        
        getmysqlconnection().close();
        
    }}
    public static Connection getmysqlconnection() {
    	Connection mycon = null;
        try {
			mycon = DriverManager.getConnection(database, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return mycon;
    }
    static void NameList(ArrayList<Combaten> list){
    	namns.clear();
        for(int c=0;c<list.size();c++) {
        	namns.add(list.get(c).getName());
        } 
    }
    static void databaseNameList() throws SQLException{
    	namns.clear();
    	PreparedStatement myStm;
	
			myStm = getmysqlconnection().prepareStatement("select * from fighter");
		
        ResultSet myres = myStm.executeQuery();
        while (myres.next()) {
            namns.add(myres.getString("Nickname"));
        }
        getmysqlconnection().close();
		
    }
}