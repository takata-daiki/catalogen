package com.meta4.games.letitlight;

public enum CellType {
    POWER_SOURCE {
        private final boolean[] connectedTo = {false, false, true, false};
        private final boolean[] connections = {false, false, true, false};

        @Override
        public boolean connectableFrom(int direction) {
            return direction == 2;
        }

        @Override
        public boolean[] connectedTo(int fromDirection) {
            return connectedTo;
        }

        @Override
        public boolean[] connections() {
            return connections;
        }
    },
    LAMP {
        private final boolean[] connectedTo = {false, false, false, false};
        private final boolean[] connections = {false, false, true, false};

        @Override
        public boolean connectableFrom(int direction) {
            return direction == 2;
        }

        @Override
        public boolean[] connectedTo(int fromDirection) {
            return connectedTo;
        }

        @Override
        public boolean[] connections() {
            return connections;
        }
    },
    C1 {
        private final boolean[] connections = {true, false, true, false};
        private final boolean[][] connectedTo = {
                {false, false, true, false},
                {false, false, false, false},
                {true, false, false, false},
                {false, false, false, false},
        };

        @Override
        public boolean connectableFrom(int direction) {
            return direction == 0 || direction == 2;
        }

        @Override
        public boolean[] connectedTo(int fromDirection) {
            return connectedTo[fromDirection];
        }

        @Override
        public boolean[] connections() {
            return connections;
        }
    },
    C2 {
        private final boolean[] connections = {true, false, false, true};
        private final boolean[][] connectedTo = {
                {false, false, false, true},
                {false, false, false, false},
                {false, false, false, false},
                {true, false, false, false},
        };

        @Override
        public boolean connectableFrom(int direction) {
            return direction == 0 || direction == 3;
        }

        @Override
        public boolean[] connectedTo(int fromDirection) {
            return connectedTo[fromDirection];
        }

        @Override
        public boolean[] connections() {
            return connections;
        }
    },
    C3 {
        private final boolean[] connections = {false, true, true, true};
        private final boolean[][] connectedTo = {
                {false, false, false, false},
                {false, false, true, true},
                {false, true, false, true},
                {false, true, true, false},
        };

        @Override
        public boolean connectableFrom(int direction) {
            return direction > 0;
        }

        @Override
        public boolean[] connectedTo(int fromDirection) {
            return connectedTo[fromDirection];
        }

        @Override
        public boolean[] connections() {
            return connections;
        }
    };

    public abstract boolean connectableFrom(int direction);

    public abstract boolean[] connectedTo(int fromDirection);

    public abstract boolean[] connections();

    public static final CellType[] FROM_ORDINAL = {POWER_SOURCE, LAMP, C1, C2, C3};
}
