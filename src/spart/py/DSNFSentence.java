package spart.py;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-19 16:36:07
 */
public class DSNFSentence {

	public DSNFWord[] words;
	
	public static DSNFSentence fromCoNLLSentence(CoNLLSentence sentence) {
		DSNFSentence s = new DSNFSentence();
		
		final CoNLLWord[] wordArray = sentence.getWordArray();
		s.words = new DSNFWord[wordArray.length];
		for(int idx=0; idx<s.words.length; ++idx) {
			s.words[idx] = DSNFWord.fromCoNLLWord(wordArray[idx]);
		}
				
		return s;			
	}
	
	private static void testDsnf() {
		final String text = "“机动船”一词，指用机器推进的任何船舶";
		final CoNLLSentence sentence = HanLP.parseDependency(text);
		final DSNFSentence dnsfSentence = DSNFSentence.fromCoNLLSentence(sentence);
		
		for(DSNFWord w: dnsfSentence.words) {
			System.out.println(w.ID + "\t" + w.lemma + "\t" + w.postag + "\t" + w.head + "\t" + w.dependency);
		}
		
		PyTool instance = PyTool.newInstance();
		instance.exec("from dsnf.core.extractor import Extractor");
		instance.exec(String.format("extractor = Extractor('%s')", "D:/knowledge_triple.json"));
		
		@SuppressWarnings("unused")
		Object rst = instance.invoke("extractor.get_entities", dnsfSentence);
		rst = instance.invoke("extractor.get_entity_pairs", dnsfSentence);
		rst = instance.invoke("extractor.extract_sentence", text , dnsfSentence);

	}
	
	public static void main(String[] args) {
		testDsnf();
	}
}
