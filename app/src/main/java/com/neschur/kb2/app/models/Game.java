package com.neschur.kb2.app.models;

import com.neschur.kb2.app.R;
import com.neschur.kb2.app.controllers.ActivateCallback;
import com.neschur.kb2.app.countries.World;
import com.neschur.kb2.app.entities.ArmyShop;
import com.neschur.kb2.app.entities.Fighting;
import com.neschur.kb2.app.entities.Nave;

import java.io.Serializable;

public class Game implements Serializable {
    public static final int MODE_TRAINING = Player.MODE_TRAINING;
    public static final int MODE_GAME = Player.MODE_GAME;

    final transient private ActivateCallback mainController;

    private final World world;
    private final Player player;
    private Nave nave;
    private int weeks;
    private int days = 0;
    private int currentWorker = -1;

    public Game(ActivateCallback mainController, int mode) {
        this.mainController = mainController;
        world = new World(mode);
        player = new Player(world.getCountry(0), mode);
        if (mode == MODE_GAME) {
            weeks = 200;
        } else if (mode == MODE_TRAINING) {
            weeks = 600;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void moveEntities() {
//        Sorcerer sorcerer = player.getCountry().getSorcerer();
//        sorcerer.moveTo(player.getMapPoint());
    }

    public void weekUpdate() {
        if (days > 0) {
            days--;
        } else {
            days = 200;
            weeks--;
        }
    }

    public void move(int dx, int dy) {
        int x = player.getX();
        int y = player.getY();
        if (x + dx < 2 || x + dx > 62 || y + dy < 2 || y + dy > 62) {
            return;
        }

        moveEntities();
        weekUpdate();

        MapPoint mp = player.getCountry().getMapPoint(x + dx, y + dy);

        if (mp.getEntity() == null) {
            if (currentWorker > -1) {
                if (currentWorker == 0 && mp.getLand() == R.drawable.water) {
                    mp.setLand(R.drawable.plot);
                }
                if (currentWorker == 1 && mp.getLand() == R.drawable.forest) {
                    mp.setLand(R.drawable.land);
                }
                if (currentWorker == 2 && mp.getLand() == R.drawable.land) {
                    mp.setLand(R.drawable.water);
                }
                if (currentWorker == 3 && mp.getLand() == R.drawable.stone) {
                    mp.setLand(R.drawable.land);
                }
                currentWorker = -1;
            } else if (player.inNave()) {
                if (mp.getLand() == R.drawable.land || mp.getLand() == R.drawable.sand) {
                    player.setNave(null);
                    player.move(x + dx, y + dy);
                }
                if (mp.getLand() == R.drawable.water) {
                    player.move(x + dx, y + dy);
                }
            } else {
                if (mp.getLand() == R.drawable.land || mp.getLand() == R.drawable.plot
                        || mp.getLand() == R.drawable.sand) {
                    player.move(x + dx, y + dy);
                }
            }
        } else {
            actionWithObject(player, mp);
        }
    }

    private void actionWithObject(Player player, MapPoint mp) {
        if (mp.getEntity() instanceof Nave) {
            player.setNave((Nave) mp.getEntity());
            player.move(mp.getX(), mp.getY());
        } else {
            mainController.activateEntity(mp.getEntity());
        }
    }

    public boolean getNave() {
        return nave != null;
    }

    public void createNave(int x, int y) {
        nave = new Nave(world.getCountry(0).getMapPoint(x, y));
    }

    public void destroyNave() {
        nave.destroy();
        nave = null;
    }

    public void buyArmy(ArmyShop armyShop, int count) {
        if (armyShop.getCount() >= count &&
                player.armyAfford(armyShop.getWarrior()) >= count &&
                player.getMoney() >= armyShop.getWarrior().getPriceInShop() * count &&
                player.getWarriorSquadsCount() < Player.MAX_ARMY) {
            player.changeMoney(-armyShop.getWarrior().getPriceInShop() * count);
            armyShop.pullArmy(count);
            player.pushArmy(armyShop.getWarrior(), count);
        }
    }

    public void activateBattle(Fighting fighting) {
        mainController.activateBattle(fighting);
    }

    public void selectWorker(int n) {
        if (player.getWorker(n) > 0) {
            currentWorker = n;
            player.changeWorker(n, -1);
        }
    }
}