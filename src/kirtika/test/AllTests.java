package kirtika.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
/**
 * 
 * @author Jansromi
 * Kaikkien testien ajo
 */
@Suite
@SelectClasses({ GenretTest.class, KirjaTest.class, KirjatTest.class })
public class AllTests {
	// Ajetaan kaikki kirtikan testit
}
