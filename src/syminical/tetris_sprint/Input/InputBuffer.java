package syminical.tetris_sprint;

public class InputBuffer {
    private IBNode Head, Last;
    private int size = 0;
    
    public void add(InputActionType __) {
        if (size == 0)  Head(new IBNode(__));
        else Last(new IBNode(__));
    }
    
    public InputActionType next() {
        if (size == 0) return null;
        
        InputActionType __ = Head.Data();
        
        Head = Head.Next();
        --size;
        if (size == 0) Last = Head;
        
        return __;
    }
    
    public void clear() { Head = null; Last = Head; size = 0; }
    private void Head(IBNode __) { Head = __; Last = Head; size = 1; }
    private void Last(IBNode __) { Last = Last.Next(__); ++size; }
    public int size() { return size; }
    
    //-----Helper Classes-----
    private class IBNode {
        private IBNode Next;
        private InputActionType Data;
        
        public IBNode(InputActionType IAT) { Data = IAT; }
        public IBNode Next(IBNode __) { Next = __; return Next; }
        public IBNode Next() { return Next; }
        public InputActionType Data() { return Data; }
    }
}