import java.util.Random;
import java.util.Scanner;

public class Combaten {

    private int charge = 0;
    private String name;
    private moves move = moves.Read;
    private boolean k�n;
    //boolean som ber�ttare om denna karakt�r lever eller ej
    private boolean status = true;
    private int life = 100;
    //boolean f�r att bed�mma om denna karakt�r �r en spelare ps kan bara finnas en spelare per omg�ng
    private boolean player = false;
    private int diffuculty = 1;
    //attack �r postion 0 ,defense �r postion nummer 1 och special �r postion nummer 2
    private int[] stats = new int[3];
    public String Character = " ";
    public int[] getstatsarray() {
        return stats;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAttack() {
        return stats[0];
    }


    public int getDefense() {
        return stats[1];
    }


    public int getSpec() {
        return stats[2];
    }
    public void setAttack(int attack) {
        this.stats[0] = attack;
    }


    public void setDefense(int defense) {
        this.stats[1] = defense;
    }


    public void setstats(int a, int b, int c) {
        stats[0] = a;
        stats[1] = b;
        stats[2] = c;

    }
    public moves getMove() {
        return move;
    }

    public void setMove(moves move) {
        this.move = move;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isK�n() {
        return k�n;
    }

    public void setK�n(boolean k�n) {
        this.k�n = k�n;
    }

    public void setgender(Scanner scan) {
        System.out.println("vilket k�n har du \n \n tryck 1 f�r kvinna \n tryck 2 f�r man");

        if (scan.nextInt() == 1)
            this.k�n = false;
        else
            this.k�n = true;
    }
    protected void createstats() {
        Random rand = new Random();
        int pos1, pos2, pos3;

        while (true) {
            pos1 = rand.nextInt(3);

            pos2 = rand.nextInt(3);

            pos3 = rand.nextInt(3);
            if (pos1 != pos2 && pos2 != pos3 && pos3 != pos1)
                break;
        }
        while ((stats[0] + stats[1] + stats[2] != 150)) {
            stats[pos1] = rand.nextInt(90);
            if ((150 - stats[pos1]) > 90)
                stats[pos2] = rand.nextInt(150 - stats[pos1]);
            else
                stats[pos2] = rand.nextInt(90);

            stats[pos3] = 150 - (stats[pos1] + stats[pos2]);
            if (stats[pos3] > 100) {
                int temp = stats[pos3] - 100;
                temp = temp / 2;
                stats[pos1] += temp;
                stats[pos2] += temp;
            }
        }
    }

    public void setplayer(boolean b) {
        this.player = b;
    }
    public boolean getplayer() {
        return this.player;
    }
    
    public int getCharge() {
        return charge;
    }
    public void setCharge(int charge) {
        this.charge = charge;
    }
	public int getDiffuculty() {
		return diffuculty;
	}
}