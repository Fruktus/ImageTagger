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
public class Command {
    private final LinkedList<Object[]> undo;
    private final LinkedList<Object[]> redo;
    private final int maxSize;
    
    public Command(int maxSize){
        //use linked lists for storage
        this.undo = new LinkedList();
        this.redo = new LinkedList();
        this.maxSize = maxSize;
    }
    
    public void addCommand(Object[] params){ //should take enum corresponding to action and its parameters
        if(this.undo.size() > this.maxSize){
            this.undo.removeFirst();
        }
        this.undo.addLast(params);
        //clear redo
        this.redo.clear();
    }
    
    public Object[] undo(){ //wont be void, needs to return previous set of parameters
        if(this.undo.isEmpty()){
            return null;
        }
        if(this.redo.size() > this.maxSize){
            this.redo.removeFirst();
        }
        this.redo.addLast((Object[]) this.undo.getLast());
        return this.undo.removeLast();
    }
    
    public Object[] redo(){ //needs to return new set of parameters
        if(this.redo.isEmpty()){
            return null;
        }
        if(this.undo.size() > this.maxSize){
            this.undo.removeFirst();
        }
        this.undo.addLast((Object[]) this.redo.getLast());
        return this.redo.removeLast();
    }
}