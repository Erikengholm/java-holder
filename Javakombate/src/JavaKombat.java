import java.util.Scanner;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class Music extends Thread{
private Player p;
public void run(){
	try {
		FileInputStream fis = new FileInputStream("octo.mp3");
		p = new Player(fis);
		while(true) {
		p.play();}
	}catch (Exception e) {
		} 
		  }

public void stopsong() throws JavaLayerException {
	p.close();
}
}
public class JavaKombat extends create  {
	public static void main( String[] args ) throws SQLException, IOException, JavaLayerException, InterruptedException {
		boolean loki=true;
		ArrayList<Combaten> list = new ArrayList<Combaten>();	
		Scanner scan = new Scanner(System.in);
		while(loki) {

		System.out.println("vad vill du g�ra "
				+ "\n1 = skapa en karakt�r "
				+ "\n2 = h�mta alla karakt�rena fr�n data listan"
				+ "\n3 = l�gg till alla nya karakt�rer i listan till databasen"	
				+ "\n4 = starta en fight"
				+ "\n5 = v�lj karakt�r");
		switch(scan.nextInt()) {
			case 1:
				createCombaten(list);
				break;
			case 2:		
				getSqlCombatenList(list);
				break;	
			case 3:
				insertCombatenListTosql(list);
				break;
			case 4:
				startfight(list);
				break;
			case 5:
				chooseplayer(list);
				break;
			default:
				loki=false;	
				scan.close();
				break;
		}}
	}

	private static void startfight(ArrayList<Combaten> list) {
		
		FightControler FC= new FightControler(list);
		FC.chooseFighter();
	}

}