package imagetagger;

public class Command<V, T> {
    // wil be used for storing the actions taken
    public final V modifiedObject;  //reference, used for determining how to restore state
    public final T oldValue;
    public final T newValue;

    public Command(V modifiedObject, T oldValue, T newValue){
        this.modifiedObject = modifiedObject;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
