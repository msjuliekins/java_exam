import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Kort {
    protected int id;
    protected int serie;
    protected String tilstand;
    protected String spiller;
    protected String klubb;
    protected int sesonger;
    protected int kamper;

    public Kort(int id, int serie, String tilstand, String spiller, String klubb, int sesonger, int kamper) {
        this.id = id;
        this.serie = serie;
        this.tilstand = tilstand;
        this.spiller = spiller;
        this.klubb = klubb;
        this.sesonger = sesonger;
        this.kamper = kamper;
    }

    public boolean isMint() {
        return tilstand.equalsIgnoreCase("Mint");
    }

    public abstract void printInfo();
}

public class FotballKort extends Kort {
    private int seriescoringer;
    private int cupscoringer;

    public FotballKort(int id, int serie, String tilstand, String spiller, String klubb,
                       int sesonger, int kamper, int seriescoringer, int cupscoringer) {
        super(id, serie, tilstand, spiller, klubb, sesonger, kamper);
        this.seriescoringer = seriescoringer;
        this.cupscoringer = cupscoringer;
    }

    @Override
    public void printInfo() {
        System.out.println("-----");
        System.out.println(spiller + " - " + klubb);
        System.out.println("Serie: " + serie);
        System.out.println("Seasons: " + sesonger);
        System.out.println("Matches: " + kamper);
        System.out.println("Serie goals: " + seriescoringer);
        System.out.println("Cup goals: " + cupscoringer);
        System.out.println("Condition: " + tilstand);
    }
}


public class KortLoader {
    public List<Kort> loadFraFil(String filnavn) throws FileNotFoundException {
        List<Kort> kortListe = new ArrayList<>();
        Scanner input = new Scanner(new File(filnavn));

        while (input.hasNextLine()) {
            String linje = input.nextLine();
            if (linje.startsWith("Kort:")) {
                int antall = Integer.parseInt(input.nextLine());
                for (int i = 0; i < antall; i++) {
                    int id = Integer.parseInt(input.nextLine());
                    int serie = Integer.parseInt(input.nextLine());
                    String tilstand = input.nextLine();
                    String spiller = input.nextLine();
                    String klubb = input.nextLine();
                    int sesonger = Integer.parseInt(input.nextLine());
                    int kamper = Integer.parseInt(input.nextLine());
                    String sport = input.nextLine();

                    if (sport.equalsIgnoreCase("Fotball")) {
                        int serieGoals = Integer.parseInt(input.nextLine());
                        int cupGoals = Integer.parseInt(input.nextLine());
                        kortListe.add(new FotballKort(id, serie, tilstand, spiller, klubb, sesonger, kamper, serieGoals, cupGoals));
                    }
                    else if (sport.equalsIgnoreCase("Basketball")) {
                        int fg = Integer.parseInt(input.nextLine());
                        int ft = Integer.parseInt(input.nextLine());
                        double snitt = Double.parseDouble(input.nextLine());
                        kortListe.add(new BasketballKort(id, serie, tilstand, spiller, klubb, sesonger, kamper, fg, ft, snitt));
                    }
                    else if (sport.equalsIgnoreCase("Baseball")) {
                        int homeruns = Integer.parseInt(input.nextLine());
                        kortListe.add(new BaseballKort(id, serie, tilstand, spiller, klubb, sesonger, kamper, homeruns));
                    }
                    
                }
            } else {
                input.nextLine(); // hopp over samlerkortserie
            }
        }

        return kortListe;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        KortLoader loader = new KortLoader();
        List<Kort> kortSamling = new ArrayList<>();

        try {
            kortSamling = loader.loadFraFil("files/samlerkort.txt");
        } catch (Exception e) {
            System.out.println("Kunne ikke laste kort: " + e.getMessage());
            return;
        }

        int choice = 0;
        while (choice != 4) {
            System.out.println("""
                Welcome to your card sorting system!
                1. See all cards
                2. See number of cards
                3. See mint condition cards
                4. Exit
                Please enter your choice:
                """);

            choice = input.nextInt();

            switch (choice) {
                case 1 -> kortSamling.forEach(Kort::printInfo);
                case 2 -> System.out.println("Total cards: " + kortSamling.size());
                case 3 -> kortSamling.stream()
                        .filter(Kort::isMint)
                        .forEach(Kort::printInfo);
                case 4 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
public class BasketballKort extends Kort {
    private int fgPercent;
    private int ftPercent;
    private double poengsnitt;

    public BasketballKort(int id, int serie, String tilstand, String spiller, String klubb,
                          int sesonger, int kamper, int fgPercent, int ftPercent, double poengsnitt) {
        super(id, serie, tilstand, spiller, klubb, sesonger, kamper);
        this.fgPercent = fgPercent;
        this.ftPercent = ftPercent;
        this.poengsnitt = poengsnitt;
    }

    @Override
    public void printInfo() {
        System.out.println("-----");
        System.out.println(spiller + " - " + klubb);
        System.out.println("Serie: " + serie);
        System.out.println("Seasons: " + sesonger);
        System.out.println("Matches: " + kamper);
        System.out.println("FG%: " + fgPercent);
        System.out.println("FT%: " + ftPercent);
        System.out.println("Points per game: " + poengsnitt);
        System.out.println("Condition: " + tilstand);
    }
}
public class BaseballKort extends Kort {
    private int homeruns;

    public BaseballKort(int id, int serie, String tilstand, String spiller, String klubb,
                        int sesonger, int kamper, int homeruns) {
        super(id, serie, tilstand, spiller, klubb, sesonger, kamper);
        this.homeruns = homeruns;
    }

    @Override
    public void printInfo() {
        System.out.println("-----");
        System.out.println(spiller + " - " + klubb);
        System.out.println("Serie: " + serie);
        System.out.println("Seasons: " + sesonger);
        System.out.println("Matches: " + kamper);
        System.out.println("Homeruns: " + homeruns);
        System.out.println("Condition: " + tilstand);
    }
}

