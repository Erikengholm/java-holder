import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Fightdata {
 private static Fightdata FightIN = null;
 private static Scanner scan = new Scanner(System.in);
 //contact är till för om båda valde samma attack att det bara går igenom en gång
 public boolean contact = false;
 public int res;//res-ult-at är för att skicka in på fightview vilken text som ska skrivas ut
 public String ult = " ";
 public String at = " ";

 public static Fightdata getInstance() {
  if (FightIN == null)
   FightIN = new Fightdata();

  return FightIN;
 }
 protected int checkplayer(ArrayList<Combaten> list) {
		int postion=-1;
		for(int c=0;c<list.size();c++) {
			if(list.get(c).getplayer()) {
				postion=c;
				System.out.println("spelare hittad");
			}
				
		}
		return postion;
		
	}
 public void chooseMove(Combaten c) {
  if (c.getplayer()) {
   String move;
   boolean b;
   do {
    b = false;
    System.out.println("vad vill du göra \n1 Attack \n2 Defend \n3 charge \n4 Read");
    move = scan.next();
    move.toLowerCase();
    switch (move) {

     case "attack":
      c.setMove(moves.Attack);
      break;
     case "defend":
      c.setMove(moves.Defend);
      break;
     case "charge":
      c.setMove(moves.charge);
      break;
     case "read":
      c.setMove(moves.Read);
      break;
     default:
      System.out.println("inget move har blivit valt skriv igen");
      b = true;
    }
   } while (b);
  } else {
   aiMove(c);
  }


 }

 
 
 private void aiMove(Combaten c) {
  Random rand = new Random();
  int chanse;
  double move1 = (double) c.getAttack() / 150;
  double move2 = (double) c.getDefense() / 150;
  double fair;
  chanse = rand.nextInt(100);
  fair = (double) chanse / 100;
  if (fair < move1) {
   c.setMove(moves.Attack);
  } else if (move1 < fair && fair < (move2 + move1)) {
   c.setMove(moves.Defend);
  } else {
   c.setMove(moves.charge);
  }


 }

 public void contact(Combaten first, Combaten second) {
	 int result = 0;
  if (first.getMove() == moves.Read) {
   result=1;
  } else if (first.getMove() == second.getMove() && !contact) {//denna metod finns för att två attacker inte upprepas på varandra
   equal(first, second);
   result=2;
   contact = true;
  } else if (first.getMove() == moves.Attack && second.getMove() == moves.Defend) {
   attack(first, second, true);
   result=3;


  } else if (first.getMove() == moves.charge && (second.getMove() == moves.Defend || second.getMove() == moves.Read)) {
   charge(first, second, false);
   result=4;


  } else if (first.getMove() == moves.Attack && second.getMove() == moves.charge || second.getMove()== moves.Read) {
   attack(first, second, false);
   charge(second, first, true);
   result=5;
   
  }else if (contact){
	result=6;
  }else
	  result=0;

  res=result;
	
 }

 private void equal(Combaten first, Combaten second) {
  switch (first.getMove()) {
   case Attack:
    attack(first, second, false);
    attack(second, first, false);
    break;
   case Defend:
	   //försvarar två ska ingenting hända 
    break;
   case charge:
    charge(first, second, false);
    charge(second, second, false);
    break;
   default:
    break;
  }
 }
public void waiting(){    
	//denna funktion är för att få programet att vänta med backend tills front ende är färdig
	System.out.println("tryck enter för att forsätta");
    @SuppressWarnings("unused")
	String i=scan.nextLine();
}
 private void charge(Combaten user, Combaten victim, boolean counter) {
  if (counter) {
   //om någon attackerade denna omgång så kommer den person att förlora en fjärde del av sin charge
   user.setCharge(user.getCharge() - (user.getCharge() / 4));
  } else {
   user.setCharge(user.getCharge() + user.getSpec());
  }

 }

 private void attack(Combaten user, Combaten victim, boolean counter) {
  if (counter) {
   if (user.getAttack() + user.getCharge() <= victim.getDefense() + victim.getCharge()) {
    victim.setLife(victim.getLife() + 20);
   } else {
    
   }
  } else {
   victim.setLife(victim.getLife() - (user.getAttack() + user.getCharge()));
  }
  user.setCharge(0);
 }


 public void checkLife(Combaten first, Combaten second) {
  
   if (first.getLife() <= 0)
   first.setStatus(false);

  else if (second.getLife() <= 0)
   second.setStatus(false);

 }
}
    
