package spart.search;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import spart.nlpir.HanlpTool;
import spart.parser.ASSentence;
import spart.py.DSNFTriple;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-05 10:11:47
 */
public class ASTriple {
	private static final ASTriple EMPTY_TRIPLE = new ASTriple();
	private static final String WILDCARD = "*";
	
	private String matchedThing;
	private String subject, predicate, object;
	
	private ASTriple() {}
	
	private ASTriple(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	public static ASTriple fromDSNFTriple(DSNFTriple triple) {		
		return new ASTriple(triple.subject(), triple.predicate(), triple.object());
	}

	public static ASTriple make(String text) {
		CoNLLSentence sentence = HanLP.parseDependency(text);
		List<CoNLLWord> words = HanlpTool.coreDependency(sentence);
		
		if(words.size() <= 1) return null;
		
		final CoNLLWord predicate = words.get(0),
				subject = words.get(1);
		if(words.size() == 2 && predicate.DEPREL.equals("主谓关系")) {
			return new ASTriple(makeTing(subject.LEMMA), predicate.LEMMA, WILDCARD);
		}
		
		if(words.size() >= 3) {
			final CoNLLWord object = words.get(2);
			return new ASTriple(makeTing(subject.LEMMA), predicate.LEMMA, makeTing(object.LEMMA));
		}
		
		return EMPTY_TRIPLE;
	}
	
	public boolean match(ASTriple triple) {
		if(triple.subject == null || triple.predicate == null || triple.object == null)
			return false;
		
		return matchThing(subject, triple.subject)
				&& matchThing(predicate, triple.predicate)
				&& matchThing(object, triple.object);
	}
	
	public String matchedThing() {
		return matchedThing;
	}
	
	private boolean matchThing(String text1, String text2) {
		if(text1 == null || text2 == null) return false;
		
		if(WILDCARD.equals(text1) || WILDCARD.equals(text2)) {
			matchedThing = (WILDCARD.equals(text1) ? text2 : text1);
			return true;
		}
		
		if(text1.equals(text2)) return true;
		
		return new ASSentence(text1).senseSimilarity(new ASSentence(text2)) >= 1.0f;
	}
	
	private static final String[] keys = {"什么", "哪", "谁"};
	private static String makeTing(String thing) {
		
		for(String key: keys) {
			if(thing.contains(key)) return WILDCARD;
		}
		
		return thing;
	}
	
	public String toString() {
		return subject + " " + predicate + " " + object;
	}
}
