package spart.search;

import java.io.InputStream;
import java.util.List;

import com.lingjoin.demo.NlpirMethod;
import spart.parser.ASBlock;
import spart.parser.TextFileParser;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 11:17:04
 */
public class SearchEngine {
	
	private static SearchEngine instance = new SearchEngine();
	
	private List<ASBlock> blockList;

	private SearchEngine() {
		
	}
	
	public static SearchEngine initiate(String textFilePath) {
		TextFileParser parser = new TextFileParser(textFilePath);
		instance.blockList = parser.parse();
		return instance;
	}
	
	public static SearchEngine initiate(InputStream inputStream) {
        TextFileParser parser = new TextFileParser(inputStream);
        instance.blockList = parser.parse();
        return instance;
    }
	
	public static void exit() {
		NlpirMethod.NLPIR_Exit();
	}
	
	public static SearchEngine instance() {
		return instance;
	}
	
	public SearchResult semanticSearch(String search) {
		return new SemanticSearch(blockList).search(search);
	}
	
	public SearchResult smartQA(String search) {
		return new SmartSearch(blockList).search(search);
	}
	
	public SearchResult regulationInfer(String search) {
		return new InferenceSearch(blockList).search(search);
	}
}
