package spart.nlpir;

import java.util.HashMap;
import java.util.Map;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

import spart.parser.ASSentence;
import spart.py.HowNetTool;

/** 
 * 
 * Triple = (p, q, r)
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-18 09:44:38
 */
public class StructureSimilarity {

	private CoNLLSentence sA, sB;
	private float similarity = -1;
	private float[][] simMatrix;
	private float[] tmaxA, tmaxB;
	private Map<String, Float> weightMap = new HashMap<String, Float>();
	private int lenA, lenB;

	public StructureSimilarity(CoNLLSentence s1, CoNLLSentence s2) {
		this.sA = s1;
		this.sB = s2;
	}
	
	public float calculate() {
		if(similarity >= 0) return similarity;
		
		buildSimMatrix();
		buildWeightMap();
		
		float wA = 0, wB = 0, 
				twA = 0, twB = 0;
		for(int i=1; i<=lenA; ++i) {
			final float wi = w(rA(i));
			twA += (wi * tmaxA[i]);
			wA += wi;
		}
		for(int j=1; j<=lenB; ++j) {
			final float wj = w(rB(j));
			twB += (wj * tmaxB[j]);
			wB += wj;
		}
		
		return similarity = ((twA/wA + twB/wB) / 2);
	}
	
	private void buildSimMatrix()  {
		lenA = sA.getWordArray().length;
		lenB = sB.getWordArray().length;		
		
		simMatrix = new float[lenA+1][];
		for(int i=1; i<=lenA; ++i) {
			simMatrix[i] = new float[lenB+1];
			for(int j=1; j<=lenB; ++j) {
				simMatrix[i][j] = tsim(i, j);
			}
		}
		
		buildTMax(lenA, lenB);
	}
	
	private void buildTMax(int lenA, int lenB) {
		tmaxA = new float[lenA+1];
		tmaxB = new float[lenB+1];
		for(int i=1; i<=lenA; ++i) {
			tmaxA[i] = 0;
			for(int j=1; j<=lenB; ++j) {
				tmaxA[i] = Math.max(tmaxA[i], simMatrix[i][j]);
			}
		}
		for(int j=1; j<=lenB; ++j) {
			tmaxB[j] = 0;
			for(int i=1; i<=lenA; ++i) {
				tmaxB[j] = Math.max(tmaxB[j], simMatrix[i][j]);
			}
		}
	}
	
	private void buildWeightMap() {
		weightMap.put("主谓关系", 0.433f);
		weightMap.put("动宾关系", 0.347f);
		weightMap.put("间宾关系", 0.347f);
		weightMap.put("前置宾语", 0.347f);
		weightMap.put("兼语", 0.347f);
		weightMap.put("other", 0.250f);
	}
	
	private float w(String r) {
		if(weightMap.containsKey(r)) return weightMap.get(r);
		
		return weightMap.get("other");
	}
	
	/**
	 * 
	 * @param i: word ID of sentence A, starting from 1
	 * @param j: word ID of sentence A, starting from 1
	 * @return
	 */
	private int rsim(int i, int j) { 
		return rA(i).equals(rB(j)) ? 1 : 0;
	}
	
	private float tsim(int i, int j) {
		return (float) (Math.sqrt(psim(i, j) * qsim(i, j)) * rsim(i, j));
	}
	
	private float psim(int i, int j) {
		return wordSim(pA(i), pB(j));
	}
	
	private float qsim(int i, int j) {
		return wordSim(qA(i), qB(j));
	}
	
	private CoNLLWord tA(int i) {
		return sA.getWordArray()[i-1];
	}
	
	private CoNLLWord tB(int i) {
		return sB.getWordArray()[i-1];
	}

	private String rA(int i) { 
		return tA(i).DEPREL;
	}
	
	private String rB(int i) { 
		return tB(i).DEPREL;
	}
	
	private String pA(int i) {
		return tA(i).LEMMA;
	}
	
	private String pB(int i) {
		return tB(i).LEMMA;
	}
	
	private String qA(int i) {
		int head = tA(i).HEAD.ID;
		if(head == 0) return "root";
		
		return tA(head).LEMMA;
	}
	
	private String qB(int i) {
		int head = tB(i).HEAD.ID;
		if(head == 0) return "root";
		
		return tB(head).LEMMA;
	}
	
	private float wordSim(String w1, String w2) {
		float r = HowNetTool.newInstance().similarity(w1, w2);
		if(r < 0) {
			r = new ASSentence(w1).senseSimilarity(new ASSentence(w2));
		}
		
		return r < 0 ? 0 : r;
	}
	
	public static void main(String[] args) {
		CoNLLSentence s1 = HanLP.parseDependency("他喝了一打汽水."),
				s2 = HanLP.parseDependency("他喝了一打汽水.");
		System.out.println(new StructureSimilarity(s1, s2).calculate());
		
		s2 = HanLP.parseDependency("我买了一瓶汽水.");
		System.out.println(new StructureSimilarity(s1, s2).calculate());
		
		s2 = HanLP.parseDependency("我买了一打汽水.");
		System.out.println(new StructureSimilarity(s1, s2).calculate());
		
		s2 = HanLP.parseDependency("我喝了一打汽水.");
		System.out.println(new StructureSimilarity(s1, s2).calculate());
	}
}
