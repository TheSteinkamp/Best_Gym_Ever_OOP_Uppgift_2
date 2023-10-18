import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberInfoTest {
    MemberInfo mi = new MemberInfo();
    String readPath = "Test\\MembersListTest";
    String writePath = "Test\\ActiveMembersListTest";
    List<Members> testMembers = new ArrayList<>();

    @Test
    public void saveToListFromFileTest(){
        testMembers = mi.saveToListFromFile(readPath);
        assertTrue(testMembers.size()==3);
    }
    // FUNKAR! en metod som i inparametern tar en string med filen man ska läsa till och sen i metoden går igenom två rader i taget och sparar till
    // objektet member och sedan lägger in alla objekten i en lista, ungefär som uppgift 9b.

    @Test
    public void searchInListTest(){
        String searchName = "Alhambra Aromes";
        String searchSSN = "8512021234";
        String searchName2 = "Kent Ek";
        testMembers = mi.saveToListFromFile(readPath);
        assertTrue(Objects.equals(mi.searchInList(testMembers, searchName), "Existing member"));
        assertTrue(Objects.equals(mi.searchInList(testMembers, searchSSN), "Previous member"));
        assertTrue(Objects.equals(mi.searchInList(testMembers, searchName2), "Not Member"));
    }
    // metod som i inparameter tar en lista och en string som man ger in genom användarinput från terminalen och med den söker igenom listan man
    // ger i parametern och ser om personen finns med och hur länge sen personen betalade och skriver ut passande text.

    public final int countLinesInFile(String fileToCount){
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileToCount))) {
            while (reader.readLine() != null) lines++;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return lines;
    }
    // metod som man kan kolla igenom filen nedan för att se att testet gick igenom

    @Test
    public void saveToFileFromListTest(){
        Members m = new Members(7703021234L, "Alhambra Aromes", LocalDate.now());
        mi.saveToFileFromList(m, writePath);
        assertTrue(countLinesInFile(writePath) == 1);

        try(BufferedReader b = new BufferedReader(new FileReader(writePath))){ // för att kolla att det kom in lär man läsa in filen igen
            String s = b.readLine(); // för varje läsning läser vi in en ny rad
            assertTrue(s.contains("Alhambra"));
        }
        catch (Exception e ){
            e.printStackTrace();
        }
    }
    // metod som i inparameter tar ett objekt av typen Member från när man söker ovan och en string dit vi ska spara infon och lägger det i
    // en ny fil med de medlemmar som är aktiva. Den här metoden används alltså i metoden ovan

    @Test
    public void stringToLocalDateTest(){
        String date = "2023-07-01";
        String date2 = "2011-02-12";
        LocalDate ld = LocalDate.of(2023, 07, 01);
        assertTrue(Objects.equals(mi.stringToLocalDate(date), ld));
        assertFalse(Objects.equals(mi.stringToLocalDate(date2), ld));
    }
    // metod som tar en string i inparameter och returnerar ett LocalDate

    @Test
    public void lengthOfTimeTest(){
        LocalDate now = LocalDate.now();
        LocalDate member = LocalDate.of(2023, 07, 01);
        LocalDate previousMember = LocalDate.of(2021, 01, 01);
        assertTrue(mi.lengthOfTime(now, member));
        assertFalse(mi.lengthOfTime(now, previousMember));
        assert !mi.lengthOfTime(now, previousMember);
    }
    //Metod som i inparameter tar två datum och jämför dom och returnerar en boolean. är datumen mindre än ett år ifrån varandra returneas true, annars false
}