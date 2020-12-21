public enum MapDirection {
    NORTH, NORTHEAST, EAST,SOUTHEAST, SOUTH, SOUTHWEST, WEST,NORTHWEST;  // in that order

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "↑";
            case SOUTH -> "↓";
            case WEST -> "←";
            case EAST -> "→";
            case NORTHEAST -> "↗";
            case NORTHWEST -> "↖";
            case SOUTHEAST -> "↘";
            case SOUTHWEST ->  "↙";
            default -> "Wrong direction";
        };
    }
    public static MapDirection orientation(int i){
        MapDirection[] values = MapDirection.values();
        return values[i];

    }

    public MapDirection next(){
        MapDirection[] values = MapDirection.values();
        return values[(this.ordinal()+1)%values.length];

    }
    public MapDirection previous(){
        MapDirection[] values = MapDirection.values();
        if (this.ordinal() >0) return values[(this.ordinal()-1)%values.length];
        else return values[(this.ordinal()-1)%values.length +values.length];

    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1,1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1,-1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1,1);
            default -> new Vector2d(0, 0);
        };
    }


}