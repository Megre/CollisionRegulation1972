package spart.search;

import java.util.ArrayList;
import java.util.List;
import spart.parser.ASBlock;
import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 11:32:40
 */
public class SemanticSearch extends SearchMethod {
	
	private ASSentence search;
	private List<ResultPair> resultList;
	private final float minScore = 0.7f;
	
	public SemanticSearch(List<ASBlock> blockList) {
		super(blockList);
	}

	public SearchResult search(String search) {
		this.search = new ASSentence(search);
		resultList = new ArrayList<ResultPair>();
		
		for(ASBlock block: blockList) {
			search(block);
		}
		
		return new SearchResult(resultList);
	}
	
	private void search(ASBlock block) {
		ASSentence sentence = retrieveSentence(block);
		
		if(sentence != null) {
			float score = sentence.senseSimilarity(search);
			if(score > minScore) {
				resultList.add(new ResultPair(sentence, score));
			}
		}
		
		List<ASBlock> contentList = block.getContentList();
		for(ASBlock child: contentList) {
			search(child);
		}
	}
}
