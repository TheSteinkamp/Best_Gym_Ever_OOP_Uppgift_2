import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;

import static java.util.concurrent.TimeUnit.DAYS;

public class MemberInfo {

    String readString = "src\\Memberslist";
    String writeString = "src\\ActiveMembersList";
    List<Members> allMembers = new ArrayList<>();

    public List<Members> saveToListFromFile(String readString) {
        List<Members> allMembers = new ArrayList<>();
        String line1 = "";
        String line2 = "";
        try (Scanner scan = new Scanner(new FileReader(readString))) {
            while (scan.hasNextLine()) { // om det finns en till rad i scanner objektet vi skickade in gör vi följande
                Members m = new Members();
                line1 = scan.nextLine(); // scannar den raden och nästa till två variabler
                String[] tempArray = line1.split(",");
                m.setSsn(Long.parseLong(tempArray[0].trim()));
                m.setName(tempArray[1].trim());
                line2 = scan.nextLine();
                m.setLastPayment(stringToLocalDate(line2));
                allMembers.add(m);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen hittas inte");
        } catch (Exception e) {
            System.out.println("Något gick fel");
        }
        return allMembers;
    }
    // en metod som i inparametern tar en string med filen man ska läsa från och sen i metoden går igenom två rader i taget och sparar till
    // objektet member och sedan lägger in alla objekten i en lista, ungefär som uppgift 9b


    public LocalDate stringToLocalDate(String date) {
        String[] tempArray = date.split("-");
        int year = Integer.parseInt(tempArray[0].trim());
        int month = Integer.parseInt(tempArray[1].trim());
        int day = Integer.parseInt(tempArray[2].trim());
        return LocalDate.of(year, month, day);
    }
    // metod som tar en string i inparameter och returnerar ett LocalDate


    public String searchInList(List<Members> allMembers, String input) {
        LocalDate now = LocalDate.now();
        try {
            for (Members m : allMembers) {
                if (input.equals(m.getName()) || input.equals(String.valueOf(m.getSsn()))) {
                    if (lengthOfTime(now, m.getLastPayment())) {
                        System.out.println(m.getName());
                        System.out.println(saveToFileFromList(m, writeString));
                        return "Existing member";
                    } else {
                        System.out.println(m.getName());
                        return "Previous member";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Något gick fel");
        }
        return "Not member";
    }
    // metod som i inparameter tar en lista och en string som man ger in genom användarinput från terminalen och med den söker igenom listan man
    // ger i parametern och ser om personen finns med och hur länge sen personen betalade och skriver ut passande text.


    public Boolean lengthOfTime(LocalDate now, LocalDate payment) {
        long noOfDaysBetween = ChronoUnit.DAYS.between(payment, now);
        return noOfDaysBetween <= 365;
    }
    //Metod som i inparameter tar två datum och jämför dom och returnerar en boolean. är datumen mindre än ett år ifrån varandra returneas true, annars false


    public String saveToFileFromList(Members m, String writeString) {
        LocalDate ld = LocalDate.now();

        try (PrintWriter pr = new PrintWriter(new FileWriter(writeString, true))) {
            pr.println(m.getName() + " har besökt gymet " + ld);
        } catch (FileNotFoundException e) {
            System.out.println("Filen hittas inte");
        } catch (IOException e) {
            System.out.println("Något gick fel");
        }
        return m.getName() + " Har besökt gymet " + ld;
    }

    // metod som i inparameter tar ett objekt av typen Member från när man söker ovan och en string dit vi ska spara infon och lägger det i
    // en ny fil med de medlemmar som är aktiva. Den här metoden används alltså i metoden ovan


    MemberInfo() {
        allMembers = saveToListFromFile(readString);
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Skriv in personnummer eller namn: ");
            String answer = scan.nextLine();
            if (!answer.isEmpty()) {
                System.out.println(searchInList(allMembers, answer));
                break;
            }
        }

/*        Scanner scan = new Scanner(System.in);
        System.out.println("Skriv in personnummer eller namn: ");
        String answer = scan.nextLine();
        System.out.println(searchInList(allMembers, answer));*/


    }

    public static void main(String[] args) {
        MemberInfo m = new MemberInfo();

    }
}
