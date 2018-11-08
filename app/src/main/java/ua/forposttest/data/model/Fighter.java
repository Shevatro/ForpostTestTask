package ua.forposttest.data.model;

import java.io.Serializable;

public class Fighter implements Serializable {
    private int id;
    private double position_lat;
    private double position_lon;
    private int health;
    private int type;
    private String team;
    private int clips;
    private int ammo;

    public void setId(int id) {
        this.id = id;
    }

    public void setPosition_lat(double position_lat) {
        this.position_lat = position_lat;
    }

    public void setPosition_lon(double position_lon) {
        this.position_lon = position_lon;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setClips(int clips) {
        this.clips = clips;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getId() {
        return id;
    }

    public double getPosition_lon() {
        return position_lon;
    }

    public double getPosition_lat() {
        return position_lat;
    }

    public int getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }

    public String getTeam() {
        return team;
    }

    public int getClips() {
        return clips;
    }

    public int getAmmo() {
        return ammo;
    }

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
