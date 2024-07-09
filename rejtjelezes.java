
public class rejtjelezes {
	
	public rejtjelezes() {
		String uzenet = "helloworld";
        String kulcs = "abcdefgijkl";
        //Teszt adatok
        
        String kodoltUzenet = kodolas(uzenet, kulcs);
        System.out.println("A kódolt teszt üzenet: " + kodoltUzenet);
	}

	public static void main(String[] args) {
		new rejtjelezes();
	}
	
	private boolean kulcsEll(String uzenet, String kulcs) {
		boolean eleg = true;
		if(kulcs.length() < uzenet.length()) { //kulcs hosszának ellenörzése
			eleg = false;
		}
		return eleg;
	}
	
	private String kodolas(String uzenet, String kulcs) {
		String kodoltUzenet = "";
		if(kulcsEll(uzenet, kulcs)) { //ha a kulcs minimum olyan hosszú mint az üzenet
			
			for(int i = 0;i < uzenet.length();i++) { //végig megy az üzeneten karakterenként
				char uzenetChar = uzenet.charAt(i);
				char kulcsChar = kulcs.charAt(i);
				//Az üzenet és a kulcs i. karakteréből változót csinál.
				
				int uzenetKod = charKod(uzenetChar);
				int kulcsKod = charKod(kulcsChar);
				//A karaktereket számmá alakitja.
				
				int kodoltKod = (uzenetKod + kulcsKod) % 27;
				char kodoltChar = kodChar(kodoltKod);
				//A két számmá alakitott karaktert összeadjuk és osztjuk 27-el.
				//Utána az eredményt számból karakterré alakitja.
				
				kodoltUzenet += kodoltChar;
				//A kodoltUzenet nevű változóhoz hozzáadjuk az új karaktert amiből egy kódolt üzenet lessz.
			}
		}else {
			kodoltUzenet = "A kulcs hossza rövidebb mint az üzeneté";
		}
		return kodoltUzenet;
	}
	
	private void dekodolas(String uzenet, String kulcs) {
		
	}
	
	private int charKod(char a) {
		if(a == ' ') return 26;
		else return a - 'a';
	}
	
	private char kodChar(int kod) {
		if(kod == 26) return ' ';
		else return (char)(kod + 'a');
	}
	
	private void betolt(String fajlnev) {
		
	}
}
