package spart.py;

import jep.SharedInterpreter;

/** 
 * 
 * @author megre
 * @email megre@vip.qq.com
 * @version created on: 2023-04-15 20:15:18
 */
public class PyTool {
	
	private static SharedInterpreter interpreter = new SharedInterpreter();;
	private static PyTool pyTool = new PyTool();
	
	private PyTool() {

	}
	
	public static PyTool newInstance() {		
		return pyTool;
	}
	
	public Object getValue(String str) {
		return interpreter.getValue(str);
	}
	
	public <T> T getValue(String str, Class<T> clazz) {
		return interpreter.getValue(str, clazz);
	}
	
	public void exec(String cmd) {
		interpreter.exec(cmd); 
	}
	
	public Object invoke(String name, Object... args) {
		return interpreter.invoke(name, args);
	}
	
	
}
