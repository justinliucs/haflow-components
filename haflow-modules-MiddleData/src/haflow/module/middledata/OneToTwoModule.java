package haflow.module.middledata;

import haflow.module.AbstractJavaModule;
import haflow.module.DataType;
import haflow.module.Module;
import haflow.module.ModuleEndpoint;
import haflow.module.ModuleType;

import java.util.Map;

@Module(id = "ada611a8-aa63-968a-ca46-4356a1e1bdab", name = "One2Two", category = "Data-Source", type = ModuleType.MIDDLE_DATA, 
	configurations = {}, 
	inputs = { @ModuleEndpoint(name = "input", minNumber = 1, maxNumber = 1, dataType = DataType.AUTO) }, 
	outputs = { @ModuleEndpoint(name = "out1", minNumber = 1, maxNumber = 1, dataType = DataType.AUTO),
		@ModuleEndpoint(name = "out2", minNumber = 1, maxNumber = 1, dataType = DataType.AUTO)})
public class OneToTwoModule extends AbstractJavaModule {

	@Override
	public boolean validate(Map<String, String> configurations,
			Map<String, String> inputs, Map<String, String> outputs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMainClass() {
		return OneToTwoModule.class.getName();
	}
	
	public static void main(String[] args) {
		System.out.println("Dummy!");
	}
}
