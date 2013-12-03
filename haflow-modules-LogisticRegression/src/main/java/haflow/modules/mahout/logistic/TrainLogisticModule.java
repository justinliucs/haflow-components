package haflow.modules.mahout.logistic;

import java.util.Map;


import haflow.component.mahout.logistic.TrainLogistic;
import haflow.module.AbstractJavaModule;
import haflow.module.DataType;
import haflow.module.Module;
import haflow.module.ModuleConfiguration;
import haflow.module.ModuleConfigurationType;
import haflow.module.ModuleEndpoint;
import haflow.module.ModuleType;

@Module(id = "aee600a8-ab63-8901-ca46-aaf120d0cd5f", name = "TrainLogistic", category = "DataMining-Mahout", type = ModuleType.JAVA,
configurations = {
		/*@ModuleConfiguration(key = "infor", displayName = "Information File: show information about training", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key = "input", displayName = "Input File: where to get training data", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key = "output", displayName = "Output File: where to store the training model", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT), */
		@ModuleConfiguration(key="passes",displayName = "passes: the number of times to pass over the input data", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key="rate",displayName = "rate: the learning rate", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key="lambda",displayName = "lambda: the amount of coefficient decay to use", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key="features",displayName = "features: the number of internal hashed features to use", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key="target",displayName = "target: the name of the target variable", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key="categories",displayName = "categories: the number of target categories to be considered", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),
		@ModuleConfiguration(key="predictors",displayName="predictors: a list of predictor variables",pattern = "^(.*)$", type = ModuleConfigurationType.TEXT_ARRAY),
		@ModuleConfiguration(key="types",displayName = "types: a list of predictor variable types (numeric, word, or text)", pattern = "^(.*)$", type = ModuleConfigurationType.TEXT_ARRAY),
		@ModuleConfiguration(key="noBias",displayName = "noBias: don't include a bias term", pattern = "^(.*)$", type = ModuleConfigurationType.BOOLEAN),
},
		inputs = { @ModuleEndpoint(name = "input", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText),
		}, 
		outputs = {
		@ModuleEndpoint(name = "output", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText),
		@ModuleEndpoint(name = "infor", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) 
		})
public class TrainLogisticModule extends AbstractJavaModule {
	@Override
	public boolean validate(Map<String, String> configurations,
			Map<String, String> inputs, Map<String, String> outputs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMainClass() {
		return  TrainLogistic.class.getName();
	}
	
//	@Override
//	public List<String> getArguments(Map<String, String> configurations) {
//		Module module= this.getClass().getAnnotation(Module.class);
//		ModuleConfiguration[] confs = module.configurations();
//		
//		List<String> result = new ArrayList<String>();
//		for (String key : configurations.keySet()) {
//			ModuleConfigurationType confType = getConfigurationType(key, confs);
//			switch(confType){
//			case BOOLEAN:
//				String boolValue = configurations.get(key);
//				if( boolValue.equals("on")){
//					result.add("--" + key );
//				}
//				break;
//			case PLAIN_TEXT:
//				String textValue = configurations.get(key).trim();
//				if( textValue.length() > 0){
//					if(key.equals("predictors")||key.equals("types")){
//						result.add("--" + key);
//						String[] values = textValue.split(" ");
//						for(String value : values){
//							result.add(value);
//						}
//					}else{
//						result.add("--" + key);
//						result.add(configurations.get(key));
//					}
//				}
//				break;
//
//			case OTHER:	
//				
//			default:
//				System.out.println("Invalid Parameters!");
//				break;
//			}
//		}
//		return result;
//	}

	/*private ModuleConfigurationType getConfigurationType(String key, ModuleConfiguration[] confs){
		for( ModuleConfiguration conf : confs){
			if( key.equals(conf.key()))
				return conf.type();
		}
		return ModuleConfigurationType.OTHER;
	}*/
	
	public static void main(String[] args) {
		System.out.println("Demo Java Main");

		System.out.println("# Arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println("Argument[" + i + "]: " + args[i]);
		}
	}

}
