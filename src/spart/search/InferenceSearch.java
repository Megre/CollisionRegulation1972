package spart.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-05 11:30:34
 */
public class InferenceSearch extends SearchMethod {

	private static List<ASFact> facts = new ArrayList<ASFact>();
	static {
		registerFact("左舷受风的船应给他船让路", "两船在不同舷受风");
		registerFact("上风船应给下风船让路", "两船在同舷受风");
		
		registerFact("船舶应给他船让路", "两船交叉相遇", "有碰撞危险", "有他船在本船右舷");
		registerFact("应尽可能及早地采取大幅度的行动", "船舶应给他船让路");
	}
	
	private Set<ASSentence> conditions;
	private List<ResultPair> resultList;
	
	public InferenceSearch(SearchEngine searchEngine) {
		super(searchEngine);
	}
	
	public static void registerFact(String conclusion, String... conditions) {
		facts.add(new ASFact(conclusion, conditions));
	}

	@Override
	public SearchResult search(String search) {
		makeConditions(search);		
		resultList = new ArrayList<ResultPair>();
		
		while(search()) {
			search();
		}
		
		return new SearchResult(resultList);
	}
	
	private boolean search() {
		boolean appendFlag = false;
		
		for(ASFact fact: facts) {
			if(fact.infer(conditions)) {
				if(appendCondition(fact.getConclusion())) {
					resultList.add(new ResultPair(fact.getConclusion(), fact.getScore()));
					appendFlag = true;
				}
			}
		}
		
		return appendFlag;
	}
	
	private boolean appendCondition(ASSentence conclusion) {
		if(conditions.contains(conclusion)) return false;
		
		conditions.add(conclusion);
		return true;
	}
	
	private void makeConditions(String search) {
		String[] condiArray = search.split(ASSentence.SPLITTER);
		
		conditions = new HashSet<ASSentence>();
		for(String condi: condiArray) {
			conditions.add(new ASSentence(condi));
		}
	}
	


}
