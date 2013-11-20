package haflow.module.CloudTag;


import haflow.component.CloudTag;
import haflow.module.AbstractJavaModule;
import haflow.module.DataType;
import haflow.module.Module;
import haflow.module.ModuleEndpoint;
import haflow.module.ModuleType;

import java.util.Map;

@Module(id = "add600a8-ab63-8901-ca46-bbffa0d0cd11", name = "CloudTag2", category = "TextMining", type = ModuleType.JAVA, 
	configurations = {}, 
	inputs = { @ModuleEndpoint(name = "input_file", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) }, 
	outputs = { @ModuleEndpoint(name = "output_file", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) })
public class CloudTag2Module extends AbstractJavaModule {

	@Override
	public boolean validate(Map<String, String> configurations,
			Map<String, String> inputs, Map<String, String> outputs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMainClass() {
//		return  CloudTag.class.getName();
		return CloudTag.class.getName();
	}

}
