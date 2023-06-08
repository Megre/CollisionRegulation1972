package spart.search;

import java.util.ArrayList;
import java.util.List;

import spart.parser.ASBlock;
import spart.parser.ASSentence;
import spart.py.DSNFKnowledge;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-05 09:52:08
 */
public class SmartSearch extends SearchMethod {
	private ASTriple search;
	private List<ResultPair> resultList;

	public SmartSearch(SearchEngine searchEngine) {
		super(searchEngine);
	}
	
	@Override
	public SearchResult search(String search) {
		this.search = ASTriple.make(search);
		resultList = new ArrayList<ResultPair>();
		
		for(ASBlock block: blockList) {
			search(block);
		}
		
		return new SearchResult(resultList);
	}
	
	private void search(ASBlock block) {
		ASSentence sentence = retrieveSentence(block);
		
		if(sentence != null && sentence.getKnowledgeList().size() > 0) {
			List<DSNFKnowledge> knList = sentence.getKnowledgeList();
			for(DSNFKnowledge kn: knList) {
				ASTriple triple = ASTriple.fromDSNFTriple(kn.triple());
				if(triple.match(this.search)) {
					resultList.add(new ResultPair(triple.matchedThing(), -1f));
				}
			}
		}
		
		List<ASBlock> contentList = block.getContentList();
		for(ASBlock child: contentList) {
			search(child);
		}
	}
	
	

}
