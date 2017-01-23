package model.agents;

public interface Agent {

    boolean update();

    boolean calculate(long currentTime);
}
