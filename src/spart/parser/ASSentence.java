package spart.parser;

import java.util.*;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.lingjoin.demo.NlpirMethod;

import spart.nlpir.HanlpTool;
import spart.nlpir.NlpirWord;
import spart.nlpir.SenseSimilarity;
import spart.nlpir.SemanticSimilarity;
import spart.py.DSNFKnowledge;
import spart.py.DSNFTool;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:05:12
 */
public class ASSentence extends ASBlock {
	public static final ASSentence EMPTY_SENTENCE = new ASSentence("");
	public static final String SPLITTER = "[；;，,、]";
	
	private String text;
	private List<NlpirWord> words, wordsNoPunc;
	private List<DSNFKnowledge> knowledgeList;
	private List<ASSentence> parts;
	private CoNLLWord coreWord;
	private CoNLLSentence dependency;

	public ASSentence(String sentence) {
		assert sentence != null : "sentence can't be null";
		text = sentence.trim();
	}
	
	public void preprocess() {
		preprocess(true);
	}
	
	private void preprocess(boolean buildParts) {
		parse();
		parseDependency();
		extractTriples();

		if(buildParts) {
			buildParts();
			for(ASSentence part: parts) {
				part.preprocess(false);
			}
		}
	}
	
	public List<NlpirWord> getWords() {	
		parse();
		return this.words;
	}
	
	public List<NlpirWord> getWordsWithoutPunc() {
		if(this.wordsNoPunc != null) return this.wordsNoPunc;		

		wordsNoPunc = new ArrayList<NlpirWord>();		
		final List<NlpirWord> words = this.getWords();
		
		for(NlpirWord w: words) {
			if(w.getType().isPunctuation()) continue;
			
			this.wordsNoPunc.add(w);
		}
		
		return this.wordsNoPunc;
	}
	
	public List<DSNFKnowledge> getKnowledgeList() {	
		extractTriples();
		return knowledgeList;
	}
	
	public CoNLLSentence getDenpency() {
		parseDependency();
		return dependency;
	}
	
	public CoNLLWord getCoreWord() {
		parseDependency();
		return coreWord;
	}
	
	public float senseSimilarity(ASSentence other) {		
		return new SenseSimilarity(this, other).calculate();
	}
	
	public float semanticSimilarity(ASSentence other) {
		parseDependency();
		return new SemanticSimilarity(dependency, 
				other.getDenpency()).calculate();
	}
	
	public boolean isEmpty() {
		return text.isEmpty();
	}
	
	private void parse() {
		if(words != null) return;
		
		this.words = new ArrayList<NlpirWord>();
		
		String result = NlpirMethod.NLPIR_ParagraphProcess(text, 1);
		String[] wordList = result.split(" ");
		for(String wordWithType: wordList) {
			final String trimmed = wordWithType.trim();
			if(trimmed.isEmpty()) continue;
			
			final NlpirWord npWord = new NlpirWord(trimmed);
			this.words.add(npWord);
		}	
	}
	
	private void extractTriples() {
		if(knowledgeList != null) return;
		
		knowledgeList = new ArrayList<DSNFKnowledge>();
		
		String[] sentences = text.split("[。？！；（）．\\.．]");
		for(String s: sentences) {
			final String trimmed = s.trim();
			if(trimmed.isEmpty()) continue;
			
			ArrayList<?> triples = (ArrayList<?>) DSNFTool.newInstance().extractTriple(trimmed);
			if(triples.size() > 0) {
				for(Object triple: triples) {
					@SuppressWarnings("unchecked")
					final HashMap<String, Object> map = (HashMap<String, Object>) triple;
					final DSNFKnowledge kn = new DSNFKnowledge(map);
					knowledgeList.add(kn);
				}
			}
			
		}
	}
	
	private void buildParts() {
		if(parts != null) return;
		
		parts = new ArrayList<ASSentence>();
		String[] partArray = text.split(SPLITTER);
		for(String pt: partArray) {
			String trimmed = pt.trim();
			if(trimmed.isEmpty()) continue;
			
			final ASSentence part = new ASSentence(trimmed);
			part.setParent(this);
			part.preprocess(false);
			parts.add(part);
		}
	}
	
	private void parseDependency() {
		if(dependency != null) return;
		
		dependency = HanlpTool.parseDependency(text);
		coreWord = HanlpTool.findRoot(dependency);
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other) return true;
		if(other == null) return false;
	
		if(!(other instanceof ASSentence)) return false;
		return text.equals(((ASSentence) other).text);
	}
	
	@Override 
	public int hashCode() {
		return text.hashCode();
	}
	
	public String toString() {
		return text;
	}
	
	
	public List<ASSentence> getParts() {
		buildParts();
		return parts;
	}
	
	public static void main(String[] args) {
		ASSentence s1 = new ASSentence("他喝了一打汽水."),
				s2 = new ASSentence("我们都有一个中国梦.");
		System.out.println(s1.senseSimilarity(s2));
		
		s1 = new ASSentence("包括");
		s2 = new ASSentence("包含");
		System.out.println(s1.senseSimilarity(s2));
		
		s1 = new ASSentence("是");
		s2 = new ASSentence("指");
		System.out.println(s1.senseSimilarity(s2));
		
		s1 = new ASSentence("用机器推动的所有船只");
		s2 = new ASSentence("“机动船”一词，指用机器推进的任何船舶");
		System.out.println(s1.senseSimilarity(s2));
		
		s1 = new ASSentence("两船在不同舷受风");
		s2 = new ASSentence("两船的不同船舷受风");
		System.out.println(s1.senseSimilarity(s2));
		
		s1 = new ASSentence("长度小于12米的船舶");
		s2 = new ASSentence("长度小于50米的船舶");
		System.out.println(s1.semanticSimilarity(s2));
	}
}
