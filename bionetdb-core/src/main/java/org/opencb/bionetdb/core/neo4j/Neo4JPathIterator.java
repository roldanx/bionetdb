package org.opencb.bionetdb.core.neo4j;

import org.neo4j.driver.v1.StatementResult;
import org.opencb.bionetdb.core.api.PathIterator;
import org.opencb.bionetdb.core.network.Network;
import org.opencb.bionetdb.core.utils.Neo4JConverter;

import java.util.ArrayList;
import java.util.List;

public class Neo4JPathIterator implements PathIterator {
    private StatementResult statementResult;
    private List<Network> buffer;

    public Neo4JPathIterator(StatementResult statementResult) {
        this.statementResult = statementResult;
        this.buffer = new ArrayList<>();
    }

    @Override
    public boolean hasNext() {
        if (!buffer.isEmpty()) {
            return true;
        } else {
            if (statementResult.hasNext()) {
                buffer = Neo4JConverter.toNetworks(statementResult.next());
            }
            return !buffer.isEmpty();
        }
    }

    @Override
    public Network next() {
        return buffer.remove(0);
    }
}
