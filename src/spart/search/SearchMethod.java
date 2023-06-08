package spart.search;

import java.util.List;

import spart.parser.ASBlock;
import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 11:34:49
 */
public abstract class SearchMethod {
	
	protected List<ASBlock> blockList;
	protected SearchEngine searchEngine;
	
	public SearchMethod(SearchEngine searchEngine) {
		this.blockList = searchEngine.getBlockList();
		this.searchEngine = searchEngine;
	}
	
	public abstract SearchResult search(String text);
	
	protected ASSentence retrieveSentence(ASBlock block) {
		ASSentence sentence = null;
		
		if(block instanceof ASSentence) {
			 sentence = (ASSentence) block;
		}
		
		return sentence;
	}

}
