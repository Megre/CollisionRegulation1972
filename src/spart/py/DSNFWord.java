package spart.py;

import java.util.HashMap;
import java.util.Map;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-19 16:46:37
 */
public class DSNFWord {
    public int ID;
    public String lemma;
    public String postag;
    public int head;
    public DSNFWord head_word;
    public String dependency;
    
    public static DSNFWord fromCoNLLWord(CoNLLWord word) {
    	DSNFWord result = new DSNFWord();
    	result.ID = word.ID;
    	result.lemma = word.LEMMA;
    	result.postag = word.CPOSTAG;
    	result.head = (word.HEAD==null?0:word.HEAD.ID);
    	result.head_word = (word.HEAD==null?null:fromCoNLLWord(word.HEAD));
    	result.dependency = enDepRel(word);
    	
    	return result;
    }
    
    private static Map<String, String> depMap = new HashMap<String, String>();
    static {
    	depMap.put("定中关系", "ATT");
    	depMap.put("数量关系", "QUN");
    	depMap.put("并列关系", "COO");
    	depMap.put("同位关系", "APP");
    	depMap.put("核心关系", "HED");
    	depMap.put("标点符号", "WP");
    	depMap.put("核心关系", "HED");
    	depMap.put("主谓关系", "SBV");
    	depMap.put("状中结构", "ADV");
    	depMap.put("介宾关系", "POB");
    	depMap.put("动宾关系", "VOB");
    	depMap.put("间宾关系", "IOB");
    	depMap.put("前置宾语", "FOB");
    	depMap.put("兼语", "DBL");
    	depMap.put("动补结构", "CMP");
    	depMap.put("左附加关系", "LAD");
    	depMap.put("右附加关系", "RAD");
    	depMap.put("独立结构", "IS");
    }
    
    public static String enDepRel(CoNLLWord word ) {
    	return depMap.get(word.DEPREL);
    }
}
