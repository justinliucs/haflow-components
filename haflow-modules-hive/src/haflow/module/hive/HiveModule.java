package haflow.module.hive;

import haflow.module.AbstractHiveModule;
import haflow.module.DataType;
import haflow.module.Module;
import haflow.module.ModuleConfiguration;
import haflow.module.ModuleConfigurationType;
import haflow.module.ModuleEndpoint;
import haflow.module.ModuleType;

import java.util.Map;

@Module(id = "add600a8-aa63-8901-ca46-aaffa0e0bd2f", name = "Hive", category = "DataSource", type = ModuleType.HIVE, 
	configurations = {
		@ModuleConfiguration(key = "sql", displayName = "Sql Command", pattern = "^(.*)$", type = ModuleConfigurationType.PLAIN_TEXT),}, 
	inputs = { }, 
	outputs = {
		@ModuleEndpoint(name = "ok", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText),
		@ModuleEndpoint(name = "error", minNumber = 1, maxNumber = 1, dataType = DataType.PlainText) })
public class HiveModule extends AbstractHiveModule {
	@Override
	public boolean validate(Map<String, String> configurations,
			Map<String, String> inputs, Map<String, String> outputs) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		System.out.println("Demo Java Main");

		System.out.println("# Arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println("Argument[" + i + "]: " + args[i]);
		}
	}

	@Override
	public String getSQL(Map<String, String> configurations) {
		return configurations.get("sql");
	}

	@Override
	public String getMainClass() {
		return HiveJdbcClient.class.getName();
	}

	@Override
	public String getOutPath(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return "";
	}

}
