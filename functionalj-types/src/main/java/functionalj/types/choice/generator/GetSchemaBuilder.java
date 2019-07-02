package functionalj.types.choice.generator;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import functionalj.types.choice.generator.model.CaseParam;
import lombok.val;

public class GetSchemaBuilder implements Lines {

	@Override
	public List<String> lines() {
		val map = Map.class.getCanonicalName();
		val str = String.class.getCanonicalName();
		val prm = CaseParam.class.getCanonicalName();
		return asList(
				"public " + map + "<" + str + ", " + map + "<" + str + ", " + prm + ">> __getSchema() {",
				"	return getChoiceSchema();",
				"}");
	}
	

}
