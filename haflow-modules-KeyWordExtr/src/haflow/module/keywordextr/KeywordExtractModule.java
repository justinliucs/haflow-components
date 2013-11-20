package haflow.module.keywordextr;


import haflow.component.KeyWordExtraction;
import haflow.module.AbstractJavaModule;
import haflow.module.DataType;
import haflow.module.Module;
import haflow.module.ModuleEndpoint;
import haflow.module.ModuleType;

import java.util.Map;

@Module(id = "add600a8-ab63-8901-ca46-aaffa0e0bdcf", name = "KeyWordExtraction", category = "TextMining", type = ModuleType.JAVA, 
	configurations = {}, 
	inputs = { @ModuleEndpoint(name = "input_file", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) }, 
	outputs = { @ModuleEndpoint(name = "output_file", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) })
public class KeywordExtractModule extends AbstractJavaModule {

	@Override
	public boolean validate(Map<String, String> configurations,
			Map<String, String> inputs, Map<String, String> outputs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMainClass() {
//		Dictionary.getInstance();
		return  KeyWordExtraction.class.getName();
	}

}
