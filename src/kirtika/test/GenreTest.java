package kirtika.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kirtika.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.11 11:28:50 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class GenreTest {



  // Generated by ComTest BEGIN
  /** testGenre16 */
  @Test
  public void testGenre16() {    // Genre: 16
    Genre genre = new Genre("84.2|Suomalainen kertomakirjallisuus"); 
    assertEquals("From: Genre line: 18", "84.2", genre.getGenreId()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString64 */
  @Test
  public void testToString64() {    // Genre: 64
    Genre genre = new Genre("84.2|Suomalainen kertomakirjallisuus"); 
    assertEquals("From: Genre line: 66", "84.2 Suomalainen kertomakirjallisuus", genre.toString()); 
  } // Generated by ComTest END
}