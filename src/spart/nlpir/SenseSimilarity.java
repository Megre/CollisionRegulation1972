package spart.nlpir;

import java.util.List;

import spart.parser.ASSentence;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-18 09:36:20
 */
public class SenseSimilarity {
	private ASSentence s1, s2;
	
	private float[][] similarityMatrix;
	private List<NlpirWord> words1, words2;

	public SenseSimilarity(ASSentence s1, ASSentence s2) {
		this.s1 = s1;
		this.s2 = s2;
	}
	
	public float calculate() {
		if(s1.equals(s2)) return 1;
		
		words1 = s1.getWords();
		words2 = s2.getWords();
		final int rowNum = words1.size(), 
				columnNum = words2.size();
		initSimilarityMatrix(rowNum, columnNum);
		
		float partA = 0, partB = 0;
		int countA = rowNum, countB = columnNum;
		for(int row=0; row<rowNum; ++row) {
			float result = 0;
			for(int column=0; column<columnNum; ++column) {
				result = Math.max(result, similarity(row, column));
			}
			partA += result;
		}
		
		for(int column=0; column<columnNum; ++column) {
			float result = 0;
			for(int row=0; row<rowNum; ++row) {
				result = Math.max(result, similarity(row, column));
			}
			partB += result;
		}
		
		return (partA/countA + partB/countB) / 2;
	}

	private float similarity(int row, int column) {
		if(similarityMatrix[row][column] >= 0)
			return similarityMatrix[row][column];
		
		float s = words1.get(row).similarity(words2.get(column));
		similarityMatrix[row][column] = s;
		
		return s;
	}
		
	private void initSimilarityMatrix(int rowNum, int columnNum) {
		similarityMatrix = new float[rowNum][];
		for(int r=0; r<rowNum; ++r) {
			similarityMatrix[r] = new float[columnNum];
			for(int c=0; c<columnNum; ++c) {
				similarityMatrix[r][c] = -1;
			}
		}
	}
}
