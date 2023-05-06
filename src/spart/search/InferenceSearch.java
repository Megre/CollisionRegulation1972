package spart.search;

import java.util.ArrayList;
import java.util.List;

import spart.parser.ASBlock;
import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-05 11:30:34
 */
public class InferenceSearch extends SearchMethod {

	private static List<ASInference> defaultInferences = new ArrayList<ASInference>();
	static {
		registerInference("左舷受风的船应给他船让路", "两船在不同舷受风");
		registerInference("上风船应给下风船让路", "两船在同舷受风");
		
		registerInference("船舶应给他船让路", "两船交叉相遇", "有碰撞危险", "有他船在本船右舷");
		registerInference("应尽可能及早地采取大幅度的行动", "船舶应给他船让路");
	}
	
	private List<ASSentence> conditions;
	private List<ResultPair> resultList;
	
	public InferenceSearch(List<ASBlock> blockList) {
		super(blockList);
	}
	
	public static void registerInference(String conclusion, String... conditions) {
		defaultInferences.add(new ASInference(conclusion, conditions));
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
		
		for(ASInference infer: defaultInferences) {
			if(infer.infer(conditions)) {
				if(appendCondition(infer.getConclusion())) {
					resultList.add(new ResultPair(infer.getConclusion(), infer.getScore()));
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
		String[] condiArray = search.split("[；;，,、]");
		
		conditions = new ArrayList<ASSentence>();
		for(String condi: condiArray) {
			conditions.add(new ASSentence(condi));
		}
	}
	


}
