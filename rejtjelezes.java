import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class rejtjelezes {
	private ArrayList<String> words = new ArrayList<>();
	
	public rejtjelezes() {
		betolt("words.txt");
		System.out.println("Szavak a fájlban: "+words.size());
		String uzenet = "helloworld";
        String kulcs = "abcdefgijkl";
        //Teszt adatok
        
        String kodoltUzenet = kodolas(uzenet, kulcs);
        System.out.println("A kódolt teszt üzenet: " + kodoltUzenet);
        
        String dekodoltUzenet = dekodolas(kodoltUzenet, kulcs);
        System.out.println("A dekódolt teszt üzenet: " + dekodoltUzenet);
        
     // Két rejtjelezett üzenet
        String kodoltUzenet1 = "vnfzcmknscwtgxy";
        String kodoltUzenet2 = "ifszwwzeonzfva ";
        
        List<String> lehetKulcsok = lehetKulcsokKeres(kodoltUzenet1, kodoltUzenet2, words);
        
        System.out.println("Lehetséges kulcsok:");
        for (String kulcsok : lehetKulcsok) {
            System.out.println(kulcsok);
            System.out.println("Kulcs: " + kulcsok);
            String dekodoltUzenet1 = dekodolas(kodoltUzenet1, kulcsok);
            String dekodoltUzenet2 = dekodolas(kodoltUzenet2, kulcsok);
            System.out.println("Dekódolt üzenet 1: " + dekodoltUzenet1);
            System.out.println("Dekódolt üzenet 2: " + dekodoltUzenet2);
        }
	}

	public static void main(String[] args) {
		new rejtjelezes();
	}
	
	private String kodolas(String uzenet, String kulcs) {
		String kodoltUzenet = "";
		if(kulcs.length() >= uzenet.length()) { //ha a kulcs minimum olyan hosszú mint az üzenet
			for(int i = 0 ; i < uzenet.length() ; i++) { //végig megy az üzeneten karakterenként
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
			kodoltUzenet = "A kulcs hossza rövidebb mint az üzeneté.";
		}
		return kodoltUzenet;
	}
	
	private String dekodolas(String kodoltUzenet, String kulcs) {
		String dekodoltUzenet = "";
		if(kulcs.length() >= kodoltUzenet.length() && !kodoltUzenet.contains("rövidebb")) { //ha a kulcs minimum olyan hosszú mint a kodolt üzenet.
			for(int i = 0 ; i < kodoltUzenet.length() ; i++) {
				char kodoltChar = kodoltUzenet.charAt(i);
				char kulcsChar = kulcs.charAt(i % kulcs.length());
				
				int kodoltKod = charKod(kodoltChar);
				int kulcsKod = charKod(kulcsChar);
				
				int dekodoltKod = (kodoltKod - kulcsKod + 27) % 27;
				char dekodoltChar = kodChar(dekodoltKod);
				
				dekodoltUzenet += dekodoltChar;
			}
		}else {
			dekodoltUzenet = "A kulcs hossza rövidebb mint a kódolt üzeneté.";
		}
		return dekodoltUzenet;
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
		Scanner be = null;
		try {
			be = new Scanner(rejtjelezes.class.getResourceAsStream(fajlnev));
			String next ;
			while(be.hasNextLine()) {
				next = be.nextLine();
				words.add(next.trim().toLowerCase());
				//System.out.println(next);
			}
		}catch(Exception e) {
			System.err.println(e.toString());
		}//Betölti a szavakat a words.txt-ből és listába teszi őket figyelve a hibákra pl.(esetlegesen hiányzó fájl. FileNotFoundException) etc..
	}
	
	private List<String> lehetKulcsokKeres(String kodoltUzenet1, String kodoltUzenet2, List<String> words) {
	    List<String> lehetKulcsok = new ArrayList<>();
	    
	    for (String word : words) {
	        if (word.length() <= kodoltUzenet1.length()) {
	            String reszKulcs = reszKulcsKeres(kodoltUzenet1, word);
	            if (reszKulcs != null) {
	                String teljesKulcs = teljesKulcsKeszit(reszKulcs, kodoltUzenet1.length());
	                String lehetDekodolt = dekodolReszKulccsal(kodoltUzenet2, teljesKulcs);
	                if (vanSzavak(lehetDekodolt, words)) {
	                    lehetKulcsok.add(teljesKulcs);
	                }
	            }
	        }
	    }
	    
	    return lehetKulcsok;
	}

	private String reszKulcsKeres(String kodoltUzenet, String szo) {
	    StringBuilder reszKulcs = new StringBuilder();
	    for (int i = 0; i < szo.length(); i++) {
	        int kodoltKod = charKod(kodoltUzenet.charAt(i));
	        int szoKod = charKod(szo.charAt(i));
	        int kulcsKod = (kodoltKod - szoKod + 27) % 27;
	        reszKulcs.append(kodChar(kulcsKod));
	    }
	    return reszKulcs.toString();
	}

	private String teljesKulcsKeszit(String reszKulcs, int hossz) {
	    StringBuilder teljesKulcs = new StringBuilder();
	    for (int i = 0; i < hossz; i++) {
	        teljesKulcs.append(reszKulcs.charAt(i % reszKulcs.length()));
	    }
	    return teljesKulcs.toString();
	}

	private String dekodolReszKulccsal(String kodoltUzenet, String teljesKulcs) {
	    StringBuilder dekodoltUzenet = new StringBuilder();
	    for (int i = 0; i < kodoltUzenet.length(); i++) {
	        int kodoltKod = charKod(kodoltUzenet.charAt(i));
	        int kulcsKod = charKod(teljesKulcs.charAt(i));
	        int dekodoltKod = (kodoltKod - kulcsKod + 27) % 27;
	        dekodoltUzenet.append(kodChar(dekodoltKod));
	    }
	    return dekodoltUzenet.toString();
	}

	private boolean vanSzavak(String uzenet, List<String> words) {
	    String[] szavak = uzenet.split(" ");
	    for (String szo : szavak) {
	        if (!words.contains(szo.trim())) {
	            return false;
	        }
	    }
	    return true;
	}
}
