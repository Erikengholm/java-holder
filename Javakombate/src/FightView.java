import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class FightView {
    private static FightView ViewIN = null;
    public static int distance = 0;
    public static int distance2 = 0;
    public String matchname;
    private String tournementname;
    public JFrame frame = new JFrame();
    private String fighterone = "images/Alfyn";
    private String fightertwo = "images/Ophilia";
    private String background = "images/desert.jpg";
    
    public static FightView getInstance() {
        if (ViewIN == null) {
            ViewIN = new FightView();
            }

        return ViewIN;
    }

    public void characterSelect() {}
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
            result = "medans " + two.getName() + " Attackerade så satte sig " + one.getName() + " i försvars läge";
            break;
        case 4:
            result = "medans " + one.getName() + " inte gjorde något så laddade " + two.getName() + " upp för sin attack";
            break;
        case 5:
            result = "medans " + one.getName() + " laddade upp sin attack så Attackerade " + two.getName();
            break;
        case 6:
            result = " ";
            break;
        default:
            result = two.getName() + " bettalde för sina misstag och förlorade " + (currentlife - two.getLife()) + " hp";
            break;
        }
        return result;

    }
    private String doubleMove(Combaten one, Combaten two) {
        String result = "";
        switch (one.getMove()) {
        case Attack:
            result = "båda " + one.getName() + " och " + two.getName() + " går på en hård offensiv";
            break;
        case Defend:
            result = "inget hände då både " + one.getName() + " och " + two.getName() + " började försvara";
            break;
        case charge:
            result = "både " + one.getName() + " och " + two.getName() + " chargar för en jätte move";
            break;
        default:
            result = "nåt gick fel för både spelarna använde sig av read";
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
            , "döds"
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
        g2.drawImage(img2, null, img1.getWidth() - (220 + distance) - 85, 200);
        g2.drawImage(img3, img3.getHeight() + (220+distance2) - 40, 200, -img3.getWidth(), img3.getHeight(), null);
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