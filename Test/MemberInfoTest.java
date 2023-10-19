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
    public void saveToListFromFileTest() {
        testMembers = mi.saveToListFromFile(readPath);
        assertEquals(3, testMembers.size());
    }
    // metod som i inparameter tar en string med filen man ska läsa till och sen i metoden går igenom två rader i taget och sparar till
    // objektet member och sedan lägger in alla objekten i en lista.

    @Test
    public void searchInListTest() {
        String searchName = "Alhambra Aromes";
        String searchSSN = "8512021234";
        String searchName2 = "Kent Ek";
        testMembers = mi.saveToListFromFile(readPath);
        assertEquals("Nuvarande medlem", mi.searchInList(testMembers, searchName));
        assertEquals("Före detta medlem", mi.searchInList(testMembers, searchSSN));
        assertEquals("Inte medlem", mi.searchInList(testMembers, searchName2));
    }
    // metod som i inparameter tar en lista och en string som man ger in genom användarinput från terminalen och med den söker igenom listan man
    // ger i parametern och ser om personen finns med och hur länge sen personen betalade och skriver ut passande text.

    @Test
    public void saveToFileFromListTest() {
        Members m = new Members(7703021234L, "Alhambra Aromes", LocalDate.now());
        mi.saveToFileFromList(m, writePath);

        try (BufferedReader b = new BufferedReader(new FileReader(writePath))) { // för att kolla att det skrevs till filen lär man läsa in filen igen
            String s = b.readLine();
            assertTrue(s.contains("Alhambra"));
            assertFalse(s.contains("Evert"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // metod som i inparameter tar ett objekt av typen Member från när man söker ovan och en string dit vi ska spara infon och lägger det i
    // en ny fil med de medlemmar som är aktiva. Den här metoden används alltså i metoden ovan

    @Test
    public void stringToLocalDateTest() {
        String date = "2023-07-01";
        String date2 = "2011-02-12";
        LocalDate ld = LocalDate.of(2023, 07, 01);
        assertEquals(mi.stringToLocalDate(date), ld);
        assertNotEquals(mi.stringToLocalDate(date2), ld);
    }
    // metod som tar en string i inparameter och returnerar ett LocalDate

    @Test
    public void lengthOfTimeTest() {
        LocalDate now = LocalDate.now();
        LocalDate isMember = now.minusMonths(2); // subtraherar från dagens datum så testet alltid ska bli rätt när man än kör
        LocalDate isPreviousMember = now.minusYears(2);
        assertTrue(mi.lengthOfTime(now, isMember));
        assertFalse(mi.lengthOfTime(now, isPreviousMember));
    }
    //Metod som i inparameter tar två datum och jämför dom och returnerar en boolean. är datumen mindre än ett år ifrån varandra returneas true, annars false

    @Test
    public void inputFromUserTest() {
        MemberInfo.isTest = true;
        String text = "hej";
        assert (Objects.equals(mi.inputFromUser(text), "Slut på testet"));
    }
    //Metod som kollar av inmatningen och gör att vi kan köra testerna för sig utan att vänta på inmatning från användaren

    @Test
    public void reverseNameTest() {
        String rightName = "Janne Ek";
        String reverseName = "Ek Janne";
        String wrongName = "Ek";
        assertTrue(Objects.equals(mi.reverseName(rightName), reverseName));
        assertFalse(Objects.equals(mi.reverseName(rightName), wrongName));
    }
    // metod som i inparameter tar en string och om stringen innehåller ett mellanslag så delar vi uppp den och vänder på strängen,
    // så det funkar att söka även om vi skriver in efternamnet först
}