package videira.ifc.edu.br.georent.enums;

/**
 * Created by iuryk on 01/12/2016.
 */

public enum ActionEnum {
    ACTION_INDEX(0),
    ACTION_STORE(1),
    ACTION_UPDATE(2),
    ACTION_DESTROY(3),
    ACTION_LOGIN(4);

    public int actionValue;

    ActionEnum(int actionValue){
        this.actionValue = actionValue;
    }
}
