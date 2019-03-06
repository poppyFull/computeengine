import java.util.HashMap;
import java.util.Map;

public class OutOperator implements Operator{

    public Map<String, Port> compute(Map<String, Port> input, Map<String, String> param) {
        String inVal = input.get("input1").value;
        System.out.println(inVal);
        Map<String, Port> outMap = new HashMap<String, Port>();
        outMap.put("out1", input.get("input1"));
        return outMap;
    }
}
