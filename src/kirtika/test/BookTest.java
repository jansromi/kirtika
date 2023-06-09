package kirtika.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kirtika.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.09 20:29:35 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class BookTest {



  // Generated by ComTest BEGIN
  /** testGetIsbn118 */
  @Test
  public void testGetIsbn118() {    // Book: 118
    Book kirja1 = new Book(); 
    kirja1.setOdysseia(); 
    assertEquals("From: Book line: 121", "9789511318866", kirja1.getIsbn()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testSetBookId140 */
  @Test
  public void testSetBookId140() {    // Book: 140
    Book book1 = new Book(); 
    Book book2 = new Book(); 
    int id1 = book1.setBookId(); 
    int id2 = book2.setBookId(); 
    assertEquals("From: Book line: 145", id2 - 1, id1); 
  } // Generated by ComTest END
}