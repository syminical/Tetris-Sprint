package syminical.tetris_sprint;

public class InputBuffer {
    private InputAction Head, Last;
    private int size = 0;
    public enum InputActionType { LEFT, RIGHT, TURN_LEFT, TURN_RIGHT, FAST_DROP }
    
    public void add(InputActionType _) {
        if (size == 0)
            Head(new IBNode(null, new InputAction(_)));
        else
            if (Last.Data().Type() == _)
                if (Last.Data().merge(_) == 0)
                    if (size != 1)
                        Last(Last.Prev());
                    else {
                        Head(null);
                        size = -1;
                    }   
            else {
                Last = Last.Next(new IBNode(Last, new InputAction(_)));
            }
        ++size;
    }
    
    public void empty(long deadLine) {
        while (size > 0 && System.currentTimeMillis() < deadLine) {
            switch (Head.Data().Type()) {
                case LEFT:
                    
                case 
            }
        }
    }
    
    private void Head(IBNode _) { Head = _; Last = Head; }
    private void Last(IBNode _) { Last = _; Last.Next(null); --size; }
    public int rawVal(InputActionType _) { return ((_ == LEFT || _ == TURN_LEFT)? -1 : 1); }
    public int size() { return size; }
    
    //-----Helper Classes-----
    private class IBNode {
        private IBNode Next, Prev;
        private InputAction Data;
        
        public IBNode(IBNode IBN, InputAction IA) { Prev = IBN; Data = IA; }
        public IBNode Next(IBNode _) { Next = _; return Next; }
        public IBNode Next() { return Next; }
        public IBNode Prev() { return Prev; }
        public InputAction Data() { return Data; }
    }
    
    private class InputAction {
        private InputActionType Type;
        private int magnitude;
        
        public InputAction(InputActionType _) {
            Type = _;
            magnitude = InputBuffer.rawVal(_);
        }
        public int merge(InputActionType _) {
            magnitude += InputBuffer.rawVal(_);
            return magnitude;
        }
        public InputActionType Type() { return Type; }
        public int magnitude() { return magnitude; }
    }
}