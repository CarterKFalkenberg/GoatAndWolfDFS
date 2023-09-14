public class StateHolder {

    public int goatsAtA;
    public int goatsAtB;
    public int wolvesAtA;
    public int wolvesAtB;
    public char boatLocation;

    //----------------------------------------------------------------
    public StateHolder(int gA, int gB, int wA, int wB, char loc){
        this.goatsAtA = gA;
        this.goatsAtB = gB;
        this.wolvesAtA = wA;
        this.wolvesAtB = wB;
        this.boatLocation = loc;
    }

    public StateHolder(StateHolder state){
        this.goatsAtA = state.goatsAtA;
        this.goatsAtB = state.goatsAtB;
        this.wolvesAtA = state.wolvesAtA;
        this.wolvesAtB = state.wolvesAtB;
        this.boatLocation = state.boatLocation;
    }

    //----------------------------------------------------------------

    /*
     * This returns true for valid states, false otherwise
     * Failures are NOT valid 
     */
    public boolean isValidState(){
        if (this.goatsAtA < 0 || this.goatsAtA > 3) return false;
        if (this.goatsAtB < 0 || this.goatsAtB > 3) return false;
        if (this.wolvesAtA < 0 || this.wolvesAtA > 3) return false;
        if (this.wolvesAtB < 0 || this.wolvesAtB > 3) return false;
        if (this.goatsAtA + this.goatsAtB != 3) return false;
        if (this.wolvesAtA + this.wolvesAtB != 3) return false;
        if (this.boatLocation != 'A' && this.boatLocation != 'B') return false;
        if (this.goatsAtA > 0 && this.wolvesAtA > this.goatsAtA) return false;
        if (this.goatsAtB > 0 && this.wolvesAtB > this.goatsAtB) return false;
        return true;
    }

    //----------------------------------------------------------------

    /*
     * Returns true if the state is the same
     */
    public boolean equals(StateHolder state){
        return this.goatsAtA == state.goatsAtA 
            && this.goatsAtB == state.goatsAtB
            && this.wolvesAtA == state.wolvesAtA
            && this.wolvesAtB == state.wolvesAtB;
    }

    public String toString(){
        String result = "Boat on side " + Character.toString(this.boatLocation);
        result += ". SIDE A: ";
        for(int i = 0; i < this.goatsAtA; i++){
            result += "G";
        }
        for(int i = 0; i < this.wolvesAtA; i++){
            result += "W";
        }
        result += "| SIDE B: ";
        for(int i = 0; i < this.goatsAtB; i++){
            result += "G";
        }
        for(int i = 0; i < this.wolvesAtB; i++){
            result += "W";
        }
        return result;
    }

}
