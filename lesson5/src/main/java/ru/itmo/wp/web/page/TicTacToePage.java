package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    final static int SIZE = 3;
    static private State privateState;
    static private State state;

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (privateState == null) {
            privateState = new State(SIZE);
        } else {
            privateState.check(state);
        }
        state = privateState.copy();
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (request.getParameter("cell_" + i + j) != null) {
                    if (state.getCrossesMove()) {
                        state.getCells()[i][j] = "X";
                    } else {
                        state.getCells()[i][j] = "O";
                    }
                }
            }
        }

        action(request, view);
    }

    private void newGame(Map<String, Object> view) {
        privateState = new State(SIZE);
        state = privateState.copy();
        view.put("state", state);
    }

    public static class State {
        private final String[][] cells;
        private String phase;
        private final int size;
        private boolean crossesMove = true;
        private int turn = 0;

        public State(int size) {
            this.size = size;
            this.cells = new String[size][size];
            for (int i = 0; i < this.cells.length; i++) {
                cells[i] = new String[size];
            }
            this.phase = "RUNNING";
        }

        public String getPhase() {
            return phase;
        }

        public int getSize() {
            return size;
        }

        public String[][] getCells() {
            return cells;
        }

        public boolean getCrossesMove() {
            return crossesMove;
        }

        public State copy() {
            State copy = new State(size);
            copy.phase = phase;
            copy.crossesMove = crossesMove;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    copy.cells[i][j] = cells[i][j];
                }
            }
            return copy;
        }

        public void check(State state) {
            int changeQuantity = change(state.getCells());
        }

        private int change(String[][] nextCells) {
            if (!phase.equals("RUNNING")) {
                return 0;
            }
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j] != null && !cells[i][j].equals(nextCells[i][j])) {
                        return -1;
                    } else if (cells[i][j] == null && nextCells[i][j] != null &&
                            ((nextCells[i][j].equals("X") && crossesMove) ||
                                    (nextCells[i][j].equals("O") && !crossesMove))) {
                        cells[i][j] = nextCells[i][j];
                        turn++;
                        checkWin(i, j);
                        return 1;
                    }
                }
            }
            return 0;
        }

        private void checkWin(int i, int j) {
            if (checkWin(i, j, 0, 1) ||
                    checkWin(i, j, 1, 0) ||
                    checkWin(i, j, 1, 1) ||
                    checkWin(i, j, -1, 1)) {
                if (crossesMove) {
                    phase = "WON_X";
                } else {
                    phase = "WON_O";
                }
            } else if (turn == SIZE * SIZE) {
                phase = "DRAW";
            } else {
                crossesMove = !crossesMove;
            }
        }

        private boolean isCell(int i, int j) {
            return 0 <= i && i < size && 0 <= j && j < size;
        }

        private boolean checkWin(int i, int j, int x, int y) {
            int ip = i + x, im = i - x,
                    jp = j + y, jm = j - y;
            int sum = 1;
            String type = cells[i][j];
            while (isCell(ip, jp) || isCell(im, jm)) {
                if (isCell(im, jm)) {
                    if (cells[im][jm] != null && cells[im][jm].equals(type)) {
                        sum++;
                    } else {
                        return false;
                    }
                    im -= x;
                    jm -= y;
                }
                if (isCell(ip, jp)) {
                    if (cells[ip][jp] != null && cells[ip][jp].equals(type)) {
                        sum++;
                    } else {
                        return false;
                    }
                    ip += x;
                    jp += y;
                }
            }
            return sum == 3;
        }
    }
}
