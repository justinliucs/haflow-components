package haflow.modules.mahout.logistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import haflow.component.mahout.logistic.RunLogistic;
import haflow.module.AbstractJavaModule;
import haflow.module.DataType;
import haflow.module.Module;
import haflow.module.ModuleConfiguration;
import haflow.module.ModuleConfigurationType;
import haflow.module.ModuleEndpoint;
import haflow.module.ModuleType;

@Module(id = "aee612a8-ab34-8901-ca46-aaf120d0cd5f", name = "RunLogistic", category = "DataMining-Mahout", type = ModuleType.JAVA,
configurations = {
		@ModuleConfiguration(key = "model", displayName = "Model File: where to get training model", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key = "input", displayName = "Input File: where to get training data", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key = "output", displayName = "Output File: where to store the final results", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT), 
	
		@ModuleConfiguration(key="auc",displayName = "auc: print AUC", pattern = "^(.*)$", type = ModuleConfigurationType.BOOLEAN),
		@ModuleConfiguration(key="scores",displayName = "scores: print scores", pattern = "^(.*)$", type = ModuleConfigurationType.BOOLEAN),
		@ModuleConfiguration(key="confusion",displayName = "confusion: print confusion matrix", pattern = "^(.*)$", type = ModuleConfigurationType.BOOLEAN),
},
		inputs = { @ModuleEndpoint(name = "from", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) }, 
		outputs = {
		@ModuleEndpoint(name = "ok", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText),
		@ModuleEndpoint(name = "error", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) 
		})
public class RunLogisticModule extends AbstractJavaModule {
	@Override
	public boolean validate(Map<String, String> configurations,
			Map<String, String> inputs, Map<String, String> outputs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMainClass() {
		return  RunLogistic.class.getName();
	}
	
	@Override
	public List<String> getArguments(Map<String, String> configurations) {
		Module module= this.getClass().getAnnotation(Module.class);
		ModuleConfiguration[] confs = module.configurations();
		
		List<String> result = new ArrayList<String>();
		for (String key : configurations.keySet()) {
			ModuleConfigurationType confType = getConfigurationType(key, confs);
			switch(confType){
			case BOOLEAN:
				String boolValue = configurations.get(key);
				System.out.println(boolValue);
				if( boolValue.equals("on")){
					result.add("--" + key );
				}
				break;
			case PLAIN_TEXT:
				String textValue = configurations.get(key).trim();
				if( textValue.length() > 0){
					result.add("--" + key);
					result.add(configurations.get(key));
				}
				break;
			case OTHER:	
				
			default:
				System.out.println("Invalid Parameters!");
				break;
			}
		}
		return result;
	}

	private ModuleConfigurationType getConfigurationType(String key, ModuleConfiguration[] confs){
		for( ModuleConfiguration conf : confs){
			if( key.equals(conf.key()))
				return conf.type();
		}
		return ModuleConfigurationType.OTHER;
	}
	
	public static void main(String[] args) {
		System.out.println("Demo Java Main");

		System.out.println("# Arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println("Argument[" + i + "]: " + args[i]);
		}
	}

}

