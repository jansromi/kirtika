package kirtika.test;
// Generated by ComTest BEGIN
import java.time.LocalDate;
import kirtika.Loan;
import static org.junit.Assert.*;
import org.junit.*;
import static kirtika.Loan.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.11 11:44:39 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LoanTest {



  // Generated by ComTest BEGIN
  /** testSetDate92 */
  @Test
  public void testSetDate92() {    // Loan: 92
    Loan loan = new Loan(); 
    LocalDate a1 = SetDate.setDate("2022-01-01"); 
    assertEquals("From: Loan line: 98", 2022, a1.getYear()); 
    assertEquals("From: Loan line: 99", 1, a1.getMonthValue()); 
  } // Generated by ComTest END
}