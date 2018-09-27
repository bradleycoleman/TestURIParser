package uriparser;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;



/**
 * SOFTENG 254 2018 Assignment 1 submission
 *
 * Author: (Bradley Coleman, bcol085)
 **/

public class TestURIParser {
	
	/**
	 * This tests that attempting to parse a null URI will throw a ParseException error
	 **/
	@Test
	public void testNullURI() {
		URIParser parser = new URIParser();
		try {
			URI nullURL = parser.parse(null);
			fail("Exception not thrown");
		} catch (ParseException e) {
		}
	}
	
	/**
	 * This tests that all of the characters which are legal can be parsed without exception - 
	 * no characters have been labelled illegal by mistake.
	 **/
	@Test
	public void testAllCharURI() {
		URIParser parser = new URIParser();
		try {
			URI allChar = parser.parse("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$-_.+!*'(),;/?:@&=%#");
		} catch (ParseException e) {
			fail("Did not parse legal URI");
		}
	}
	
		
	/**
	 * This URL-URI has characters that are illegal in some parts but not all (':','/','?','#') 
	 * in any parts of the URI that can have them legally, to test that the legal characters
	 * for each part aren't considered the same.
	 **/
    @Test
    public void testURL() {
		URIParser URLParser = new URIParser();
		URI URL = URLParser.parse("http://www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Incorrect scheme", "http", URL.getScheme());
		//Special character ':'
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", URL.getAuthority());
		//Special characters '//',':'
		assertEquals("Incorrect path", "//.:", URL.getPath());
		//Special characters '//',':','?' 
		assertEquals("Incorrect query", "a?//:", URL.getQuery());
		//Special characters '//',':','?','#'
		assertEquals("Incorrect fragment", "#a?//:", URL.getFragment());
    }
	
	/**
	 * ~~~~~~~~~~~~~~TESTS ON THE EFFECT OF REMOVING INDIVIDUAL PARTS~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * These are done to test that each part can be considered null, even if all other parts 
	 * are legal.
	 *
	 **/
	
