package spart.nlpir;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-17 14:11:21
 */
public class NlpirWordType {
	private String typeID;
	
	public NlpirWordType(String type) {
		this.typeID = type;
	}
	
	/**
	 * 判断实词. 前缀, 后缀, 字符串, 标点不包括在内.
	 * @return
	 */
	public boolean isNotional() {
		return isNoun()
				|| isTime()
				|| isPlace()
				|| isPosition()
				|| isState()
				|| isVerb()
				|| isAdj()
				|| isNum()
				|| isQuantifier()
				|| isPronoun()
				|| isAdv()
				|| isInterj()
				|| isMimetic()
				|| isDistinguishing();
	}
	
	/**
	 * 判断虚词. 前缀, 后缀, 字符串, 标点不包括在内.
	 * @return
	 */
	public boolean isFunction() {
		return isPrep()
				|| isConj()
				|| isAux()
				|| isModalParticle();
	}
	
	public boolean isComparable(NlpirWordType type) {
		return this.equals(type)
				|| (isNotional() && type.isNotional())
				|| (isFunction() && type.isFunction());
	}
	
	public String getTypeID() {
		return typeID;
	}
	
	public String toString()  {
		return typeID;
	}
	
	public boolean equals(NlpirWordType wordType) {
		return this.typeID.equals(wordType.typeID);
	}
	
	public boolean isNoun() {
		return typeID.startsWith("n");
	}
	
	public boolean isTime() {
		return typeID.startsWith("t");
	}
	
	public boolean isPlace() {
		return typeID.startsWith("s");
	}
	
	public boolean isPosition() {
		return typeID.startsWith("f");
	}
	
	public boolean isVerb() {
		return typeID.startsWith("v");
	}
	
	public boolean isAdj() {
		return typeID.startsWith("a");
	}
	
	/**
	 * 区别词
	 * @return
	 */
	public boolean isDistinguishing() {
		return typeID.startsWith("b");
	}
	
	/**
	 * 状态词
	 * @return
	 */
	public boolean isState() {
		return typeID.startsWith("z");
	}
	
	/**
	 * 代词
	 * @return
	 */
	public boolean isPronoun() {
		return typeID.startsWith("r");
	}
	
	/**
	 * 数词
	 * @return
	 */
	public boolean isNum() {
		return typeID.startsWith("m");
	}
	
	/**
	 * 量词
	 * @return
	 */
	public boolean isQuantifier() {
		return typeID.startsWith("q");
	}
	
	public boolean isAdv() {
		return typeID.startsWith("d");
	}
	
	/**
	 * 介词
	 * @return
	 */
	public boolean isPrep() {
		return typeID.startsWith("p");
	}
	
	/**
	 * 连词
	 * @return
	 */
	public boolean isConj() {
		return typeID.startsWith("c");
	}
	
	/**
	 * 助词
	 * @return
	 */
	public boolean isAux() {
		return typeID.startsWith("u");
	}
	
	/**
	 * 叹词
	 * @return
	 */
	public boolean isInterj() {
		return typeID.startsWith("e");
	}
	
	/**
	 * 语气词
	 * @return
	 */
	public boolean isModalParticle() {
		return typeID.startsWith("y");
	}
	
	/**
	 * 拟声词
	 * @return
	 */
	public boolean isMimetic() {
		return typeID.startsWith("o");
	}
	
	/**
	 * 标点
	 * @return
	 */
	public boolean isPunctuation() {
		return typeID.startsWith("w");
	}
	
	public boolean isString() {
		return typeID.startsWith("x");
	}
	
	public boolean isPrefix() {
		return typeID.startsWith("h");
	}
	
	public boolean isPostfix() {
		return typeID.startsWith("h");
	}
	

}
