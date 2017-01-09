package agents;

import java.util.HashMap;
import java.util.Map;

class Fuel {

	// lookup
	static Map<Integer, Double> consumption = new HashMap<Integer, Double>(){{
		put(0,0.5);
		put(1,1.);
		put(2,2.);
		put(3,3.);
		put(4,4.);
		put(5,5.);
	}};

	static Map<FuelType, Double> co2PerL = new HashMap<FuelType, Double>(){{
        put(FuelType.Gasoline,2320.);
        put(FuelType.Diesel,2650.);
        put(FuelType.LPG,1630.);
        put(FuelType.CNG,1790.);
    }};
    

}


