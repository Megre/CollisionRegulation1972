package spart.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

import spart.parser.ASSentence;
import spart.py.HowNetTool;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-05-08 13:32:31
 */
public class CoreWordMap {

	private Map<String, List<ASSentence>> map;
	
	public CoreWordMap() {
		map = new HashMap<String, List<ASSentence>>();
	}
	
	public void put(ASSentence sentence) {
		for(ASSentence st: sentence.getParts()) {
			CoNLLWord word = st.getCoreWord();
			List<ASSentence> list = map.get(word.LEMMA);
			
			if(list == null) {
				list = new ArrayList<ASSentence>();
				map.put(word.LEMMA, list);
			}
			
			list.add(st);
		}
	}
	
	public List<ASSentence> get(CoNLLWord word) {
		List<ASSentence> list = map.get(word.LEMMA);
		if(list != null) return list;
		
		list = new ArrayList<ASSentence>();
		for(String key: map.keySet()) {
			if(HowNetTool.newInstance().similarity(word.LEMMA, key) >= 1.0f) {
				list.addAll(map.get(key));
			}
		}
		
		if(list.size() > 0) {
			map.put(word.LEMMA, list);
		}
		
		return list;
	}
}
