package JsonUtils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JsonUnitls {

	public void ListToJsonListReapet() {
		List pickDetailList = new ArrayList();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("") || name.equals("")) {
					return true;
				} else {
					return false;
				}
			}
		});
		String jsonStr = JSONArray.fromObject(pickDetailList, jsonConfig)
				.toString();
	}

}
