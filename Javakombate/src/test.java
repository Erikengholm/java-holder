import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import java.util.Random;

class test {

	@Test
	void teststats() {
		Combaten c = new Combaten();
		int hundra =0;
		int femtio;
		boolean b=true;
		while (hundra!=1000) {
		c.createstats();
		femtio=c.getAttack()+c.getSpec()+c.getDefense();
		if(femtio !=150) {
			b=false;
			System.out.println(femtio);
		}
		hundra++;
		}
    	assertTrue(b);
 
	}
	@SuppressWarnings("static-access")
	@Test
	void testlistinsert() throws SQLException {
		ArrayList<Combaten> list = new ArrayList<Combaten>();
		JavaKombat jk = new JavaKombat();
		jk.getSqlCombatenList(list);
		assertFalse(list.isEmpty());
	}

	@Test
	void testsetround() {
		int r=1,m=1;
		FightView FW = new FightView();
		FW.setMatch(r, m);
		r++;
		FW.setMatch(r, m);
		assertEquals("runda 2 Match number 1",FW.matchname);
	}
	@Test
	void testgetback() {
		FightView FW = new FightView();
		String background=FW.getBackground();
		assertEquals("images/desert.jpg",background);
	}
	@Test
	void testsetfighterone() {
		boolean b=true;
		moves[] test = {moves.Attack,moves.charge,moves.Defend};
		int move; 
		Random r = new Random();
		int i=0;
		FightView FW = new FightView();//"images/Alfyn/attack.png",FW.getFighterone()
		while(i<100) {
			move=r.nextInt(2);
			FW.setFighterState(test[move],FW.getFighterOne());
			if("images/Alfyn/read.png".equalsIgnoreCase(FW.setFighterState(test[move],FW.getFighterOne()))) {
			b=false;	
			}
			
			i++;
		}
		assertTrue(b);
	}
	@Test
	void testlife() {
		boolean b=true;
		ArrayList<Combaten> c=new ArrayList<Combaten>();
		Combaten te= new Combaten();
		Combaten st= new Combaten();
		c.add(te);
		c.add(st);
		int i=0;
		Fightdata FD= new Fightdata();
		Random r = new Random();
		while(i<100) {
			te.setLife(r.nextInt(1));
			st.setLife(r.nextInt(1));
			FD.checkLife(te, st);
			if(st.getLife()==0 && !st.isStatus() && te.getLife()!=0) {
				b=false;
			}
			st.setStatus(true);
			i++;
		}
		assertTrue(b);
	}

	
	

}
