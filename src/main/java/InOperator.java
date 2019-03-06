import java.util.HashMap;
import java.util.Map;

public class InOperator implements Operator{

    public Map<String, Port> compute(Map<String, Port> input, Map<String, String> param) {
        String inValue = param.get("param1");
        Port outPort = new Port("", inValue);
        Map<String, Port> outMap = new HashMap<String, Port>();
        outMap.put("out1", outPort);
        return outMap;
    }
}
