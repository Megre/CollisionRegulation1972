package spart.nlpir;

import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-18 09:50:09
 */
public class HanlpTool {
	
	public static CoNLLWord findRoot(CoNLLSentence sentence) {
		for(CoNLLWord word: sentence.getWordArray()) {
			if(word.HEAD.ID == 0) return word;
		}
		
		return null;
	}
	
	public static List<CoNLLWord> coreDependency(CoNLLSentence sentence) {
		List<CoNLLWord> list = new ArrayList<CoNLLWord>();
		
		CoNLLWord root = findRoot(sentence);
		list.add(root);
		for(CoNLLWord child: sentence.findChildren(root)) {
			if(!child.DEPREL.equals("标点符号")) {
				list.add(child);
			}
		}
		return list;
	}
	
	private static void printSentence(CoNLLSentence sentence) {
		System.out.println(sentence);
	}
	
	private static void printRoot(CoNLLSentence sentence) {
		CoNLLWord root = findRoot(sentence);
		System.out.println(root);
		for(CoNLLWord child: sentence.findChildren(root)) {
			System.out.println(child);
		}
	}
	
	public static void main(String[] args) {
		String text = "做完了作业.";
		
		CoNLLSentence result = HanLP.parseDependency(text);
		printSentence(result);
		printRoot(result);
		
		System.out.println(new ASSentence(text).getWords());
		
		text = "船舶包括什么";
		result = HanLP.parseDependency(text);
		printSentence(result);
		printRoot(result);
		
	}
}
