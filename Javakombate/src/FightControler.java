import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
/*för att se till att jframe inte fryser 
 * måste du skriva om koden och lägga 
 * allting jframe i controller klassen*/

import javazoom.jl.decoder.JavaLayerException;


public class FightControler {
    public ArrayList < Music > ost = new ArrayList < Music > ();
    public GridBagConstraints gbc = new GridBagConstraints();
    public JFrame frame = new JFrame();
    private Fightdata Back;
    private FightView front;
    public int first;//fghjk
    public int second;
    public int phase = 0;
    private ArrayList < Combaten > tour = new ArrayList < Combaten > ();

    public FightControler(ArrayList < Combaten > list) {
        this.front = FightView.getInstance();
        this.Back =  Fightdata.getInstance();
        tour = list;
        Music t1 = new Music();
        ost.add(t1);
        frame.setLayout(new GridBagLayout());
    }
    public void chooseFighter() {
        int remember = 0;
        int match = 1;
        int[] turns = new int[2];
        turns[0] = 1;
        turns[1] = 0;
        int c = 0;   
        front.createTournementName();
        //off finns här så att det alltid blir två olika fighters som strider mot varandra och se till att det inte blir någon upprepning
        boolean off = false;
        //jag skickar en array för att både kolla om listan kan delas med upphöjt med två och kolla hur många gånger tourneringen ska upprepas
        sizaeCheck(tour, turns);
        if (turns[1] == 1)
            System.out.println("the combatens list must be dividable with two this one is not\nno fight ");
        else {
            while (turns[0] > c) {
                for (int counter = 0; counter < tour.size(); counter++) {
                    if (tour.get(counter).isStatus() && tour.get(remember).isStatus() && off) {
                    	//sätter in placeringen av både stridarna i en int värde för att lättare komma ihåg 
                    	second = counter;
                        first = remember;
                        tour.get(first).setLife(100);
                        tour.get(second).setLife(100);
                        
                        try {
                            front.setMatch(c, match);
                            startFight();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        off = false;
                        match++;

                    } else if (tour.get(counter).isStatus()) {
                        remember = counter;
                        off = true;
                    }
                }
                c++;
                match=1;
            }
            System.out.println("Tackar för att du spelar och ser fram emot vad mera saker ni tycker jag ska lägga till");
            System.exit(0);
        }

    }
 
    private void songStop() throws JavaLayerException {
        ost.get(0).stopsong();
        ost.remove(0);
        Music t2 = new Music();
        ost.add(t2);
    }
    
    private void startFight() throws IOException, InterruptedException {
        int turn = 0;
        if (tour.get(first)
            .getplayer()) {
            updateFram();
            ost.get(0).start();
            //pausa threaden tills spelare skriver något
            Back.waiting();
        } else if (tour.get(second)
            .getplayer()) {
            switchingplace();
            updateFram();
            ost.get(0).start();
            //pausa threaden tills spelare skriver något
            Back.waiting();
        } else {
            while (tour.get(first)
                .isStatus() && tour.get(second)
                .isStatus() && turn <= 100) {
                System.out.println("\t\t Turn " + turn);
                Back.contact = false;
                Back.chooseMove(tour.get(first));
                Back.chooseMove(tour.get(second));
                Back.contact(tour.get(first), tour.get(second));
                Back.contact(tour.get(second), tour.get(first));
                Back.checkLife(tour.get(first), tour.get(second));
                turn++;

            }
        }



    }

    private void switchingplace() {
        int temp;
        temp = first;
        first = second;
        second = temp;
    }

    public FightView getFront() {
        return front;
    }

    public Fightdata getBack() {
        return Back;
    }

    private void Displayfight() throws IOException {
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        JRadioButton attack = new JRadioButton("    1 : Attack");

        JRadioButton defend = new JRadioButton("    2 : Defend");

        JRadioButton charge = new JRadioButton("    3 : Charge ");

        JRadioButton read = new JRadioButton("    4 : Read    ");
       
        //Groupera knapparna så att bara en kan bli tryckt i taget
        ButtonGroup movemeny = new ButtonGroup();
        movemeny.add(attack);
        movemeny.add(defend);
        movemeny.add(charge);
        movemeny.add(read);

        JButton next = new JButton("Nästa omgång");

        JLabel statsmeny = new JLabel("<Html>Life : " + tour.get(first)
            .getLife() + "<br>Attack : " + tour.get(first)
            .getAttack() + "<br>Charge : " + tour.get(first)
            .getCharge() + "<br>Defense : " + tour.get(first)
            .getDefense() + "</html>");
        JLabel opponenetstats = new JLabel("Life : " + tour.get(second)
            .getLife());

        JLabel turninfo = new JLabel("<Html>" + Back.ult + "<br>" + Back.at + "</html>");
        JLabel meny = new JLabel("vilket move vill du använda");
        BufferedImage sceneimg = front.combaineImges(front.getImg(front.getBackground()), front.getImg(front.setFighterState(tour.get(second).getMove(),front.getFighterTwo())), front.getImg(front.setFighterState(tour.get(first).getMove(),front.getFighterOne())));
        JLabel matchinfo = new JLabel("<HTML>"+front.matchname+"<br>"+tour.get(first).getName()+" vs "+tour.get(second).getName()+"</HTML>");
        JLabel scene = new JLabel(new ImageIcon(sceneimg));
        statsmeny.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statsmeny.setFont(statsmeny.getFont().deriveFont(20.0f));
        opponenetstats.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opponenetstats.setFont(statsmeny.getFont().deriveFont(20.0f));
        matchinfo.setFont(matchinfo.getFont().deriveFont(15.0f));
        turninfo.setFont(turninfo.getFont().deriveFont(15.0f));
        
        gbc.ipady = 0;
        gbc.gridwidth = 3;
        gbc.gridx = 6;
        gbc.gridy = 0;
        frame.add(matchinfo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(statsmeny, gbc);
        gbc.gridx = 20;
        frame.add(opponenetstats, gbc);
        gbc.gridx = 6; 
        frame.add(turninfo, gbc);
        frame.add(scene, gbc);
        gbc.gridy = 2;
        frame.add(meny, gbc);
        gbc.gridy = 3;

        frame.add(attack, gbc);

        gbc.gridy = 4;

        frame.add(defend, gbc);
        gbc.gridy = 5;

        frame.add(charge, gbc);
        gbc.gridy = 6;
        frame.add(read, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 6;
        gbc.gridy = 7;
        frame.add(next, gbc);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        next.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (attack.isSelected()) {
                    tour.get(first)
                        .setMove(moves.Attack);
                } else if (defend.isSelected()) {
                    tour.get(first)
                        .setMove(moves.Defend);
                } else if (charge.isSelected()) {
                    tour.get(first)
                        .setMove(moves.charge);
                } else if (read.isSelected()) {
                    tour.get(first)
                        .setMove(moves.Read);
                }
                try {
                	moveHit();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    private void moveHit() throws IOException, InterruptedException {
        int currentlife1, currentlife2;
        currentlife1 = tour.get(first)
            .getLife();
        currentlife2 = tour.get(second)
            .getLife();

        Back.contact = false;
        Back.chooseMove(tour.get(second));
        Back.contact(tour.get(first), tour.get(second));
        Back.ult = front.result(Back.res, tour.get(second), tour.get(first), currentlife1);
        Back.contact(tour.get(second), tour.get(first));
        Back.checkLife(tour.get(first), tour.get(second));
        Back.at = front.result(Back.res, tour.get(first), tour.get(second), currentlife2);
        Back.contact = false;
        phase = 0;
        if (!tour.get(first)
            .isStatus()) {
            phase = 1;
        } else if (!tour.get(second)
            .isStatus()) {
            phase = 2;
        }

        updateFram();
    }
    private void updateFram() throws IOException, InterruptedException {
    	//används för att berätta att ta bort allt från jframe och sen att du ska lägga till någotnytt i jframe
        frame.getContentPane()
            .removeAll();
        frame.getContentPane()
            .repaint();
        switch (phase) {
        case 0: 
        	Displayfight();
        	break;
        case 1:
            victoryScreen(tour.get(second), tour.get(first));
            break;
        case 2:
            victoryScreen(tour.get(first), tour.get(second));
        	break;
        }
    }
    
    private void sizaeCheck(ArrayList < Combaten > list, int[] turns) {
        int n = list.size();
        while (n % 2 == 0) {
            n /= 2;
            turns[0] = turns[0] + 1;
        }

        if (n == 0 || n != 1)
            turns[1] = 1;
        else
            turns[1] = 0;
    }
    
    private void victoryScreen(Combaten victor, Combaten loser) throws IOException {
        String victorymessage = " \t \t \t " + front.matchname + " Avslutad segrar är " + victor.getName() + " kom tillbaka till nästa match i " + front.getTournementName();
        BufferedImage victoryscreen = front.combaineImges(front.getImg(front.getBackground()), front.getImg(front.setFighterState(tour.get(second).getMove(),front.getFighterTwo())),
        		front.getImg(front.setFighterState(tour.get(first).getMove(),front.getFighterOne())));
        JLabel scene = new JLabel(new ImageIcon(victoryscreen));
        JButton endmatch = new JButton("next");
        victorymessage = victorymessage.replaceAll("\t", "           ");
        JLabel victory = new JLabel(victorymessage);
        victory.setFont(victory.getFont().deriveFont(20.0f));
        Back.ult=" ";
        Back.at= " ";
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(scene, gbc);
        gbc.gridy = 1;
        frame.add(victory, gbc);
        gbc.gridy = 2;

        frame.add(endmatch, gbc);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endmatch.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                phase = 0;
                System.out.println("tryck enter för att starta nästa match");
                try {
                    songStop();
                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                } finally {
                    frame.dispose();
                }
            }
        });
    }
}
