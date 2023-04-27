package kirtika.test;
// Generated by ComTest BEGIN
import java.util.List;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.*;
import kirtika.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.27 09:12:53 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class BooksTest {



  // Generated by ComTest BEGIN
  /** testBookMatches89 */
  @Test
  public void testBookMatches89() {    // Books: 89
    Books books = new Books(true); 
    Book ody = new Book(); 
    ody.setOdysseia(); 
    Book ody2 = new Book(); 
    ody2.setOdysseia(); 
    Book buko = new Book(); 
    buko.setBukowski(); 
    books.addBook(ody); books.addBook(ody2); books.addBook(buko); 
    List<Book> matches = books.bookMatches("ody"); 
    assertEquals("From: Books line: 101", 2, matches.size()); 
    matches.clear(); 
    matches = books.bookMatches("pulp"); 
    assertEquals("From: Books line: 104", 1, matches.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testResizeArray133 */
  @Test
  public void testResizeArray133() {    // Books: 133
    Books books = new Books(true); 
    Book book = new Book(); 
    book.setOdysseia(); 
    assertEquals("From: Books line: 137", 10, books.getItemsLength()); 
    books.addItems(book, 10); 
    assertEquals("From: Books line: 139", 10, books.getAmt()); 
    assertEquals("From: Books line: 140", 10, books.getItemsLength()); 
    books.addBook(book); 
    assertEquals("From: Books line: 142", 20, books.getItemsLength()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testDeleteBook159 */
  @Test
  public void testDeleteBook159() {    // Books: 159
    Books books = new Books(true); 
    Book book1 = new Book(); 
    book1.setOdysseia(); 
    books.addItems(book1, 3); 
    Book book2 = new Book(); 
    book2.setOdysseia(); 
    books.addBook(book2); 
    assertEquals("From: Books line: 167", book2, books.get(3)); 
    books.addItems(book1, 3); 
    books.deleteBook(book2); 
    assertEquals("From: Books line: 170", book1, books.get(3)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testGetBookNotes194 
   * @throws IOException when error
   */
  @Test
  public void testGetBookNotes194() throws IOException {    // Books: 194
    Books books = new Books(true); 
    Book book = new Book(); 
    book.setOdysseia(); 
    books.addBook(book); 
    assertEquals("From: Books line: 202", "", books.getBookNotes(book)); 
    books.saveBookNotes(book, "Nymfit najadit Zeun tyttäret!"); 
    assertEquals("From: Books line: 204", "Nymfit najadit Zeun tyttäret!", books.getBookNotes(book)); 
    books.deleteBookNotes(book); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testDeleteBookNotes235 
   * @throws IOException when error
   */
  @Test
  public void testDeleteBookNotes235() throws IOException {    // Books: 235
    Books books = new Books(); 
    Book book = new Book(); 
    book.setOdysseia(); 
    assertEquals("From: Books line: 240", false, books.deleteBookNotes(book)); 
    String s = books.getBookNotes(book); 
    assertEquals("From: Books line: 242", true, books.deleteBookNotes(book)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAddBook295 */
  @Test
  public void testAddBook295() {    // Books: 295
    Books kirjat = new Books(); 
    Book ody = new Book(); 
    int x = kirjat.getAmt(); 
    kirjat.addBook(ody); 
    assertEquals("From: Books line: 300", x + 1, kirjat.getAmt()); 
  } // Generated by ComTest END
}