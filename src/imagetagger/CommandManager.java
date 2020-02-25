/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetagger;

import java.util.LinkedList;

/**
 * Class for holding performed operations to allow undo and redo commands
 * @author fruktus
 */
public class CommandManager {
    private final LinkedList<Object> undo;
    private final LinkedList<Object> redo;
    private final int maxSize;
    
    public CommandManager(int maxSize){
        //use linked lists for storage
        this.undo = new LinkedList();
        this.redo = new LinkedList();
        this.maxSize = maxSize;
    }
    
    public void addCommand(Object command){ //the commandManager does not need to know what exactly is stored
        if(this.undo.size() > this.maxSize){
            this.undo.removeFirst();
        }
        this.undo.addLast(command);
        //new action was taken so now all redo actions are outdated and need to be removed
        this.redo.clear();
    }
    
    public Object undo(){
        if(this.redo.isEmpty()){
            return null;
        }
        // move last action to redo, if redo is full, delete least recent one
        if(this.redo.size() > this.maxSize){
            this.redo.removeFirst();
        }
        Object lastAction = this.undo.removeLast();
        this.redo.addLast(lastAction);
        return lastAction;
    }
    
    public Object redo(){
        if(this.redo.isEmpty()){
            return null;
        }
        if(this.undo.size() > this.maxSize){
            this.undo.removeFirst();
        }
        Object action = this.redo.removeLast();
        this.undo.addLast(action);
        return action;
    }
}