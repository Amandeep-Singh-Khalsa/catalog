import java.io.File;
import java.math.BigInteger;
import java.util.*;
import org.json.JSONObject; // Use org.json

public class poly {
    static class Root {
        int x;
        BigInteger y;
        Root(int x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("input2.json"));
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }

            JSONObject json = new JSONObject(sb.toString());

            JSONObject keyObj = json.getJSONObject("keys");
            int n = keyObj.getInt("n");
            int k = keyObj.getInt("k");

            List<Root> roots = new ArrayList<>();
            for (String key : json.keySet()) {
                if ("keys".equals(key)) continue;
                JSONObject child = json.getJSONObject(key);
                int x = Integer.parseInt(key);
                int base = child.getInt("base");
                String val = child.getString("value");
                BigInteger y = new BigInteger(val, base);
                roots.add(new Root(x, y));
            }


            List<Root> use = roots.subList(0, k);
            BigInteger c = lagrangeAtZero(use);
            System.out.println("constant: " + c.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static BigInteger lagrangeAtZero(List<Root> points) {
    BigInteger result = BigInteger.ZERO;
    int n = points.size();
    for (int i = 0; i < n; i++) {
        BigInteger num = BigInteger.ONE;
        BigInteger deno = BigInteger.ONE;
        int xi = points.get(i).x;
        for (int j = 0; j < n; j++) {
            if (i == j) continue;
            int xj = points.get(j).x;
            num = num.multiply(BigInteger.valueOf(-xj));
            deno = deno.multiply(BigInteger.valueOf(xi - xj));
        }
        result = result.add(points.get(i).y.multiply(num).divide(deno));
    }
    return result;
}
}