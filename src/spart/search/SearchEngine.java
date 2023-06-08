package spart.search;

import java.io.InputStream;
import java.util.List;

import com.lingjoin.demo.NlpirMethod;

import spart.parser.ASBlock;
import spart.parser.ASSentence;
import spart.parser.TextFileParser;
import spart.py.HowNetTool;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 11:17:04
 */
public class SearchEngine {
	
	private static SearchEngine instance = new SearchEngine();
	
	private List<ASBlock> blockList;
	private CoreWordMap coreWordMap;

	private SearchEngine() {
		coreWordMap = new CoreWordMap();
	}
	
	public static SearchEngine initiate(String textFilePath) {
		return initiate(new TextFileParser(textFilePath));
	}
	
	public static SearchEngine initiate(InputStream inputStream) {    
        return initiate(new TextFileParser(inputStream));
    }
	
	private static SearchEngine initiate(TextFileParser fileParser) {
        instance.blockList = fileParser.parse();
        
        // This may take more than 1 minute
        System.out.println("preprocessing...");
        instance.preprocess();
        HowNetTool.newInstance();
        System.out.println("preprocess finished");
        
        return instance;
	}
	
	public static void exit() {
		NlpirMethod.NLPIR_Exit();
	}
	
	public static SearchEngine instance() {
		return instance;
	}
	
	public List<ASBlock> getBlockList() {
		return blockList;
	}
	
	public CoreWordMap getCoreWordMap() {
		return coreWordMap;
	}
	
	public SearchResult semanticSearch(String search) {
		return new SemanticSearch(this).search(search);
	}
	
	public SearchResult smartQA(String search) {
		return new SmartSearch(this).search(search);
	}
	
	public SearchResult regulationInfer(String search) {
		return new InferenceSearch(this).search(search);
	}
	
	private void preprocess() {
		for(ASBlock block: blockList) {
			preprocess(block);
		}
	}
	
	private void preprocess(ASBlock block) {
		
		if(block instanceof ASSentence) {
			final ASSentence sentence = ((ASSentence) block);
			sentence.preprocess();
			
			coreWordMap.put(sentence);
		}
		
		for(ASBlock child: block.getContentList()) {
			preprocess(child);
		}
	}
}
