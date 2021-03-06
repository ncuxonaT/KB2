package by.siarhei.kb2.app.models.battle;

import by.siarhei.kb2.app.entities.Entity;
import by.siarhei.kb2.app.models.Glade;
import by.siarhei.kb2.app.models.MapPoint;

public class MapPointBattle extends MapPoint {
    private boolean move = false;
    private WarriorEntity entity;

    public MapPointBattle(Glade glade, int x, int y) {
        super(glade, x, y);
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    @Override
    public WarriorEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = (WarriorEntity) entity;
    }
}
