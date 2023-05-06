package spart.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-28 17:46:34
 */
public class SearchResult {
	private List<ResultPair> resultList;
	
	public SearchResult(List<ResultPair> resultList) {
		this.resultList = resultList;
		this.resultList.sort(new Comparator<ResultPair>() {

			public int compare(ResultPair o1, ResultPair o2) {
				return o1.getScore() - o2.getScore() > 0 ? -1 : 1;
			}

		});
	}
	
	public List<ResultPair> getResultList() {
		return resultList;
	}
	
	public List<ResultPair> first(int length) {
		List<ResultPair> list = new ArrayList<ResultPair>();
		for(int i=0; i<length && i<resultList.size(); ++i) {
			list.add(resultList.get(i));
		}
		
		return list;
	}
	
	public List<ResultPair> filter(float minScore) {
		List<ResultPair> list = new ArrayList<ResultPair>();
		for(ResultPair r: resultList) {
			if(r.getScore() >= minScore)
				list.add(r);
		}
		
		return list;
	}
	
	public String toString() {
		return resultList.toString();
	}
	
	public String toJson() {
		return toJson(getResultList());
	}
	
	public String toJson(List<ResultPair> resultPairs) {
		JSONArray array = new JSONArray();
		for(ResultPair pair: resultPairs) {
			JSONObject object = new JSONObject();
			object.put("result", pair.getResult().toString());
			object.put("score", String.format("%.2f", pair.getScore()));
			
			array.add(object);
		}
		
		return array.toString();
	}
	
}
