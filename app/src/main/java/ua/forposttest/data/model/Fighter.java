package ua.forposttest.data.model;

import java.io.Serializable;

public class Fighter implements Serializable {
    public int id;
    public float position_lon;
    public float position_lat;
    public int health;
    public int type;
    public String team;
    public int clips;
    public int ammo;

    @Override
    public String toString() {
        return "Fighter{" +
                "id=" + id +
                ", position_lon=" + position_lon +
                ", position_lat=" + position_lat +
                ", health=" + health +
                ", type=" + type +
                ", team='" + team + '\'' +
                ", clips=" + clips +
                ", ammo=" + ammo +
                '}';
    }
}
