package spart.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import spart.parser.ASSentence;

/** 
 * condition1, condition2, ... -> conclusion
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-05 11:39:08
 */
public class ASFact {

	private static float minScore = 0.9f;
	
	private List<ASSentence> conditions;
	private ASSentence conclusion;
	private float score;
	
	public ASFact(String conclusion, String... conditions) {
		this.conclusion = new ASSentence(conclusion);
		buildSentences(conditions);
	}
	
	public List<ASSentence> getConditions() {
		return conditions;
	}
	
	public ASSentence getConclusion() {
		return conclusion;
	}
	
	public float getScore() {
		return score;
	}
	
	public boolean infer(Set<ASSentence> otherConditions) {
		if(conditions.size() > otherConditions.size()) return false;
	
		for(ASSentence condi: conditions) {
			score = 0f;
			for(ASSentence otherCondi: otherConditions) {
				score = Math.max(score, condi.senseSimilarity(otherCondi));
			}
			if(score < minScore) return false;
		}
		
		return true;
	}
	
	private void buildSentences(String... conditions) {
		this.conditions = new ArrayList<ASSentence>();
		
		for(String condi: conditions) {
			this.conditions.add(new ASSentence(condi));
		}
	}
}
