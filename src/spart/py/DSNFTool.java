package spart.py;

import java.io.File;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-25 13:47:07
 */
public class DSNFTool {
	
	private static DSNFTool dsnfTool = new DSNFTool();
	private static PyTool instance = PyTool.newInstance();
	private static String outputPath = "./knowledge_triple.json";
	
	static {
		dsnfTool.init();
	}
	
	private DSNFTool() { }

	public static DSNFTool newInstance() {
		return dsnfTool; 
	}
	
	public void init() {
		File file = new File(outputPath);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
		
		instance.exec("from dsnf.core.extractor import Extractor");
		instance.exec(String.format("extractor = Extractor('%s')", outputPath));
	}
	
	public Object extractTriple(String text) {
		return instance.invoke("extractor.extract_text", text);
	}
	
	public Object extractHanLPTriple(String text) {
		final CoNLLSentence sentence = HanLP.parseDependency(text);		
		final DSNFSentence dnsfSentence = DSNFSentence.fromCoNLLSentence(sentence);
		
		return instance.invoke("extractor.extract_sentence", text, dnsfSentence);
	}
	
	public Object getEntities(CoNLLSentence sentence) {
		final DSNFSentence dnsfSentence = DSNFSentence.fromCoNLLSentence(sentence);
		
		return instance.invoke("extractor.get_entities", dnsfSentence);
	}
	
	public Object getEntityPairs(CoNLLSentence sentence) {
		final DSNFSentence dnsfSentence = DSNFSentence.fromCoNLLSentence(sentence);
		
		return instance.invoke("extractor.get_entity_pairs", dnsfSentence);
	}
}
