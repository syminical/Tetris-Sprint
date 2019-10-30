package syminical.tetris_sprint;

public class InputBuffer {
    private IBNode Head, Last;
    private int size = 0;
    public enum InputActionType { LEFT, RIGHT, TURN_LEFT, TURN_RIGHT, FAST_DROP }
    
    public void add(InputActionType _) {
        if (size == 0)
            Head(new IBNode(null, new InputAction(_)));
        else
            if (canMerge(_))
                if (Last.Data().merge(_) == 0)
                    if (size != 1)
                        Last(Last.Prev());
                    else {
                        Head(null);
                        size = 0;
                    }   
            else {
                Last = Last.Next(new IBNode(Last, new InputAction(_)));
                ++size;
            }
    }
    
    public InputActionType next() {
        if (size == 0) return null;
        InputActionType _ = Head.Data().Type();
        if (Head.Data().update(-1 * rawVal(Head.Data().Type())) == 0) {
            Head = Head.Next();
            --size;
        }
        return extract(_);
    }
    
    public void empty(long deadLine) {
        while (size > 0 && System.currentTimeMillis() < deadLine) {
            switch (Head.Data().Type()) {
                case LEFT:
                    
                case 
            }
        }
    }
    
    private InputActionType extract(IBNode _) {
           
    }
    
    private boolean canMerge(InputActionType _) {
        switch (Last.Data().Type()) {
            case LEFT:
            case RIGHT:
                if (_ == LEFT || _ == RIGHT) return true;
                break;
            case TURN_LEFT:
            case TURN_RIGHT:
                if (_ == TURN_LEFT || _ == TURN_RIGHT) return true;
                break;
            case FAST_DROP:
                if (_ == FAST_DROP) return true;
            default:
        }
        return false;
    }
    
    private void Head(IBNode _) { Head = _; Last = Head; size = 1; }
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
        public int update(int _) { magnitude += _; return magnitude; }
    }
}