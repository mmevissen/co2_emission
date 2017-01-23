package model.agents;

import java.util.HashMap;
import java.util.Map;

class Fuel {
    // lookup
    // velocity/liters
    static Map<Integer, Double> consumption = new HashMap<Integer, Double>() {{
        put(0, 0.5);
        put(1, 1.);
        put(2, 2.);
        put(3, 3.);
        put(4, 4.);
        put(5, 5.);
    }};

    static Map<FuelType, Double> consumptionFactorToGasoline = new HashMap<FuelType, Double>() {{
        put(FuelType.Gasoline, 1.0);
        put(FuelType.Diesel, 0.85);
        put(FuelType.LPG, 1.15);
        put(FuelType.CNG, 0.7);
    }};

    static Map<FuelType, Double> co2PerL = new HashMap<FuelType, Double>() {{
        put(FuelType.Gasoline, 2320.);
        put(FuelType.Diesel, 2650.);
        put(FuelType.LPG, 1630.);
        put(FuelType.CNG, 1790.);
    }};


}


