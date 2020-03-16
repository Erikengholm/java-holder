import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class FightView {
    private static FightView ViewIN = null;
    
    // dessa tv� string anv�nds f�r att f� omg�ngs namn och tournerings namn p� spelet
    public String matchname;
    private String tournementname;
    //dessa string anv�nds f�r att kallas p� f�r att ber�tta f�r jframe vilken bild som ska anv�ndas f�r karakt�r ett och tv�r
    private String fighterone = "";
    private String fightertwo = "";
    //ber�ttar f�r jframe villken background denna match ska anv�nda (har bara en tyv�rr)
    private String background = "images/desert.jpg";
    
    public static FightView getInstance() {
        if (ViewIN == null) {
            ViewIN = new FightView();
            }

        return ViewIN;
    }

    public void AIcharecter() {
    	String chare="images/Ophilia";
    	Random r= new Random();
    	;
    	switch(r.nextInt(2)) {
    	case 0:
    		chare="images/Ophilia";
    		break;
    	case 1:
    		chare="images/Cyrus";
    		break;
    	case 2:
    		chare="images/Alfyn";
    		break;
    	}
    	
    	setFighterTwo(chare);
    	}
    public String result(int res, Combaten one, Combaten two, int currentlife) {
        String result = " ";

        switch (res) {
        case 1:
            result = read(one);
            break;
        case 2:
            result = doubleMove(one, two);
            break;
        case 3:
            result = "medans " + two.getName() + " Attackerade s� satte sig " + one.getName() + " i f�rsvars l�ge";
            break;
        case 4:
            result = "medans " + one.getName() + " inte gjorde n�got s� laddade " + two.getName() + " upp f�r sin attack";
            break;
        case 5:
            result = "medans " + one.getName() + " laddade upp sin attack s� Attackerade " + two.getName();
            break;
        case 6:
            result = " ";
            break;
        default:
            result = two.getName() + " bettalde f�r sina misstag och f�rlorade " + (currentlife - two.getLife()) + " hp";
            break;
        }
        return result;

    }
    private String doubleMove(Combaten one, Combaten two) {
        String result = "";
        switch (one.getMove()) {
        case Attack:
            result = "b�da " + one.getName() + " och " + two.getName() + " g�r p� en h�rd offensiv";
            break;
        case Defend:
            result = "inget h�nde d� b�de " + one.getName() + " och " + two.getName() + " b�rjade f�rsvara";
            break;
        case charge:
            result = "b�de " + one.getName() + " och " + two.getName() + " chargar f�r en j�tte move";
            break;
        default:
            result = "n�t gick fel f�r b�de spelarna anv�nde sig av read";
            break;
        }
        return result;
    }

    public void setMatch(int round, int match) {
        this.matchname = "runda " + (round+1) + " Match number " + match;
    }

    public String getTournementName() {
        return tournementname;
    }

    public void createTournementName() {
        Random r = new Random();
        String[] first = {
            "eld"
            , "vatten"
            , "is"
            , "jord"
            , "blixt"
            , "luft"
            , "d�ds"
            , "BOB"
        };
        String[] second = {
            "slagen"
            , "sparken"
            , "andetaget"
            , "ljudet"
            , "stirringen"
            , "sovandet"
        };
        this.tournementname = "tourneringen av " + first[r.nextInt(first.length)] + " " + second[r.nextInt(second.length)];
    }




    public BufferedImage getImg(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        return img;
    }

    public BufferedImage combaineImges(BufferedImage img1, BufferedImage img2, BufferedImage img3) {
        int offset = 0;
        int wid = img1.getWidth();
        int height = Math.max(img1.getHeight(), img2.getHeight()) + offset - 15;
        BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, wid, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth() - 220 - 85, 200);
        g2.drawImage(img3, img3.getHeight() + 220 - 40, 200, -img3.getWidth(), img3.getHeight(), null);
        g2.dispose();
        return newImage;
    }

    public String getFighterTwo() {
        return fightertwo;
    }

    public void setFighterTwo(String fightertwo) {
        this.fightertwo = fightertwo;
    }

    public String getFighterOne() {
        return fighterone;
    }

    public void setFighterOne(String fighterone) {
        this.fighterone = fighterone;
    }

    public String getBackground() {
        return background;
    }

    public String setFighterState(moves i,String fighter) {
        String moode = fighter;
        switch (i) {
        case Attack:
            moode +="/attack.png";
            break;
        case Defend:
            moode += "/block.png";
            break;
        case charge:
            moode += "/charge.png";
            break;
        case Read:
            moode += "/read.png";
            break;
        default:
            moode += "/read.png";
            break;
        }
        return moode;
    }
    private String read(Combaten target) {
        Random r = new Random();
        String read = " ";
        switch (r.nextInt(target.getstatsarray()
            .length - 1)) {
        case 0:
            read = target.getName() + " har " + target.getAttack() + " i Attack";
            break;
        case 1:
            read = target.getName() + " har " + target.getDefense() + " i defense";
            break;
        case 2:
            read = target.getName() + " har " + target.getSpec() + " i charge ";
            break;
        }
        return read;
    }}