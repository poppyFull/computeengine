import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ComputeEngine {
    public Map<String, Map<String, String>> kMap = new HashMap<String, Map<String, String>>();
    public Map<String, Set<String>> preMap = new HashMap<String, Set<String>>();

    public String addOpt(String filePath) {
        List<String> eList = new ArrayList<String>();
        String eId = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line = "";
            while ((line=br.readLine())!=null) {
                String[] s = line.split(",");
                eList.add(s[2]);
                Map<String, String> kv = new HashMap<String, String>();
                kv.put(s[1], s[3]);
                if (kMap.containsKey(s[0])==false) {
                    kMap.put(s[0], kv);
                }
            }
            br.close();
        } catch (Exception e) {
            return null;
        }
        Set<String> preSet = new HashSet<String>();
        for (String e: eList) {
            if (e.length()==0)
                continue;
            String curId = e.split(" ")[1];
            String preId = e.split(" ")[0];
            if (preMap.containsKey(curId)) {
                preMap.get(curId).add(preId);
            }
            else {
                Set<String> tmp = new HashSet<String>();
                tmp.add(preId);
                preMap.put(curId, tmp);
            }
            preSet.add(preId);
        }
        for (String id: kMap.keySet()) {
            if (preSet.contains(id)==false)
                eId = id;
        }
        return eId;
    }

    public Map<String, Port> process(String id){
        Map<String, Port> curOut = new HashMap<String, Port>();
        if (!preMap.containsKey(id)) {
            Map<String, String> param = new HashMap<String, String>();
            String inVal = kMap.get(id).get("t1").split("=")[1];
            param.put("param1", inVal);
            curOut = new InOperator().compute(null, param);
        }
        else {
            Map<String, Port> curIn = new HashMap<String, Port>();
            Map<String, Port> preOut;
            // get curIn = preOut
            int i = 1;
            for (String preId: preMap.get(id)) {
                preOut = process(preId);
                curIn.put("input"+i, preOut.get("out1"));
                i++;
            }
            String curType = kMap.get(id).keySet().toString().replace("[", "").replace("]", "").trim();
            if (curType.equals("t2")) {
                curOut = new OutOperator().compute(curIn, null);
            }
            else if (curType.equals("t3")) {
                curOut = new SumOperator().compute(curIn, null);
            }
            else if (curType.equals("t4")) {
                curOut = new MulOperator().compute(curIn, null);
            }
        }
        return curOut;
    }



    public static void main(String[] args) {
        String path = "/Users/fu/Documents/code/data/process1.csv";
        ComputeEngine eg = new ComputeEngine();
        String endNode = eg.addOpt(path);
        eg.process(endNode);
    }
}
