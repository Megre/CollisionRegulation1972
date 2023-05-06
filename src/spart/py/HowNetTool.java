package spart.py;

import spart.nlpir.NlpirWord;
import spart.nlpir.NlpirWordType;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-15 23:03:54
 */
public class HowNetTool {
	
	private static HowNetTool howNetTool = new HowNetTool();
	private static PyTool instance = PyTool.newInstance();

	static {
		howNetTool.init();
	}
	
	private HowNetTool() { }
	
	public static HowNetTool newInstance() {
		return howNetTool;
	}
	
	public void init() {
		instance.exec("import OpenHowNet");
		instance.exec("dict = OpenHowNet.HowNetDict()");
		instance.exec("dict.initialize_similarity_calculation()");
	}
	
	public float similarity(NlpirWord word1, NlpirWord word2) {
		if(word1.equals(word2)) return 1;
		
		NlpirWordType type1 = word1.getType(),
				type2 = word2.getType();
		if(!type1.isComparable(type2)) return 0;
			
		return similarity(word1.getText(), word2.getText());
	}
	
	public float similarity(String word1, String word2) {
		if(word1.equals(word2)) return 1;
		
		Object result = instance.invoke("dict.calculate_word_similarity", word1, word2);
		return Float.parseFloat(result.toString());
	}
	
	private static void testOpenHowNet() { 
		instance.exec("import OpenHowNet");
//		tool.exec("OpenHowNet.download()");
		instance.exec("hownet_dict = OpenHowNet.HowNetDict()");
		instance.exec("result_list = hownet_dict.get_sense(\"的\")");
		
		Object result = instance.getValue("result_list");
		System.out.println(result);
		
		instance.exec("hownet_dict.initialize_similarity_calculation()");
		result = instance.getValue("hownet_dict.calculate_word_similarity(\"男人\", \"父亲\")");
		System.out.println(result);
		
		result = instance.invoke("hownet_dict.calculate_word_similarity", "船", "舟");
		System.out.println(result);
	}


	public static void main(String[] args) {
		testOpenHowNet();
	}
	

}
