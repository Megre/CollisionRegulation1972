package spart.py;

import java.util.List;
import java.util.Map;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 14:49:14
 */
public class DSNFKnowledge {

	private Map<String, Object> map;
	
	public DSNFKnowledge(Map<String, Object> map) {
		this.map = map;
	}
	
	public long num() {
		return (Long) map.get("编号");
	}
	
	public String text() {
		return map.get("句子").toString();
	}
	
	@SuppressWarnings("unchecked")
	public DSNFTriple triple() {
		return new DSNFTriple((List<Object>) map.get("知识"));
	}
	
	public String type() {
		return map.get("类型").toString();
	}
	
	public String toString() {
		return map.toString();
	}
}
