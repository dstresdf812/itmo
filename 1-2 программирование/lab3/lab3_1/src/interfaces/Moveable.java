package interfaces;

import extra.FlatRoom;
import exceptions.MovementException;

public interface Moveable {
    void moveTo(FlatRoom room) throws MovementException;
    void moveinRoom() throws MovementException;
}