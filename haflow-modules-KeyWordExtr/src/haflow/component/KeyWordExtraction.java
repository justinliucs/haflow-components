package haflow.component;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;



public class KeyWordExtraction {
	private Map<String,Integer> dictionary;
	private int wordCount;
	
	public KeyWordExtraction(){
		this.dictionary=new HashMap<String,Integer>();
		this.wordCount=0;
	}
	
	public void insert(String key){
		wordCount++;
		if(dictionary.containsKey(key)){
			int num=dictionary.get(key);
			dictionary.put(key, num+1);
			
		}
		else{
			dictionary.put(key, 1);
		}
	}
	
	public int getAllWordsNum(){
		return this.wordCount;
	}
	public int getDiffWordsNum(){
		return this.dictionary.size();
	}
	public int rectifyDiffWordsNum(){
		if(this.getDiffWordsNum()<50)
			return this.getDiffWordsNum();
		else
			return 50;
	}
	public List<Map.Entry<String, Integer>> sort(){
		List<Map.Entry<String, Integer>> dicList = new ArrayList<Map.Entry<String, Integer>>(this.dictionary.entrySet()); 
		Collections.sort(dicList, new Comparator<Map.Entry<String, Integer>>() {  
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return (o2.getValue()).toString().compareTo(o1.getValue().toString());	
			}
		 });
		return dicList;
	}
	public byte[] readHdfsFile(String input)throws Exception{
		Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        
        // check if the file exists
        Path path = new Path(input);
        if ( fs.exists(path) )
        {
            FSDataInputStream is = fs.open(path);
            // get the file info to create the buffer
            FileStatus stat = fs.getFileStatus(path);
            
            // create the buffer
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
            is.readFully(0, buffer);
            //System.out.println(buffer);
            is.close();
            fs.close();
            
            return buffer;
        }
        else
        {
            throw new Exception("the file is not found:"+input);
        }
	}
	   public void createNewHDFSFile(String toCreateFilePath, String content) throws IOException
	    {
	        Configuration config = new Configuration();
	        FileSystem hdfs = FileSystem.get(config);
	        
	        FSDataOutputStream os = hdfs.create(new Path(toCreateFilePath));

	        os.write(content.getBytes("UTF-8"));
	        
	        os.close();
	        
	        hdfs.close();
	    }
	
	public static void main(String[] args){
		String  inputFile=null;
		String outputFile=null;
		boolean flag=false;
		boolean flag1=false;
		try {
			for (int i = 0; i < args.length; i = i + 2) {
				String key = args[i];
				String value = args[i + 1];

				if (key.equals("--input_file")) {
					inputFile = value;
					flag=true;
					continue;
				}
				if (!key.equals("--output_file")) {
					
					break;
				}
				outputFile = value;
				flag1=true;

			}
			if(flag==false||flag1==false) throw new RuntimeException();
		} catch (RuntimeException e) {
			System.out.println((new StringBuilder("Usage: ")).append(
					" [options] <word frequency>...").toString());
			System.out.println("\t--output_file:\tOutput file name"+outputFile);
			System.out.println("\t--input_file:\tInput file name"+inputFile);

			System.exit(1);
		}

		KeyWordExtraction kwe=new KeyWordExtraction();
		
		try {
			ByteArrayInputStream bais=new ByteArrayInputStream(kwe.readHdfsFile(inputFile));
			BufferedReader br=new BufferedReader(new InputStreamReader(bais));
			String line=null;
			Pattern expression=Pattern.compile("[a-zA-Z]+");
			
			while((line=br.readLine())!=null){
				line.toLowerCase();
				//System.out.println(line);
				Matcher m=expression.matcher(line);
				while(m.find()){
					String word=m.group();
					kwe.insert(word);
				}
			}
			br.close();
			
			StringBuilder sb=new StringBuilder();
			//BufferedWriter bw=new BufferedWriter(new FileWriter(outputFile));
			List<Map.Entry<String, Integer>> list=kwe.sort();
			Iterator<Map.Entry<String, Integer>> it=list.iterator();
			int count=0;
			while(it.hasNext()&&count<kwe.rectifyDiffWordsNum()){
				count++;
				Map.Entry<String, Integer> o=it.next();
				sb.append((String)o.getKey()+":"+o.getValue());
				sb.append("\n");
				//bw.write((String)o.getKey()+":"+o.getValue());
				//bw.newLine();
			}
			String str=sb.toString();
			kwe.createNewHDFSFile(outputFile, str);
			//bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
	}

}
