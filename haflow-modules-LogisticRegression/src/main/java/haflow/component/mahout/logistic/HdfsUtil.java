package haflow.component.mahout.logistic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtil {
	public static InputStream open(String inputFile) throws Exception{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(inputFile);
		if (fs.exists(path)) {
			FSDataInputStream is = fs.open(path);
			return is;
		}else{
			throw new Exception("the file is not found:" + inputFile);
		}
	}
	
	  public static OutputStream writeHdfs(String outputFile) throws IOException{
		  Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			Path path = new Path(outputFile);
			System.out.print(path);
			
			FSDataOutputStream os=fs.create(path);
			return os;
	  }
	

}