	/**
	 * This tests that a URI with a scheme meant to be null has a null scheme, and that the next parts 
	 * are all identified correctly.
	 **/
	@Test
	public void testNullScheme() {
		URIParser nullSchemeParser = new URIParser();
		URI nullScheme = nullSchemeParser.parse("//www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Should have null scheme", null, nullScheme.getScheme());
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", nullScheme.getAuthority());
		assertEquals("Incorrect path", "//.:", nullScheme.getPath());
		assertEquals("Incorrect query", "a?//:", nullScheme.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", nullScheme.getFragment());
	}
	
	/**
	 * This tests that a URI with an authority meant to be null has a null authority, and correctly
	 * identifies all the other parts.
	 **/
	@Test
	public void testNullAuthority() {
		URIParser nullAuthParser = new URIParser();
		URI nullAuth = nullAuthParser.parse("http:www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Incorrect scheme", "http", nullAuth.getScheme());
		assertEquals("Should have null authority", null, nullAuth.getAuthority());
		assertEquals("Incorrect path", "www.cs.auckland.ac.nz://.:", nullAuth.getPath());
		assertEquals("Incorrect query", "a?//:", nullAuth.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", nullAuth.getFragment());
	}
	
	/**
	 * This tests that a URI with a path meant to be null has a null path, and correctly
	 * identifies all the other parts.
	 **/
	@Test
	public void testNullPath() {
		URIParser nullPathParser = new URIParser();
		URI nullPath = nullPathParser.parse("http://www.cs.auckland.ac.nz:?a?//:##a?//:");
		assertEquals("Incorrect scheme", "http", nullPath.getScheme());
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", nullPath.getAuthority());
		assertEquals("Should have null path", null, nullPath.getPath());
		assertEquals("Incorrect query", "a?//:", nullPath.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", nullPath.getFragment());
	}
	
	/**
	 * This tests that a URI with a query meant to be null has a null query, and correctly
	 * identifies all the other parts.
	 **/
	@Test
	public void testNullQuery() {
		URIParser nullQueryParser = new URIParser();
		URI nullQuery = nullQueryParser.parse("http://www.cs.auckland.ac.nz://.:##a?//:");
		assertEquals("Incorrect scheme", "http", nullQuery.getScheme());
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", nullQuery.getAuthority());
		assertEquals("Incorrect path", "//.:", nullQuery.getPath());
		assertEquals("Should have null query", null, nullQuery.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", nullQuery.getFragment());
	}
	
	/**
	 * This tests that a URI with a query meant to be null has a null query, and correctly
	 * identifies all the other parts.
	 **/
	@Test
	public void testNullFragment() {
		URIParser nullFragmentParser = new URIParser();
		URI nullFragment = nullFragmentParser.parse("http://www.cs.auckland.ac.nz://.:?a?//:");
		assertEquals("Incorrect scheme", "http", nullFragment.getScheme());
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", nullFragment.getAuthority());
		assertEquals("Incorrect path", "//.:", nullFragment.getPath());
		assertEquals("Incorrect query", "a?//:", nullFragment.getQuery());
		assertEquals("Should have null fragment", null, nullFragment.getFragment());
	}
	
	/**
	 * This tests a blank URI where all parts are meant to be null
	 **/
	@Test
	public void testBlankURI() {
		URIParser blankParser = new URIParser();
		URI blankURI = blankParser.parse("");
		assertEquals("Should have null scheme", null, blankURI.getScheme());
		assertEquals("Should have null authority", null, blankURI.getAuthority());
		assertEquals("Should have null path", null, blankURI.getPath());
		assertEquals("Should have null query", null, blankURI.getQuery());
		assertEquals("Should have null fragment", null, blankURI.getFragment());
	}
	
	/**
	 * ~~~~~~~~~~~~~~TESTS ON THE EFFECT OF MAKING INDIVIDUAL PARTS BLANK~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * These are done to test that blank string parts are possible for the appropriate parts and
	 * not considered null. There is no test for path as it is impossible to have a blank path.
	 *
	 **/
	
	/**
	 * This tests that a URI with no scheme returns null for URI.getScheme. This is really testing
	 * whether the parser has a 1-character minimum for scheme, as a blank scheme is impossible.
	 **/
	@Test
	public void testNoScheme() {
		URIParser noSchemeParser = new URIParser();
		URI noScheme = noSchemeParser.parse("://www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Should have null scheme", null, noScheme.getScheme());
		assertEquals("Should have null authority", null, noScheme.getAuthority());
		assertEquals("Incorrect path","://www.cs.auckland.ac.nz://.:", noScheme.getPath());
		assertEquals("Incorrect query","a?//:", noScheme.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", noScheme.getFragment());
	}
	
	/**
	 * This tests a URI with blank authority
	 **/
	@Test
	public void testNoAuthority() {
		URIParser noAuthParser = new URIParser();
		URI noAuth = noAuthParser.parse("http:////.:?a?//:##a?//:");
		assertEquals("Incorrect scheme", "http", noAuth.getScheme());
		assertEquals("Should have blank authority", "", noAuth.getAuthority());
		assertEquals("Incorrect path", "//.:", noAuth.getPath());
		assertEquals("Incorrect query","a?//:", noAuth.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", noAuth.getFragment());
	}
	
	/**
	 * This tests a URI with blank query
	 **/
	@Test
	public void testNoQuery() {
		URIParser noQueryParser = new URIParser();
		URI noQuery = noQueryParser.parse("http://www.cs.auckland.ac.nz://.:?##a?//:");
		assertEquals("Incorrect scheme", "http", noQuery.getScheme());
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", noQuery.getAuthority());
		assertEquals("Incorrect path", "//.:", noQuery.getPath());
		assertEquals("Should have blank query", "", noQuery.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", noQuery.getFragment());
	}
	
	/**
	 * This tests a URI with blank fragment
	 **/
	@Test
	public void testNoFragment() {
		URIParser noFragmentParser = new URIParser();
		URI noFragment = noFragmentParser.parse("http://www.cs.auckland.ac.nz://.:?a?//:#");
		assertEquals("Incorrect scheme", "http", noFragment.getScheme());
		assertEquals("Incorrect authority", "www.cs.auckland.ac.nz:", noFragment.getAuthority());
		assertEquals("Incorrect path", "//.:", noFragment.getPath());
		assertEquals("Incorrect query", "a?//:", noFragment.getQuery());
		assertEquals("Should have blank	fragment", "", noFragment.getFragment());
	}
	
	/**
	 * ~~~~~~~~~~~~~~~~TESTING THE EFFECT OF HAVING ONLY ONE LEGAL PART~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * These are done to test if any parts will fail to be detected if all the other parts are 
	 * null.
	 *
	 **/
	
	/**
	 * This tests a URI with just a scheme. All parts should be null except the scheme
	 **/
    @Test
    public void testOnlyScheme() {
		URIParser schemeParser = new URIParser();
		URI scheme = schemeParser.parse("http:");
		assertEquals("Incorrect scheme", "http", scheme.getScheme());
		assertEquals("Authority should be null", null, scheme.getAuthority());
		assertEquals("Path should be null", null, scheme.getPath());
		assertEquals("Query should be null", null, scheme.getQuery());
		assertEquals("Fragment should be null", null, scheme.getFragment());
    }
	
	/**
	 * This tests a URI with just an authority. All parts should be null except the authority
	 **/
    @Test
    public void testOnlyAuthority() {
		URIParser authorityParser = new URIParser();
		URI authority = authorityParser.parse("//http"); 
		assertEquals("Scheme should be null", null, authority.getScheme());
		assertEquals("Incorrect authority", "http", authority.getAuthority());
		assertEquals("Path should be null", null, authority.getPath());
		assertEquals("Query should be null", null, authority.getQuery());
		assertEquals("Fragment should be null", null, authority.getFragment());
    }
	
	/**
	 * This tests a URI with just a path. All parts should be null except the path.
	 **/
    @Test
    public void testOnlyPath() {
		URIParser pathParser = new URIParser();
		URI path = pathParser.parse("http");
		assertEquals("Scheme should be null", null, path.getScheme());
		assertEquals("Authority should be null", null, path.getAuthority());
		assertEquals("Incorrect path", "http", path.getPath());
		assertEquals("Query should be null", null, path.getQuery());
		assertEquals("Fragment should be null", null, path.getFragment());
    }
	
	
	/**
	 * This tests a URI with just a query. All parts should be null except the query
	 **/
    @Test
    public void testOnlyQuery() {
		URIParser queryParser = new URIParser();
		URI query = queryParser.parse("?http");
		assertEquals("Scheme should be null", null, query.getScheme());
		assertEquals("Authority should be null", null, query.getAuthority());
		assertEquals("Path should be null", null, query.getPath());
		assertEquals("Incorrect query", "http", query.getQuery());
		assertEquals("Fragment should be null", null, query.getFragment());
    }

	/**
	 * This tests a URI with just a fragment. All parts should be null except the fragment
	 **/
    @Test
    public void testOnlyFragment() {
		URIParser fragmentParser = new URIParser();
		URI fragment = fragmentParser.parse("#http");
		assertEquals("Scheme should be null", null, fragment.getScheme());
		assertEquals("Authority should be null", null, fragment.getAuthority());
		assertEquals("Path should be null", null, fragment.getPath());
		assertEquals("Query should be null", null, fragment.getQuery());
		assertEquals("Incorrect fragment", "http", fragment.getFragment());
    }
	
	/**
	 * ~~~~~~~~~~~~~~~~TESTING THE EFFECT OF HAVING TWO OR MORE PART GAP ~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * These are done to test that the parser can correctly identify two parts that a seperated
	 * by two null parts. A seperation of one null part is tested in the null part tests, this 
	 * tests that parsers can have a gap of two null, while the rest of the URI is legal.
	 *
	 **/
	
	/**
	 * This tests a URI with only a scheme and a query.
	 **/
	@Test
    public void testSchemeAndQuery() {
		URIParser schemeQueryParser = new URIParser();
		URI schemeQuery = schemeQueryParser.parse("http:?www.cs.auckland.ac.nz://.:");
		assertEquals("Incorrect scheme", "http", schemeQuery.getScheme());
		assertEquals("Should be null authority", null, schemeQuery.getAuthority());
		assertEquals("Should be null path", null, schemeQuery.getPath());
		assertEquals("Incorrect query", "www.cs.auckland.ac.nz://.:", schemeQuery.getQuery());
		assertEquals("Should be null fragment", null, schemeQuery.getFragment());
    }
	
	/**
	 * This tests a URI with only a scheme and a fragment.
	 **/
	@Test
    public void testSchemeAndFragment() {
		URIParser schemeFragmentParser = new URIParser();
		URI schemeFragment = schemeFragmentParser.parse("http:#www.cs.auckland.ac.nz://.:");
		assertEquals("Incorrect scheme", "http", schemeFragment.getScheme());
		assertEquals("Should be null authority", null, schemeFragment.getAuthority());
		assertEquals("Should be null path", null, schemeFragment.getPath());
		assertEquals("Should be null query", null, schemeFragment.getQuery());
		assertEquals("Incorrect fragment", "www.cs.auckland.ac.nz://.:", schemeFragment.getFragment());
    }
	
	/**
	 * This tests a URI with only a authority and a query.
	 **/
	@Test
    public void testAuthorityAndQuery() {
		URIParser authorityQueryParser = new URIParser();
		URI authorityQuery = authorityQueryParser.parse("//http:?www.cs.auckland.ac.nz://.:");
		assertEquals("Should be null scheme", null, authorityQuery.getScheme());
		assertEquals("Incorrect authority", "http:", authorityQuery.getAuthority());
		assertEquals("Should be null path", null, authorityQuery.getPath());
		assertEquals("Incorrect query", "www.cs.auckland.ac.nz://.:", authorityQuery.getQuery());
		assertEquals("Should be null fragment", null, authorityQuery.getFragment());
    }
	
	/**
	 * This tests a URI with only a authority and a fragment.
	 **/
	@Test
    public void testAuthorityAndFragment() {
		URIParser authorityFragmentParser = new URIParser();
		URI authorityFragment = authorityFragmentParser.parse("//http:#www.cs.auckland.ac.nz://.:");
		assertEquals("Should be null scheme", null, authorityFragment.getScheme());
		assertEquals("Incorrect authority", "http:", authorityFragment.getAuthority());
		assertEquals("Should be null path", null, authorityFragment.getPath());
		assertEquals("Should be null query", null, authorityFragment.getQuery());
		assertEquals("Incorrect fragment", "www.cs.auckland.ac.nz://.:", authorityFragment.getFragment());
    }
	
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~TESTING ONLY SPECIAL CHARACTER URI'S~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * These are done to test if the parser correctly handles URI's which are comprised of only 
	 * 'special' characters - characters that are illegal for certain parts and/or signify the
	 * start/end of specific parts. A test for just a colon is not included as test testBlankScheme 
	 * makes it redundant.
	 *
	 **/
	
	/**
	 * This tests a URI which is just a '/' character. This tests to make sure the single '/'
	 * won't start a blank authority, instead neither a scheme nor authority should be created
	 * and the '/' will go to path
	 **/
	@Test
	public void testJustSlash() {
		URIParser slashParser = new URIParser();
		URI slash = slashParser.parse("/");
		assertEquals("Should be null scheme", null, slash.getScheme());
		assertEquals("Should be null authority", null, slash.getAuthority());
		assertEquals("Incorrect path", "/", slash.getPath());
		assertEquals("Should be null query", null, slash.getQuery());
		assertEquals("Should be null fragment", null, slash.getFragment());
	}
	
	/**
	 * This tests a URI which is just a '?' character - this tests to make sure a blank query 
	 * can be created even if all other parts are null.
	 **/
	@Test
	public void testJustQuestion() {
		URIParser questionParser = new URIParser();
		URI question = questionParser.parse("?");
		assertEquals("Should be null scheme", null, question.getScheme());
		assertEquals("Should be null authority", null, question.getAuthority());
		assertEquals("Incorrect path", null, question.getPath());
		assertEquals("Should be blank query", "", question.getQuery());
		assertEquals("Should be null fragment", null, question.getFragment());
	}	
	
	/**
	 * This tests a URI which is just a '#' character - this tests to make sure a blank fragment 
	 * can be created even if all other parts are null.
	 **/
	@Test
	public void testJustHash() {
		URIParser hashParser = new URIParser();
		URI hash = hashParser.parse("#");
		assertEquals("Should be null scheme", null, hash.getScheme());
		assertEquals("Should be null authority", null, hash.getAuthority());
		assertEquals("Should be null path", null, hash.getPath());
		assertEquals("Should be null query", null, hash.getQuery());
		assertEquals("Should be blank fragment", "", hash.getFragment());
	}
	
	/**
	 * This tests a URI which is two '/' characters - this tests to make sure a blank authority 
	 * can be created even if all other parts are null.
	 **/
	@Test
	public void testDoubleSlash() {
		URIParser doubleSlashParser = new URIParser();
		URI doubleSlash = doubleSlashParser.parse("//");
		assertEquals("Should be null scheme", null, doubleSlash.getScheme());
		assertEquals("Incorrect authority", "", doubleSlash.getAuthority());
		assertEquals("Should be null path", null, doubleSlash.getPath());
		assertEquals("Should be null query", null, doubleSlash.getQuery());
		assertEquals("Should be null fragment", null, doubleSlash.getFragment());
	}
	
	/**
	 * This tests a URI which is four '/' characters - this tests that two of the slashes are 
	 * used to create a blank authority, but the second pair are used in a path, as the authority
	 * should not be started twice and '/' is illegal in authority.
	 **/
	@Test
	public void testQuadSlash() {
		URIParser quadSlashParser = new URIParser();
		URI quadSlash = quadSlashParser.parse("////");
		assertEquals("Should be null scheme", null, quadSlash.getScheme());
		assertEquals("Should be blank authority", "", quadSlash.getAuthority());
		assertEquals("Incorrect path", "//", quadSlash.getPath());
		assertEquals("Should be null query", null, quadSlash.getQuery());
		assertEquals("Should be null fragment", null, quadSlash.getFragment());
	}
	
	/**
	 * This tests a URI which is two ':' characters. This will test to make sure a 1-character 
	 * scheme won't be created as ':' is an illegal character for scheme.
	 **/
	@Test
	public void testDoubleColon() {
		URIParser doubleColonParser = new URIParser();
		URI doubleColon = doubleColonParser.parse("::");
		assertEquals("Should be null scheme", null, doubleColon.getScheme());
		assertEquals("Should be null authority", null, doubleColon.getAuthority());
		assertEquals("Incorrect path", "::", doubleColon.getPath());
		assertEquals("Should be null query", null, doubleColon.getQuery());
		assertEquals("Should be null fragment", null, doubleColon.getFragment());
	}
	
	/**
	 * This tests a URI which is two '?' characters. This will test to make sure the second '?'  
	 * won't be removed from the query, so a query of only "?" can be created. 
	 **/
	@Test
	public void testDoubleQuestion() {
		URIParser doubleQuestionParser = new URIParser();
		URI doubleQuestion = doubleQuestionParser.parse("??");
		assertEquals("Should be null scheme", null, doubleQuestion.getScheme());
		assertEquals("Should be null authority", null, doubleQuestion.getAuthority());
		assertEquals("Should be null path", null, doubleQuestion.getPath());
		assertEquals("Incorrect query", "?", doubleQuestion.getQuery());
		assertEquals("Should be null fragment", null, doubleQuestion.getFragment());
	}
	
	/**
	 * This tests a URI which is two '#' characters. This will test to make sure the second '#'  
	 * won't be removed from the fragment, so a fragment of only "#" can be created. 
	 **/
	@Test
	public void testDoubleHash() {
		URIParser doubleHashParser = new URIParser();
		URI doubleHash = doubleHashParser.parse("##");
		assertEquals("Should be null scheme", null, doubleHash.getScheme());
		assertEquals("Should be null authority", null, doubleHash.getAuthority());
		assertEquals("Should be null path", null, doubleHash.getPath());
		assertEquals("Should be null query", null, doubleHash.getQuery());
		assertEquals("Incorrect fragment", "#", doubleHash.getFragment());
	}
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~TESTING PARTS WITH ILLEGAL CHARACTERS~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * These are done to test if the parser will not parse certain characters in parts where they
	 * are illegal. Some tests (nameley the blank-part tests) have already tested certain characters
	 * for certain parts so those combinations are not tested here.
	 *
	 **/
	 
	/**
	 * This tests a URI with a ':' character to ildicate the end of a scheme, but said scheme is
	 * only a '/' character. The '/' character is illegal for scheme so the scheme should not be 
	 * parsed.
	 **/
    @Test
    public void testSlashScheme() {
		URIParser slashSchemeParser = new URIParser();
		URI slashScheme = slashSchemeParser.parse("/://www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Should be null scheme", null, slashScheme.getScheme());
		assertEquals("Should be null authority", null, slashScheme.getAuthority());
		assertEquals("Incorrect path", "/://www.cs.auckland.ac.nz://.:", slashScheme.getPath());
		assertEquals("Incorrect query", "a?//:", slashScheme.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", slashScheme.getFragment());
    }
	
	/**
	 * This tests a URI with a ':' character to ildicate the end of a scheme, but said scheme is
	 * only a '#' character. The '#' character is illegal for scheme so the scheme should not be 
	 * parsed.
	 **/
    @Test
    public void testQuestionScheme() {
		URIParser questionSchemeParser = new URIParser();
		URI questionScheme = questionSchemeParser.parse("?://www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Should be null scheme", null, questionScheme.getScheme());
		assertEquals("Should be null authority", null, questionScheme.getAuthority());
		assertEquals("Should be null path", null, questionScheme.getPath());
		assertEquals("Incorrect query", "://www.cs.auckland.ac.nz://.:?a?//:", questionScheme.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", questionScheme.getFragment());
    }
	
	/**
	 * This tests a URI with a ':' character to ildicate the end of a scheme, but said scheme is
	 * only a '?' character. The '?' character is illegal for scheme so the scheme should not be 
	 * parsed.
	 **/
    @Test
    public void testHashScheme() {
		URIParser hashSchemeParser = new URIParser();
		URI hashScheme = hashSchemeParser.parse("#://www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Should be null scheme", null, hashScheme.getScheme());
		assertEquals("Should be null authority", null, hashScheme.getAuthority());
		assertEquals("Should be null path", null, hashScheme.getPath());
		assertEquals("Should be null query", null, hashScheme.getQuery());
		assertEquals("Incorrect fragment", "://www.cs.auckland.ac.nz://.:?a?//:##a?//:", hashScheme.getFragment());
    }
	
	/**
	 * This tests a URI with a "//" to ildicate the start of an authority, but said autority has
	 * a '?' character. The '?' character is illegal for authority so the authority should be 
	 * blank.
	 **/
    @Test
    public void testQuestionAuthority() {
		URIParser questionAuthorityParser = new URIParser();
		URI questionAuthority = questionAuthorityParser.parse("http://?www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Incorrect scheme", "http", questionAuthority.getScheme());
		assertEquals("Should be blank authority", "", questionAuthority.getAuthority());
		assertEquals("Should be null path", null, questionAuthority.getPath());
		assertEquals("Incorrect query", "www.cs.auckland.ac.nz://.:?a?//:", questionAuthority.getQuery());
		assertEquals("Incorrect fragment", "#a?//:", questionAuthority.getFragment());
    }
	
	/**
	 * This tests a URI with a "//" to ildicate the start of an authority, but said autority has
	 * a '#' character. The '#' character is illegal for authority so the authority should be 
	 * blank.
	 **/
    @Test
    public void testHashAuthority() {
		URIParser hashAuthorityParser = new URIParser();
		URI hashAuthority = hashAuthorityParser.parse("http://#www.cs.auckland.ac.nz://.:?a?//:##a?//:");
		assertEquals("Incorrect scheme", "http", hashAuthority.getScheme());
		assertEquals("Should be blank authority", "", hashAuthority.getAuthority());
		assertEquals("Should be null path", null, hashAuthority.getPath());
		assertEquals("Should be null query", null, hashAuthority.getQuery());
		assertEquals("Incorrect fragment", "www.cs.auckland.ac.nz://.:?a?//:##a?//:", hashAuthority.getFragment());
    }
}
