package org.ml.util;
import java.util.*;

public class Global {

	public static Properties appProps;
	static{
		FileHelper fh = new FileHelper();
		appProps = fh.getProperties("/application.configuration");
	}
       
	public static final String APPLICATION_TITLE = appProps.getProperty("title");
	public static final String APPLICATION_SUBTITLE = appProps.getProperty("subtitle");

	public static final String APPLICATION_BASE_URL = appProps.getProperty("baseUrl");

	public static final String ADMIN_LOGIN = appProps.getProperty("ADMIN_LOGIN");
	public static final String ADMIN_PASS = appProps.getProperty("ADMIN_PASS");

	public static final Integer MAX_NUMBER_OF_CONVERSATIONS = 100;
	public static final Integer MAX_NUMBER_OF_UMLS_CUIS = 5;
	public static final Integer MAX_MILISECONDS_CONVERSATION_DURATION = 3600000;

	public static final String SESAME__GRAMMAR_REPOSITORY_NAME = appProps.getProperty("SESAME__GRAMMAR_REPOSITORY_NAME");
	public static final String SESAME_REPOSITORY_URL = appProps.getProperty("SESAME_REPOSITORY_URL");

	public static  final String CLASS_PATH = appProps.getProperty("class_path");

	public static final String WORDNET_LOCATION = CLASS_PATH + "WordNet-3.0/dict";
	public static final String STANFORD_LOCATION = CLASS_PATH + "bidirectional-wsj-0-18.tagger";
	public static final String LOG4J_LOCATION = CLASS_PATH + "log4j.configuration";

	public static final char DEFAULT_CSV_SEPARATOR = ',';
	public static final String DEFAULT_LINE_SEPARATOR = "\r\n"; // CRLF.

	public static  Properties umlsDBProps;
	static {
		FileHelper fh = new FileHelper();
		umlsDBProps = fh.getProperties("/umlsDBProps.configuration");
	}

	public static  Properties SUNYREACHDB;
	static {
		FileHelper fh = new FileHelper();
		SUNYREACHDB = fh.getProperties("/sunyreachdb.configuration");
	}

	//domains
	public final static String SBU_REST_DOMAIN = appProps.getProperty("SBU_REST_DOMAIN");
	public final static String SBU_GRAMMAR_RDF_URL = appProps.getProperty("SBU_GRAMMAR_RDF_URL");

	//predicates
	public final static String PREDICATE_TYPE = appProps.getProperty("PREDICATE_TYPE");
	public final static String PREDICATE_LABEL =appProps.getProperty("PREDICATE_LABEL");
	public final static String PREDICATE_SUBCLASS_OF = appProps.getProperty("PREDICATE_SUBCLASS_OF");


	public final static String STOP_WORDS = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at," +
			"be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he," +
			"her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor," +
			"not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these," +
			"they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your,for";

}
