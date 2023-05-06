package spart.parser;

import java.util.*;

import com.lingjoin.demo.NlpirMethod;
import spart.nlpir.NlpirWord;
import spart.nlpir.SenseSimilarity;
import spart.py.DSNFKnowledge;
import spart.py.DSNFTool;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-12 16:05:12
 */
public class ASSentence extends ASBlock {
	
	private String sentence;
	private List<NlpirWord> words, wordsNoPunc;
	private List<DSNFKnowledge> knowledgeList;

	public ASSentence(String sentence) {
		this.sentence = sentence;
		words = new ArrayList<NlpirWord>();
		
		parse(sentence);
		extractTriples(sentence);
	}
	
	public List<NlpirWord> getWords() {
		return words;
	}
	
	public List<NlpirWord> getWordsWithoutPunc() {
		if(wordsNoPunc == null) {
			wordsNoPunc = new ArrayList<NlpirWord>();
		}
		for(NlpirWord w: words) {
			if(w.getType().isPunctuation()) continue;
			
			wordsNoPunc.add(w);
		}
		
		return wordsNoPunc;
	}
	
	public List<DSNFKnowledge> getKnowledgeList() {
		return knowledgeList;
	}
	
	public float senseSimilarity(ASSentence sentence) {
		return new SenseSimilarity(this, sentence).calculate();
	}
	
	private void parse(String sentence) {

		String result = NlpirMethod.NLPIR_ParagraphProcess(sentence, 1);
		String[] wordList = result.split(" ");
		for(String wordWithType: wordList) {
			if(wordWithType.trim().isEmpty()) continue;
			
			final NlpirWord npWord = new NlpirWord(wordWithType);
			words.add(npWord);
			
		}		
	}
	
	private void extractTriples(String line) {
		knowledgeList = new ArrayList<DSNFKnowledge>();
		
		String[] sentences = line.split("[。？！；（）．\\.．]");
		for(String s: sentences) {
			if(s.trim().isEmpty()) continue;
			
			ArrayList<?> triples = (ArrayList<?>) DSNFTool.newInstance().extractTriple(s);
			if(triples.size() > 0) {
				for(Object triple: triples) {
					@SuppressWarnings("unchecked")
					final HashMap<String, Object> map = (HashMap<String, Object>) triple;
					final DSNFKnowledge kn = new DSNFKnowledge(map);
					knowledgeList.add(kn);
//					System.out.println(kn);
				}
			}
			
		}
	}
	
	public boolean equals(ASSentence other) {
		return sentence.equals(other.sentence);
	}
	
	public String toString() {
		return sentence;
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
	}
}
