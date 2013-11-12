package haflow.component;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.ctrip.*;





public class CloudTag {
	private Map<String,Integer> words;
	public WordsThumb wt;
	
	
	public CloudTag(){
		this.wt=new WordsThumb();
		this.words=new HashMap<String,Integer>();
		
	}
	public void putWord(String word,Integer weight){
		this.words.put(word, weight);
	}
	public Map<String,Integer> getWords(){
		return this.words;
	}

	public byte[] readHdfsFile(String input) throws Exception {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);

		// check if the file exists
		Path path = new Path(input);
		if (fs.exists(path)) {
			FSDataInputStream is = fs.open(path);
			// get the file info to create the buffer
			FileStatus stat = fs.getFileStatus(path);

			// create the buffer
			byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat
					.getLen()))];
			is.readFully(0, buffer);
			
			is.close();
			//fs.close();

			return buffer;
		} else {
			throw new Exception("the file is not found:" + input);
		}
	}

	public void uploadLocalFile2HDFS(String s, String d)
			throws IOException {
		Configuration config = new Configuration();
		FileSystem hdfs = FileSystem.get(config);

		Path src = new Path(s);
		Path dst = new Path(d);

		hdfs.copyFromLocalFile(src, dst);

		//hdfs.close();
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
					flag = true;
					inputFile = value;
					continue;
				}
				if (!key.equals("--output_file"))
					break;
				outputFile = value;
				flag1=true;

			}
			if (flag == false||flag1==false)
				throw new Exception();
		} catch (Exception e) {
			System.out.println((new StringBuilder("Usage: ")).append(
					" [options] <word frequency>...").toString());
			System.out.println("\t--output_file:\tOutput file name");
			System.out.println("\t--input_file:\tInput file name");

			System.exit(1);
		}
		CloudTag ct=new CloudTag();
		try {
			ByteArrayInputStream bais=new ByteArrayInputStream(ct.readHdfsFile(inputFile));
			BufferedReader br=new BufferedReader(new InputStreamReader(bais));
			
			String line=null;
			while((line=br.readLine())!=null){
				String[] strs=line.split(":");
				ct.putWord(strs[0],Integer.valueOf(Integer.parseInt(strs[1])));
				
			}
			File fileDis=new File("./tmpdawncx");
			String fileName="./tmpdawncx/tmpdawncx.jpg";
			//String fileName = outputFile;
	        /*String bgPic = null;
	        String waterMarkPic = null;*/
	        int width = 640;
	        int height = 480;
	        String fontName = "\u5FAE\u8F6F\u96C5\u9ED1";
			WordsThumb wt=ct.wt;
			
			java.awt.image.BufferedImage bi = wt.createWordsThumb(ct.getWords(), width, height, fontName, null, null);
	        java.awt.image.BufferedImage bg = null;
	        java.awt.image.BufferedImage wm = null;
	        Float fgAlpha = Float.valueOf(0.8F);
	        Float wmAlpha = Float.valueOf(1.0F);
	       /* if(bgPic != null)
	            bg = ImageIO.read(new FileInputStream(bgPic));
	        if(waterMarkPic != null)
	            wm = ImageIO.read(new FileInputStream(waterMarkPic));*/
	        bi = wt.mixImages(new java.awt.image.BufferedImage[] {
	            bg, bi, wm
	        }, new Float[] {
	            Float.valueOf(1.0F), fgAlpha, wmAlpha
	        }, bi.getWidth(), bi.getHeight());
	        if(!fileDis.exists()){
	        	fileDis.mkdir();
	        }
	        File file = new File(fileName);
	        ImageIO.write(bi, "png", file);
			ct.uploadLocalFile2HDFS(fileName,outputFile);
			file.delete();
			fileDis.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
