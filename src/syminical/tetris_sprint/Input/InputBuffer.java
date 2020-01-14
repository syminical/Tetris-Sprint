package syminical.tetris_sprint;

public class InputBuffer {
    private IBNode Head, Last;
    private int size = 0;
    
    public void add(InputActionType __) {
        if (size == 0)
            Head(new IBNode(null, new InputAction(__)));
        else
            if (canMerge(__))
                if (Last.Data().merge(__) == 0)
                    if (size != 1)
                        Last(Last.Prev());
                    else 
                        clear();
            else {
                Last = Last.Next(new IBNode(Last, new InputAction(__)));
                ++size;
            }
    }
    
    public InputActionType next() {
        if (size == 0) return null;
        InputActionType __ = Head.Data().Type();
        if (Head.Data().update(-1 * rawVal(Head.Data().Type())) == 0) {
            Head = Head.Next();
            if (Head != null)
                Head.Prev(null);
            --size;
        }
        return __;
    }
    
    private boolean canMerge(InputActionType __) {
        switch (Last.Data().Type()) {
            case LEFT:
            case RIGHT:
                if (__ == InputActionType.LEFT || __ == InputActionType.RIGHT) return true;
                break;
            case TURN_LEFT:
            case TURN_RIGHT:
                if (__ == InputActionType.TURN_LEFT || __ == InputActionType.TURN_RIGHT) return true;
                break;
            case FAST_DROP:
                if (__ == InputActionType.FAST_DROP) return true;
            default:
        }
        return false;
    }
    
    public void clear() { Head = null; Last = Head; size = 0; }
    private void Head(IBNode __) { Head = __; Last = Head; size = 1; }
    private void Last(IBNode __) { Last = __; Last.Next(null); --size; }
    public static int rawVal(InputActionType __) { return ((__ == InputActionType.LEFT || __ == InputActionType.TURN_LEFT)? -1 : 1); }
    public int size() { return size; }
    
    //-----Helper Classes-----
    private class IBNode {
        private IBNode Next, Prev;
        private InputAction Data;
        
        public IBNode(IBNode IBN, InputAction IA) { Prev = IBN; Data = IA; }
        public IBNode Next(IBNode __) { Next = __; return Next; }
        public void Prev(IBNode __) { Prev = __; }
        public IBNode Next() { return Next; }
        public IBNode Prev() { return Prev; }
        public InputAction Data() { return Data; }
    }
    
    private class InputAction {
        private InputActionType Type;
        private int magnitude;
        
        public InputAction(InputActionType __) {
            Type = __;
            magnitude = InputBuffer.rawVal(__);
        }
        public int merge(InputActionType __) {
            magnitude += InputBuffer.rawVal(__);
            return magnitude;
        }
        public InputActionType Type() { return Type; }
        public int magnitude() { return magnitude; }
        public int update(int __) { magnitude += __; return magnitude; }
    }
}