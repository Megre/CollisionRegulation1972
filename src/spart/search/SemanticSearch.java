package spart.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import spart.nlpir.HanlpTool;
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
	private CoNLLWord coreWord;
	private CoreWordMap coreWordMap;
	
	public SemanticSearch(SearchEngine searchEngine) {
		super(searchEngine);
		coreWordMap = searchEngine.getCoreWordMap();
	}

	public SearchResult search(String search) {
		this.coreWord = HanlpTool.findRoot(HanlpTool.parseDependency(search));
		this.search = new ASSentence(search);
		this.resultList = new ArrayList<ResultPair>();
		
		List<ASSentence> searchList = coreWordMap.get(this.coreWord);
		for(ASSentence st: searchList) {
			final ASSentence contextSentence = (ASSentence) (st.getParent() instanceof ASSentence ? st.getParent() : st);
			
			float score = searchParts(contextSentence); 
			if(score <= 0) continue;
			
			this.resultList.add(new ResultPair(contextSentence, score));
		}
		
		if(this.resultList.size() == 0) {
			senseSearch();
		}
		
		return new SearchResult(this.resultList);
	}
	
	private void senseSearch() {
		for(ASBlock block: blockList) {
			searchSentence(block);
		}
	}
	
	private void searchSentence(ASBlock block) {
		if(block instanceof ASSentence) {
			ASSentence st = (ASSentence) block;
			final float score = st.senseSimilarity(search);
			if(score > 0) {
				resultList.add(new ResultPair(st, score));
			}
		}
		
		for(ASBlock child: block.getContentList()) {
			searchSentence(child);
		}
	}
	
	private Map<ASSentence, Boolean> cache = new HashMap<ASSentence, Boolean>();
	private float searchParts(ASSentence targetSentence) {
		if(cache.containsKey(targetSentence)) return -1.0f;
		else cache.put(targetSentence, true);
		
		float score = 0;
		for(ASSentence part: targetSentence.getParts()) {
			score = Math.max(0, part.semanticSimilarity(search));
			if(score >= 1f) break;
		}
		return score;
	}
}
