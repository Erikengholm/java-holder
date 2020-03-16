import java.util.Scanner;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

class Music extends Thread{
public int moode=1;
private Player p;
public void run(){
	try {
		FileInputStream fis = situation();
		
		p = new Player(fis);
		while(true) {
		p.play();}
	}catch (Exception e) {
		} 
		  }
private FileInputStream situation() throws FileNotFoundException {
	switch(moode) {
		case 1:
				return new FileInputStream("Music/octo.mp3");
		case 2:
				return new FileInputStream("Music/bravely.mp3");
		case 3:
				return new FileInputStream("Music/Final.mp3");
		case 4:
				return new FileInputStream("Music/assassin.mp3");
		default:
				return new FileInputStream("Music/victory.mp3");
		}
}

public void setmoode(int i) {
	moode=i;
}

public void stopsong() throws JavaLayerException {
	p.close();
}
}
public class JavaKombat extends create  {
	private static Scanner scan = new Scanner(System.in);

	public static void main( String[] args ){
		boolean loki=true;
		ArrayList<Combaten> list = new ArrayList<Combaten>();
		while(loki) {
			
		System.out.println("vad vill du g�ra "
				+ "\n\n1 = skapa en karakt�r "
				+ "\n\n2 = h�mta alla karakt�rena\n fr�n data listan"
				+ "\n\n3 = l�gg till alla nya karakt�rer\n i listan till databasen"	
				+ "\n\n4 = starta en fight"
				+ "\n\n5 = v�lj karakt�r"
				+ "\n\n6 = t�m listan"
				+ "\n\n7 = avsluta spelet");
		switch(scan.nextInt()) {
			case 1:
				createCombaten(list);
				break;
			case 2:
				option(list);
				break;	
			case 3:
			try {
				insertCombatenListTosql(list);
			} catch (SQLException e) {
				e.printStackTrace();
			}
				break;
			case 4:
				startfight(list);
				break;
			case 5:
				chooseplayer(list);
				break;
			case 6:
				list.clear();
				System.out.println("Listan har nu blivit t�md");
				break;
			default:
				loki=false;	
				scan.close();
				break;
		}}
	}

	private static void startfight(ArrayList<Combaten> list) {
		FightControler FC = new FightControler(list);
		FC.chooseFighter();
		
	}

	private static void option(ArrayList<Combaten> list) {
		int size=0;
		System.out.println("skriv in �nskad nummer av stridare i konsolen \nM�ste vara delbart med tv�");
		size=scan.nextInt();
		if(sizaeCheck(size))
			try {
				getSqlCombatenList(list,size-list.size());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else
			System.out.println("numret du skrev in �r inte delbart med tv� skriv g�rna in ett nytt nummer");
} 
	protected static boolean sizaeCheck(int size) {
	        int n = size;
	        while (n % 2 == 0) {
	            n /= 2;
	        }

	        if (n == 0 || n != 1)
	        	return false;
	        else
	        	return true;
	    }
}