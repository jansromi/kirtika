package kirtika.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ BooksTest.class, BookTest.class, GenresTest.class, GenreTest.class, KirtikaTest.class,
		LoanTest.class })
public class AllTests {

}
