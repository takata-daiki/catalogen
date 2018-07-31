package bib;

import java.util.*;

public class Roman extends Buch implements Ausleihbar{

	public Roman() {
		// TODO Auto-generated constructor stub
	}
        public Roman(String invNummer)
        {
            super(invNummer);
        }
	public void entliehenAn(String name){
		Entleihung e = new Entleihung();
		e.setInvNummer(this.getInvNummer());
		e.setName(name);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 28);
		e.setRueckgabe(c);
		e.setVormerkung(false);
		Bibliothek b = getBibliothek();
		b.getEntleihungen().add(e);
	}
	public void verlaengereLeihfrist()throws NoExtensionException{
		Bibliothek b = getBibliothek();
		Entleihung e = b.findEntleihungInvNummer(this.getInvNummer());
		if (e != null) {
			if (!e.isVormerkung()){
				Calendar c = e.getRueckgabe();
				c.add(Calendar.DAY_OF_YEAR, 28);
			} else
				throw new NoExtensionException(this.getTitel());
		}
	}

	public void setzeVormerkung(){
		Bibliothek b = getBibliothek();
		Entleihung e = b.findEntleihungInvNummer(this.getInvNummer());
		if (e != null) {
			e.setVormerkung(true);
		}

	}
	public String toString() {
		String s = "";
		s= s.concat("Medium INVNr:"+ this.getInvNummer()+ "\n");
		s= s.concat("Medium Titel:"+ this.getTitel()+ "\n");
		s= s.concat("Medium Autor:"+ this.getAutor()+ "\n");
		return s;
	}

    public Medium getMedium() {
        return this;
    }

}
