import java.util.Map;

public interface Operator {
    public Map<String, Port> compute(Map<String, Port> input, Map<String, String> param);
}
