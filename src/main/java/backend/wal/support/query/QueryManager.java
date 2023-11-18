package backend.wal.support.query;

import java.util.List;

public class QueryManager {

    private final List<String> queries;
    private final long time = System.currentTimeMillis();

    public QueryManager(List<String> queries) {
        this.queries = queries;
    }

    public void addQuery(String sql) {
        queries.add(sql);
    }

    public boolean hasDuplicatedQuery() {
        return queries.size() != queries.stream().distinct().count();
    }

    public int getQueryCount() {
        return queries.size();
    }

    public long calculateDuration(long afterQuery) {
        return afterQuery - time;
    }
}
