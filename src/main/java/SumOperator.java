import java.util.HashMap;
import java.util.Map;

public class SumOperator implements Operator {

    public Map<String, Port> compute(Map<String, Port> input, Map<String, String> param) {
        double a = Double.valueOf(input.get("input1").value);
        double b = Double.valueOf(input.get("input2").value);
        Port outPort = new Port("", String.valueOf(a+b));
        Map<String, Port> outMap = new HashMap<String, Port>();
        outMap.put("out1", outPort);
        return outMap;
    }
}
