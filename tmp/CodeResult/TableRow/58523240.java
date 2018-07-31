package by.bsuir.acm.jaj.core.judge;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.bsuir.acm.jaj.umgr.model.User;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TableRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String user;
    private final String id;
    private final double score;
    private final double penalty;
    private final long version;
    private final List<TableCell> cells;
    private transient Map<String, TableCell> problemToCell;

    public TableRow(User user, double score, double penalty, List<TableCell> cells, long version) {
        this.user = user.getId();
        this.id = "row$" + this.user;
        this.score = score;
        this.penalty = penalty;
        this.cells = Lists.newArrayList(cells);
        this.version = version;
    }

    public TableRow(User user, double score, double penalty, List<TableCell> cells) {
        this(user, score, penalty, cells, maxCellsVersion(cells, Long.MIN_VALUE));
    }

    public TableRow(TableRow prototype, double score, double penalty, List<TableCell> cells,
            long version) {
        this.id = prototype.id;
        this.user = prototype.user;
        this.score = score;
        this.penalty = penalty;
        HashMap<String, TableCell> p2c = Maps.newHashMap(prototype.getCellsMap());
        for (TableCell c : cells) {
            p2c.put(c.getProblemId(), c);
        }
        this.cells = Lists.newArrayList(p2c.values());
        this.version = version;
    }

    public TableRow(TableRow prototype, double score, double penalty, List<TableCell> cells) {
        this(prototype, score, penalty, cells, maxCellsVersion(cells, prototype.getVersion() + 1));
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public double getScore() {
        return score;
    }

    public double getPenalty() {
        return penalty;
    }

    public List<TableCell> getCells() {
        return cells;
    }

    public String getUserId() {
        return user;
    }

    public Map<String, TableCell> getCellsMap() {
        if (problemToCell == null) {
            Map<String, TableCell> cm = Maps.newHashMapWithExpectedSize(cells.size());
            for (TableCell c : cells) {
                cm.put(c.getProblemId(), c);
            }
            problemToCell = Collections.unmodifiableMap(cm);
        }
        return problemToCell;
    }

    private static long maxCellsVersion(List<TableCell> cells, long base) {
        for (TableCell cell : cells) {
            if (cell.getVersion() > base) {
                base = cell.getVersion();
            }
        }
        return base;
    }
}
