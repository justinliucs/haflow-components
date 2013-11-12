package haflow.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
	public String input="/haflow/component/stop";
	private Map<String,Integer> stopDict;
	private static final Dictionary singleton;
	static{
		singleton=new Dictionary();
	}
	public static Dictionary getInstance(){
		return singleton;
	}
	private Dictionary(){
		loadStopDict();
	}
	private void loadStopDict(){
		stopDict=new HashMap<String,Integer>();
		InputStream is=Dictionary.class.getResourceAsStream(input);
		//File inputFile=new File(input);
		//System.out.println(inputFile.getPath());
		if(is==null){
			throw new RuntimeException("stop dictionary is not found!");
		}
		try{
			BufferedReader bf=new BufferedReader(new InputStreamReader(is));
			String line=null;
			while((line=bf.readLine())!=null){
				if(line.trim()!=""&&!stopDict.containsKey(line.trim())){
					stopDict.put(line.trim(), 1);
				}
			}
		} catch (IOException ioe) {  
            System.err.println("Main Dictionary loading exception.");  
            ioe.printStackTrace();  
		}
	}
	public boolean isStopWord(String word){
		if(stopDict.containsKey(word)){
			return true;
		}else
			return false;
	}
}
